package com.study.expensetracker.infrastructure.budget.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CreateBudgetRequest(
        @JsonProperty("name") String name,
        @JsonProperty("max_value") BigDecimal maxValue
) {
}
