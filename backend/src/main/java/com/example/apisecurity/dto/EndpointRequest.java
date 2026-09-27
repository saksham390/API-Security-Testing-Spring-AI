package com.example.apisecurity.dto;

import com.example.apisecurity.entity.HttpMethod;
import jakarta.validation.constraints.NotBlank;

public record EndpointRequest(Long projectId,@NotBlank String name,@NotBlank String path,HttpMethod method,String description,String authenticationType,Integer expectedStatusCode,String requestHeaders,String requestBody) {}
