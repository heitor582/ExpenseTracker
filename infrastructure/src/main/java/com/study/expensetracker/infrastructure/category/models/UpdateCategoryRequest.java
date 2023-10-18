package com.study.expensetracker.infrastructure.category.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateCategoryRequest(
       @JsonProperty("id") String id,
       @JsonProperty("name") String name
) {
}
