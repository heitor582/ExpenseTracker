package com.study.expensetracker.infrastructure.expense.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record InvoiceCreatedMessage(
        @JsonProperty("id") String id,
        @JsonProperty("occurred_on") Instant occurredOn
) {
}
