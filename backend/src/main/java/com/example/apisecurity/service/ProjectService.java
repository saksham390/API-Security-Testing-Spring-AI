package com.example.apisecurity.service;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.apisecurity.dto.ProjectRequest;
import com.example.apisecurity.entity.Project;
import com.example.apisecurity.entity.User;
import com.example.apisecurity.repository.ProjectRepository;

@Service
public class ProjectService {
    private final ProjectRepository repository;
    public ProjectService(ProjectRepository repository){this.repository=repository;}
    public List<Project> all(User user){return repository.findAllByOwnerOrderByUpdatedAtDesc(user);}
    public Project get(Long id,User user){return repository.findByIdAndOwner(id,user).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Project not found"));}
    public Project create(ProjectRequest request,User user){validateTarget(request.baseUrl());return repository.save(new Project(request.name(),request.description(),request.baseUrl(),user));}
    public Project update(Long id,ProjectRequest request,User user){Project p=get(id,user);validateTarget(request.baseUrl());p.update(request.name(),request.description(),request.baseUrl());return repository.save(p);}
    public void delete(Long id,User user){repository.delete(get(id,user));}
    private void validateTarget(String url){URI uri=URI.create(url);String host=uri.getHost();if(!"localhost".equalsIgnoreCase(host)&&!"127.0.0.1".equals(host))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Only localhost targets are allowed");}
}
