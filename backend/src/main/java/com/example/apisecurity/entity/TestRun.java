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
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Table(name = "test_runs")
public class TestRun {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional=false) @JsonIgnore private Project project;
    private Instant startedAt;
    private Instant completedAt;
    @Enumerated(EnumType.STRING) private TestStatus status;
    @OneToMany(mappedBy="testRun", cascade=CascadeType.ALL, orphanRemoval=true) private List<TestResult> results = new ArrayList<>();
    protected TestRun() {}
    public TestRun(Project project){this.project=project;this.startedAt=Instant.now();this.status=TestStatus.RUNNING;}
    public void complete(){this.completedAt=Instant.now();this.status=TestStatus.COMPLETED;}
    public Long getId(){return id;} public Project getProject(){return project;} public Instant getStartedAt(){return startedAt;} public Instant getCompletedAt(){return completedAt;} public TestStatus getStatus(){return status;} public List<TestResult> getResults(){return results;}
}
