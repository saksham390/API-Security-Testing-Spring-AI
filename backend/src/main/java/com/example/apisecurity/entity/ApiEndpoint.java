package com.example.apisecurity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "api_endpoints")
public class ApiEndpoint {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JsonIgnore private Project project;
    @Column(nullable=false,length=120) private String name;
    @Column(nullable=false,length=300) private String path;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=10) private HttpMethod method;
    private String description;
    @Column(nullable=false,length=30) private String authenticationType;
    @Column(length=2000) private String requestHeaders;
    @Column(length=5000) private String requestBody;
    private Integer expectedStatusCode;
    protected ApiEndpoint() {}
    public ApiEndpoint(Project project,String name,String path,HttpMethod method,String description,String authenticationType,Integer expectedStatusCode){this.project=project;this.name=name;this.path=path;this.method=method;this.description=description;this.authenticationType=authenticationType;this.expectedStatusCode=expectedStatusCode;}
    public Long getId(){return id;} public Project getProject(){return project;} public String getName(){return name;} public String getPath(){return path;} public HttpMethod getMethod(){return method;} public String getDescription(){return description;} public String getAuthenticationType(){return authenticationType;} public String getRequestHeaders(){return requestHeaders;} public String getRequestBody(){return requestBody;} public Integer getExpectedStatusCode(){return expectedStatusCode;}
}
