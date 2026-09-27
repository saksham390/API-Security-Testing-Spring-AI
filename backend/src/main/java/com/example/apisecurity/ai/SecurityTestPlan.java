package com.example.apisecurity.ai;

import java.util.List;

public record SecurityTestPlan(Long endpointId, List<SecurityTestItem> tests) {}
