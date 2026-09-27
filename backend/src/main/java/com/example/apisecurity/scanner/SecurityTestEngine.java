package com.example.apisecurity.scanner;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.springframework.stereotype.Service;
import com.example.apisecurity.entity.*;

@Service
public class SecurityTestEngine {
    private final SafeTargetValidator targets;
    private final HttpClient client=HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();
    public SecurityTestEngine(SafeTargetValidator targets){this.targets=targets;}
    public TestResult execute(TestRun run,ApiEndpoint endpoint,String testType){
        long started=System.nanoTime();
        try{
            URI uri=targets.resolve(endpoint.getProject().getBaseUrl(),endpoint.getPath());
            HttpRequest request=HttpRequest.newBuilder(uri).timeout(Duration.ofSeconds(5)).header("Accept","application/json").method(method(testType,endpoint),body(testType,endpoint)).build();
            HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString());
            long millis=Duration.ofNanos(System.nanoTime()-started).toMillis();
            boolean unauthenticatedSuccess=("AUTHENTICATION".equals(testType)&&response.statusCode()<400&&"NONE".equalsIgnoreCase(endpoint.getAuthenticationType()));
            ResultStatus status=unauthenticatedSuccess?ResultStatus.FAIL:ResultStatus.PASS;
            Severity severity=unauthenticatedSuccess?Severity.HIGH:Severity.LOW;
            String description=unauthenticatedSuccess?"Endpoint returned a successful response without authentication.":"The endpoint responded to the safe test request.";
            return new TestResult(run,endpoint,testType,status,severity,description,"HTTP "+response.statusCode(),unauthenticatedSuccess?"Require authentication before processing the request.":"Review the response and keep the control covered by regression tests.",response.statusCode(),millis);
        }catch(Exception ex){return new TestResult(run,endpoint,testType,ResultStatus.ERROR,Severity.MEDIUM,"The safe test could not complete.",ex.getClass().getSimpleName(),"Verify that the local target is running and reachable.",null,Duration.ofNanos(System.nanoTime()-started).toMillis());}
    }
    private String method(String type,ApiEndpoint endpoint){return "INVALID_METHOD".equals(type)?"OPTIONS":endpoint.getMethod().name();}
    private HttpRequest.BodyPublisher body(String type,ApiEndpoint endpoint){return endpoint.getRequestBody()!=null?HttpRequest.BodyPublishers.ofString(endpoint.getRequestBody()):HttpRequest.BodyPublishers.noBody();}
}
