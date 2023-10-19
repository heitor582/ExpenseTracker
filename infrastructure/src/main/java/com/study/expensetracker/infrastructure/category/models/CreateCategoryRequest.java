package com.study.expensetracker.infrastructure.category.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCategoryRequest(
        @JsonProperty("budget_id") String budgetId,
        @JsonProperty("name") String name,
        @JsonProperty("type") String type
) {
}
