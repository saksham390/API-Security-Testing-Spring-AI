package com.example.apisecurity.entity;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Table(name = "test_results")
public class TestResult {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JsonIgnore private TestRun testRun;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) private ApiEndpoint endpoint;
    private String testName;
    @Enumerated(EnumType.STRING) private ResultStatus status;
    @Enumerated(EnumType.STRING) private Severity severity;
    private String description;
    private String evidence;
    private String recommendation;
    private Integer responseStatus;
    private Long responseTime;
    private Instant createdAt;
    protected TestResult() {}
    public TestResult(TestRun run,ApiEndpoint endpoint,String testName,ResultStatus status,Severity severity,String description,String evidence,String recommendation,Integer responseStatus,Long responseTime){this.testRun=run;this.endpoint=endpoint;this.testName=testName;this.status=status;this.severity=severity;this.description=description;this.evidence=evidence;this.recommendation=recommendation;this.responseStatus=responseStatus;this.responseTime=responseTime;this.createdAt=Instant.now();}
    public Long getId(){return id;} public TestRun getTestRun(){return testRun;} public ApiEndpoint getEndpoint(){return endpoint;} public String getTestName(){return testName;} public ResultStatus getStatus(){return status;} public Severity getSeverity(){return severity;} public String getDescription(){return description;} public String getEvidence(){return evidence;} public String getRecommendation(){return recommendation;} public Integer getResponseStatus(){return responseStatus;} public Long getResponseTime(){return responseTime;} public Instant getCreatedAt(){return createdAt;}
}
