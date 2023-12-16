package com.study.expensetracker.domain.expense;

import com.study.expensetracker.domain.events.DomainEvent;
import com.study.expensetracker.domain.utils.InstantUtils;

import java.time.Instant;

public record InvoiceCreated(
        String id,
        Instant occurredOn
) implements DomainEvent {
    public InvoiceCreated(final String id){
        this(id, InstantUtils.now());
    }
}
