package com.example.apisecurity.ai;

import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import com.example.apisecurity.entity.ApiEndpoint;
import com.example.apisecurity.entity.TestResult;

@Service
public class AiService {
    private final ChatClient client;
    public AiService(ObjectProvider<ChatClient.Builder> builders){ChatClient.Builder builder=builders.getIfAvailable();this.client=builder==null?null:builder.build();}
    public SecurityTestPlan plan(ApiEndpoint endpoint,String request){
        if(client==null)return new SecurityTestPlan(endpoint.getId(),List.of(new SecurityTestItem(TestType.AUTHENTICATION,"Check whether protected data requires identity verification.","HIGH"),new SecurityTestItem(TestType.INPUT_VALIDATION,"Check how malformed input is handled.","MEDIUM")));
        String prompt="Return only a JSON object matching SecurityTestPlan. endpointId="+endpoint.getId()+" path="+endpoint.getPath()+" method="+endpoint.getMethod()+" request="+request+". Allowed testType values are AUTHENTICATION, AUTHORIZATION, INPUT_VALIDATION, MISSING_REQUIRED_FIELD, INVALID_METHOD, BOUNDARY_VALUE, SECURITY_HEADERS, CONTENT_TYPE, RATE_LIMIT. Never invent other values.";
        return client.prompt().user(prompt).call().entity(SecurityTestPlan.class);
    }
    public String chat(String question,List<TestResult> evidence){
        if(client==null)return "Gemini is not configured. Set GEMINI_API_KEY on the backend. Evidence available: "+evidence.size()+" result(s).";
        String context=evidence.stream().map(r->r.getTestName()+"="+r.getStatus()+"; evidence="+r.getEvidence()+"; recommendation="+r.getRecommendation()).reduce("",(a,b)->a+"\n"+b);
        return client.prompt().system("Explain only the provided API security evidence. If evidence is insufficient, say 'Insufficient evidence.' Do not claim a vulnerability without evidence.").user(question+"\nEvidence:"+context).call().content();
    }
}
