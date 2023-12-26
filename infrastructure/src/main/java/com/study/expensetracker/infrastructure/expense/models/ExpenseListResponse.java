package com.study.expensetracker.infrastructure.expense.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.expensetracker.application.expense.retrieve.list.ExpenseListOutput;
import com.study.expensetracker.domain.expense.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;

public record ExpenseListResponse(
         @JsonProperty("id") String id,
         @JsonProperty("name") String name,
         @JsonProperty("description") String description,
         @JsonProperty("amount") BigDecimal amount,
         @JsonProperty("payment_method") PaymentMethod paymentMethod,
         @JsonProperty("category_id") String categoryID,
         @JsonProperty("created_at") Instant createdAt
) {
    public static ExpenseListResponse from(final ExpenseListOutput output) {
        return new ExpenseListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.amount(),
                output.paymentMethod(),
                output.categoryID().getValue(),
                output.createdAt()
        );
    }
}
