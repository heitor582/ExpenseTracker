package com.study.expensetracker.application.category.create;

import com.study.expensetracker.domain.category.CategoryType;

public record CreateCategoryCommand(
        String name,
        CategoryType type,
        String budgetId
) {
    public static CreateCategoryCommand with(
            final String name,
            final CategoryType type,
            final String budgetId
    ) {
        return new CreateCategoryCommand(name, type, budgetId);
    }
}
