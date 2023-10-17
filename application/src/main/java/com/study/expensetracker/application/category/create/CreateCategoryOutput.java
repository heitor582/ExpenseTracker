package com.study.expensetracker.application.category.create;

import com.study.expensetracker.domain.category.Category;

public record CreateCategoryOutput(
        String id
) {
    public static CreateCategoryOutput from(final Category category) {
        return from(category.getId().getValue());
    }

    public static CreateCategoryOutput from(final String id) {
        return new CreateCategoryOutput(id);
    }
}
