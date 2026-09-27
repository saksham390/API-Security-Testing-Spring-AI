package com.example.apisecurity.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.apisecurity.dto.EndpointRequest;
import com.example.apisecurity.entity.ApiEndpoint;
import com.example.apisecurity.entity.HttpMethod;
import com.example.apisecurity.entity.Project;
import com.example.apisecurity.entity.User;
import com.example.apisecurity.repository.ApiEndpointRepository;
import com.example.apisecurity.repository.ProjectRepository;

@Service
public class EndpointService {
    private final ApiEndpointRepository endpoints; private final ProjectRepository projects;
    public EndpointService(ApiEndpointRepository endpoints,ProjectRepository projects){this.endpoints=endpoints;this.projects=projects;}
    public ApiEndpoint create(EndpointRequest request,User user){Project project=project(request.projectId(),user);validatePath(request.path());return endpoints.save(new ApiEndpoint(project,request.name(),request.path(),request.method()==null?HttpMethod.GET:request.method(),request.description(),request.authenticationType()==null?"NONE":request.authenticationType(),request.expectedStatusCode()==null?200:request.expectedStatusCode()));}
    public List<ApiEndpoint> forProject(Long projectId,User user){return endpoints.findAllByProject(project(projectId,user));}
    public ApiEndpoint get(Long id,User user){return endpoints.findById(id).filter(e->e.getProject().getOwner().getId().equals(user.getId())).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Endpoint not found"));}
    public void delete(Long id,User user){endpoints.delete(get(id,user));}
    private Project project(Long id,User user){return projects.findByIdAndOwner(id,user).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Project not found"));}
    private void validatePath(String path){if(path==null||!path.startsWith("/")||path.contains("..")||path.contains("\\")||path.contains("?"))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Endpoint path is invalid");}
}
