package com.example.apisecurity.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(@NotBlank String question, Long testRunId) {}
