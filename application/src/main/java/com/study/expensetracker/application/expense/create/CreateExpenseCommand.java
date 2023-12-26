package com.study.expensetracker.application.expense.create;

import com.study.expensetracker.domain.expense.PaymentMethod;

import java.math.BigDecimal;
import java.util.Optional;

public record CreateExpenseCommand(
        String name,
        String description,
        Optional<String> createdAt,
        PaymentMethod paymentMethod,
        BigDecimal amount,
        String categoryId
) {
    public static CreateExpenseCommand with(
            final String name,
            final String description,
            final String createdAt,
            final PaymentMethod paymentMethod,
            final BigDecimal amount,
            final String categoryId
    ){
        return new CreateExpenseCommand(
                name,
                description,
                Optional.ofNullable(createdAt),
                paymentMethod,
                amount,
                categoryId
        );
    }
}
