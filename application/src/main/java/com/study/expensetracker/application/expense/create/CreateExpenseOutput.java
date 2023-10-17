package com.study.expensetracker.application.expense.create;

import com.study.expensetracker.domain.expense.Expense;

public record CreateExpenseOutput(
        String id
) {
    public static CreateExpenseOutput from(final String id) {
        return new CreateExpenseOutput(id);
    }

    public static CreateExpenseOutput from(final Expense expense){
        return from(expense.getId().getValue());
    }
}
