package com.study.expensetracker.infrastructure.category.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public record CreateCategoryRequest(
        @JsonProperty("budget_id") Optional<String> budgetId,
        @JsonProperty("name") String name,
        @JsonProperty("type") String type
) {
}
