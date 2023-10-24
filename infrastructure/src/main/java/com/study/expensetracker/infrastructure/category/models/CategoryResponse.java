package com.study.expensetracker.infrastructure.category.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.expensetracker.application.category.retrieve.get.CategoryOutput;

import java.math.BigDecimal;
import java.time.Instant;

public record CategoryResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("type") String type,
        @JsonProperty("actual_value") BigDecimal actualValue,
        @JsonProperty("budget_id") String budgetID,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt
) {
    public static CategoryResponse from(final CategoryOutput output) {
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.type().name(),
                output.actualValue(),
                output.budgetID().getValue(),
                output.createdAt(),
                output.updatedAt()
        );
    }
}
