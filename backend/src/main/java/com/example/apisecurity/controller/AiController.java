package com.example.apisecurity.controller;

import java.util.List;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.apisecurity.ai.*;
import com.example.apisecurity.dto.ChatRequest;
import com.example.apisecurity.entity.*;
import com.example.apisecurity.repository.ApiEndpointRepository;
import com.example.apisecurity.service.TestRunService;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    private final AiService ai; private final ApiEndpointRepository endpoints; private final TestRunService runs; private final SecurityTestPlanValidator validator;
    public AiController(AiService ai,ApiEndpointRepository endpoints,TestRunService runs,SecurityTestPlanValidator validator){this.ai=ai;this.endpoints=endpoints;this.runs=runs;this.validator=validator;}
    @PostMapping("/test-plan") public SecurityTestPlan plan(@RequestBody Map<String,Object> request,@AuthenticationPrincipal User user){Long endpointId=((Number)request.get("endpointId")).longValue();ApiEndpoint endpoint=endpoints.findById(endpointId).filter(e->e.getProject().getOwner().getId().equals(user.getId())).orElseThrow();return validator.validate(ai.plan(endpoint,String.valueOf(request.getOrDefault("request","Review this endpoint."))));}
    @PostMapping("/chat") public Map<String,String> chat(@Valid @RequestBody ChatRequest request,@AuthenticationPrincipal User user){List<TestResult> evidence=request.testRunId()==null?List.of():runs.results(request.testRunId(),user);return Map.of("answer",ai.chat(request.question(),evidence));}
}
