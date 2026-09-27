package com.example.apisecurity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ProjectRequest(@NotBlank String name, String description, @NotBlank @Pattern(regexp="https?://(localhost|127\\.0\\.0\\.1)(:[0-9]+)?", message="Only localhost targets are allowed") String baseUrl) {}
