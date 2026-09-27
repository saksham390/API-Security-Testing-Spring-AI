package com.example.apisecurity.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.apisecurity.entity.*;
import com.example.apisecurity.repository.*;
import com.example.apisecurity.scanner.SecurityTestEngine;

@Service
public class TestRunService {
    private static final List<String> ALLOWED=List.of("AUTHENTICATION","AUTHORIZATION","INPUT_VALIDATION","MISSING_REQUIRED_FIELD","INVALID_METHOD","BOUNDARY_VALUE","SECURITY_HEADERS","CONTENT_TYPE","RATE_LIMIT");
    private final ProjectRepository projects; private final TestRunRepository runs; private final ApiEndpointRepository endpoints; private final TestResultRepository results; private final SecurityTestEngine engine;
    public TestRunService(ProjectRepository projects,TestRunRepository runs,ApiEndpointRepository endpoints,TestResultRepository results,SecurityTestEngine engine){this.projects=projects;this.runs=runs;this.endpoints=endpoints;this.results=results;this.engine=engine;}
    public TestRun run(Long projectId,User user){Project project=projects.findByIdAndOwner(projectId,user).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Project not found"));TestRun run=runs.save(new TestRun(project));for(ApiEndpoint endpoint:endpoints.findAllByProject(project)){for(String type:ALLOWED){results.save(engine.execute(run,endpoint,type));}}run.complete();return runs.save(run);}
    public TestRun get(Long id,User user){return runs.findById(id).filter(r->r.getProject().getOwner().getId().equals(user.getId())).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Test run not found"));}
    public List<TestResult> results(Long id,User user){return results.findAllByTestRun(get(id,user));}
    public List<TestRun> byProject(Long id,User user){Project project=projects.findByIdAndOwner(id,user).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Project not found"));return runs.findAllByProjectOrderByStartedAtDesc(project);}
    public static boolean allowed(String type){return ALLOWED.contains(type);}
}
