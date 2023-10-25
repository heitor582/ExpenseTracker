package com.study.expensetracker.application.category.create;

public record CreateCategoryCommand(
        String name,
        String type,
        String budgetId
) {
    public static CreateCategoryCommand with(
            final String name,
            final String type,
            final String budgetId
    ) {
        return new CreateCategoryCommand(name, type, budgetId);
    }
}
