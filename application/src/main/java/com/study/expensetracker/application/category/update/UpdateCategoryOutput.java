package com.study.expensetracker.application.category.update;

import com.study.expensetracker.domain.category.Category;

public record UpdateCategoryOutput(
        String id
) {
    public static UpdateCategoryOutput from(final String id) {
        return new UpdateCategoryOutput(id);
    }

    public static UpdateCategoryOutput from(final Category category){
        return from(category.getId().getValue());
    }
}
