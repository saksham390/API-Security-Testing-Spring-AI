package com.example.apisecurity.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.apisecurity.entity.Project;
import com.example.apisecurity.entity.TestRun;

public interface TestRunRepository extends JpaRepository<TestRun, Long> {
    List<TestRun> findAllByProjectOrderByStartedAtDesc(Project project);
    Optional<TestRun> findByIdAndProject(Long id, Project project);
}
