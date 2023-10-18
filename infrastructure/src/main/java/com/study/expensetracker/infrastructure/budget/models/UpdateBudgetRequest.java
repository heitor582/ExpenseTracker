package com.study.expensetracker.infrastructure.budget.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateBudgetRequest(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name
) {
}
