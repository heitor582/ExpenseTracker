package com.study.expensetracker.application.budget.update;

public record UpdateBudgetCommand(
        String id,
        String newName
) {

    public static UpdateBudgetCommand with(final String id, final String newName) {
        return new UpdateBudgetCommand(id, newName);
    }
}
