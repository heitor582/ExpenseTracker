package com.study.expensetracker.application.budget.update;

import com.study.expensetracker.domain.budget.Budget;

public record UpdateBudgetOutput(
        String id
) {
    public static UpdateBudgetOutput from(final String id) {
        return new UpdateBudgetOutput(id);
    }

    public static UpdateBudgetOutput from(final Budget budget){
        return from(budget.getId().getValue());
    }
}
