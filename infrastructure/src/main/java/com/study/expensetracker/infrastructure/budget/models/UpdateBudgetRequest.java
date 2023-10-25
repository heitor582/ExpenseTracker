package com.study.expensetracker.infrastructure.budget.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateBudgetRequest(
        @JsonProperty("name") String name
) {
}
