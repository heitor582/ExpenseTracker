package com.study.expensetracker.application.budget.create;

import com.study.expensetracker.domain.budget.Budget;

public record CreateBudgetOutput(
        String id
) {
    public static CreateBudgetOutput from(final Budget budget) {
        return from(budget.getId().getValue());
    }

    public static CreateBudgetOutput from(final String id) {
        return new CreateBudgetOutput(id);
    }
}
