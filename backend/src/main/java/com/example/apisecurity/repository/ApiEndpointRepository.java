package com.example.apisecurity.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.apisecurity.entity.ApiEndpoint;
import com.example.apisecurity.entity.Project;

public interface ApiEndpointRepository extends JpaRepository<ApiEndpoint, Long> {
    List<ApiEndpoint> findAllByProject(Project project);
    Optional<ApiEndpoint> findByIdAndProject(Long id, Project project);
}
