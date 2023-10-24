package com.study.expensetracker.application.expense.retrieve.list;

import com.study.expensetracker.domain.category.CategoryID;
import com.study.expensetracker.domain.expense.Expense;
import com.study.expensetracker.domain.expense.ExpenseID;

import java.math.BigDecimal;
import java.time.Instant;

public record ExpenseListOutput(
        ExpenseID id,
        String name,
        String description,
        BigDecimal amount,
        CategoryID categoryID,
        Instant createdAt
) {
    public static ExpenseListOutput from(final Expense expense) {
        return new ExpenseListOutput(
                expense.getId(),
                expense.getName(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getCategory().getId(),
                expense.getCreatedAt()
        );
    }
}
