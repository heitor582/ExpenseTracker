package com.study.expensetracker.infrastructure.expense.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.expensetracker.domain.expense.PaymentMethod;
import com.study.expensetracker.domain.pagination.Pagination;

import java.math.BigDecimal;

public record CreateExpenseRequest(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("payment_method") PaymentMethod paymentMethod,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("category_id") String categoryID
) {
}
