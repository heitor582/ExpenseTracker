package com.study.expensetracker.application.category.create;

import com.study.expensetracker.domain.category.CategoryType;

public record CreateCategoryCommand(
        CategoryType type,
        String budgetId
) {
    public static CreateCategoryCommand with(
            final CategoryType type,
            final String budgetId
    ) {
        return new CreateCategoryCommand(type, budgetId);
    }
}
