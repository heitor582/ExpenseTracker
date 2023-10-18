package com.study.expensetracker.application.category.update;

import com.study.expensetracker.domain.category.CategoryType;

public record UpdateCategoryCommand(
        String id,
        CategoryType newType
) {
    public static UpdateCategoryCommand with(final String id,  final CategoryType newType) {
        return new UpdateCategoryCommand(id, newType);
    }
}
