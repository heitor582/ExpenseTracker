package com.study.expensetracker.infrastructure.expense.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateExpenseRequest(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("category") String categoryID
) {
}
