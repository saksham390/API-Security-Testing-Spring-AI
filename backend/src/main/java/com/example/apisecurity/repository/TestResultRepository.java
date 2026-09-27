package com.example.apisecurity.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.apisecurity.entity.TestResult;
import com.example.apisecurity.entity.TestRun;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findAllByTestRun(TestRun testRun);
}
