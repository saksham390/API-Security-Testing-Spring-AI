package com.example.apisecurity.controller;

import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.apisecurity.entity.*;
import com.example.apisecurity.service.TestRunService;

@RestController
@RequestMapping("/api/test-runs")
public class TestRunController {
    private final TestRunService service;
    public TestRunController(TestRunService service){this.service=service;}
    @PostMapping("/{projectId}") public TestRun run(@PathVariable Long projectId,@AuthenticationPrincipal User user){return service.run(projectId,user);}
    @GetMapping("/{id}") public TestRun get(@PathVariable Long id,@AuthenticationPrincipal User user){return service.get(id,user);}
    @GetMapping("/{id}/results") public List<TestResult> results(@PathVariable Long id,@AuthenticationPrincipal User user){return service.results(id,user);}
    @GetMapping("/project/{projectId}") public List<TestRun> byProject(@PathVariable Long projectId,@AuthenticationPrincipal User user){return service.byProject(projectId,user);}
}
