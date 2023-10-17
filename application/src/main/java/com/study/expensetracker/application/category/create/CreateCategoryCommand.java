package com.study.expensetracker.application.category.create;

import com.study.expensetracker.domain.category.CategoryType;

import java.math.BigDecimal;

public record CreateCategoryCommand(
        CategoryType type,
        BigDecimal actualValue,
        String budgetId
) {
    public static CreateCategoryCommand with(
            final CategoryType type,
            final BigDecimal actualValue,
            final String budgetId
    ) {
        return new CreateCategoryCommand(type, actualValue, budgetId);
    }
}
