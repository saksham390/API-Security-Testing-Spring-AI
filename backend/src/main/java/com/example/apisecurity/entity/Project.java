package com.example.apisecurity.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "projects")
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 120) private String name;
    private String description;
    @Column(nullable = false, length = 500) private String baseUrl;
    @Column(nullable = false, updatable = false) private Instant createdAt;
    @Column(nullable = false) private Instant updatedAt;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JsonIgnore private User owner;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true) private List<ApiEndpoint> endpoints = new ArrayList<>();

    protected Project() {}
    public Project(String name, String description, String baseUrl, User owner) { this.name=name; this.description=description; this.baseUrl=baseUrl; this.owner=owner; }
    @jakarta.persistence.PrePersist void onCreate() { createdAt=Instant.now(); updatedAt=createdAt; }
    @jakarta.persistence.PreUpdate void onUpdate() { updatedAt=Instant.now(); }
    public Long getId(){return id;} public String getName(){return name;} public String getDescription(){return description;} public String getBaseUrl(){return baseUrl;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;} public User getOwner(){return owner;} public List<ApiEndpoint> getEndpoints(){return endpoints;}
    public void update(String name,String description,String baseUrl){this.name=name;this.description=description;this.baseUrl=baseUrl;}
}
