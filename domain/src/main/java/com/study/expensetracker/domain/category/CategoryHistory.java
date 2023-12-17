package com.study.expensetracker.domain.category;

import com.study.expensetracker.domain.History;

import java.math.BigDecimal;

public class CategoryHistory extends History<CategoryID> {
    protected CategoryHistory(
            final Long id,
            final CategoryID categoryId,
            final int month,
            final int year,
            final BigDecimal actualValue
    ) {
        super(id, categoryId, month, year, actualValue);
    }

    public static CategoryHistory with(
            final Long id,
            final CategoryID categoryId,
            final int month,
            final int year,
            final BigDecimal actualValue
    ) {
        return new CategoryHistory(id, categoryId, month, year, actualValue);
    }

    public static CategoryHistory newCategoryHistory(
            final CategoryID categoryId,
            final int month,
            final int year,
            final BigDecimal actualValue
    ) {
        return new CategoryHistory(0L, categoryId, month, year, actualValue);
    }
}
