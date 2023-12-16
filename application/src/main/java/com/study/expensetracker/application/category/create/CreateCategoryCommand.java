package com.study.expensetracker.application.category.create;

import java.util.Optional;

public record CreateCategoryCommand(
        String name,
        String type,
        Optional<String> budgetId
) {
    public static CreateCategoryCommand with(
            final String name,
            final String type,
            final Optional<String> budgetId
    ) {
        return new CreateCategoryCommand(name, type, budgetId);
    }
}
