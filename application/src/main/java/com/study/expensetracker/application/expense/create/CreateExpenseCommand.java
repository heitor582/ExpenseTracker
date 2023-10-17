package com.study.expensetracker.application.expense.create;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public record CreateExpenseCommand(
        String name,
        String description,
        Optional<Instant> createdAt,
        BigDecimal amount,
        String categoryId
) {
    public static CreateExpenseCommand with(
            final String name,
            final String description,
            final Instant createdAt,
            final BigDecimal amount,
            final String categoryId
    ){
        return new CreateExpenseCommand(
                name,
                description,
                Optional.ofNullable(createdAt),
                amount,
                categoryId
        );
    }
}
