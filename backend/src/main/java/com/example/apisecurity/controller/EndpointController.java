package com.example.apisecurity.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.apisecurity.dto.EndpointRequest;
import com.example.apisecurity.entity.ApiEndpoint;
import com.example.apisecurity.entity.User;
import com.example.apisecurity.service.EndpointService;

@RestController
@RequestMapping("/api/endpoints")
public class EndpointController {
    private final EndpointService service;
    public EndpointController(EndpointService service){this.service=service;}
    @PostMapping public ResponseEntity<ApiEndpoint> create(@Valid @RequestBody EndpointRequest request,@AuthenticationPrincipal User user){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request,user));}
    @GetMapping("/{id}") public ApiEndpoint get(@PathVariable Long id,@AuthenticationPrincipal User user){return service.get(id,user);}
    @GetMapping("/project/{projectId}") public List<ApiEndpoint> list(@PathVariable Long projectId,@AuthenticationPrincipal User user){return service.forProject(projectId,user);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id,@AuthenticationPrincipal User user){service.delete(id,user);return ResponseEntity.noContent().build();}
}
