package com.study.expensetracker.infrastructure.budget.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.expensetracker.application.budget.retrieve.get.BudgetOutput;

import java.math.BigDecimal;
import java.time.Instant;

public record BudgetResponse(
         @JsonProperty("id") String id,
         @JsonProperty("name") String name,
         @JsonProperty("actual_value") BigDecimal actualValue,
         @JsonProperty("max_value") BigDecimal maxValue,
         @JsonProperty("created_at") Instant createdAt,
         @JsonProperty("updated_at") Instant updatedAt
) {
    public static BudgetResponse from(final BudgetOutput output){
        return new BudgetResponse(
                output.budgetID().getValue(),
                output.name(),
                output.actualValue(),
                output.maxValue(),
                output.createdAt(),
                output.updatedAt()
        );
    }
}
