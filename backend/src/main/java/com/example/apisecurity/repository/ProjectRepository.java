package com.example.apisecurity.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.apisecurity.entity.Project;
import com.example.apisecurity.entity.User;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByOwnerOrderByUpdatedAtDesc(User owner);
    Optional<Project> findByIdAndOwner(Long id, User owner);
}
