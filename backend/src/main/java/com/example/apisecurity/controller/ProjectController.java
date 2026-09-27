package com.example.apisecurity.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.apisecurity.dto.ProjectRequest;
import com.example.apisecurity.entity.Project;
import com.example.apisecurity.entity.User;
import com.example.apisecurity.service.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService service;
    public ProjectController(ProjectService service){this.service=service;}
    @GetMapping public List<Project> all(@AuthenticationPrincipal User user){return service.all(user);}
    @GetMapping("/{id}") public Project get(@PathVariable Long id,@AuthenticationPrincipal User user){return service.get(id,user);}
    @PostMapping public ResponseEntity<Project> create(@Valid @RequestBody ProjectRequest request,@AuthenticationPrincipal User user){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request,user));}
    @PutMapping("/{id}") public Project update(@PathVariable Long id,@Valid @RequestBody ProjectRequest request,@AuthenticationPrincipal User user){return service.update(id,request,user);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id,@AuthenticationPrincipal User user){service.delete(id,user);return ResponseEntity.noContent().build();}
}
