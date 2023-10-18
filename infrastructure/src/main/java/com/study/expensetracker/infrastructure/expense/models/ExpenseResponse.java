package com.study.expensetracker.infrastructure.expense.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.expensetracker.application.expense.retrieve.get.ExpenseOutput;

import java.math.BigDecimal;
import java.time.Instant;

public record ExpenseResponse(
         @JsonProperty("id") String id,
         @JsonProperty("name") String name,
         @JsonProperty("description") String description,
         @JsonProperty("amount") BigDecimal amount,
         @JsonProperty("category_id") String categoryID,
         @JsonProperty("created_at") Instant createdAt
) {
    public static ExpenseResponse from(final ExpenseOutput output) {
        return new ExpenseResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.amount(),
                output.categoryID().getValue(),
                output.createdAt()
        );
    }
}
