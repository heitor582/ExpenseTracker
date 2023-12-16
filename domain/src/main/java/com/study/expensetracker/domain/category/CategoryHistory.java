package com.study.expensetracker.domain.category;

import com.study.expensetracker.domain.History;

import java.math.BigDecimal;

public class CategoryHistory extends History<CategoryID> {
    protected CategoryHistory(
            final CategoryID id,
            final int month,
            final int year,
            final BigDecimal actualValue
    ) {
        super(id, month, year, actualValue);
    }

    public static CategoryHistory newCategoryHistory(
            final CategoryID id,
            final int month,
            final int year,
            final BigDecimal actualValue
    ) {
        return new CategoryHistory(id, month, year, actualValue);
    }
}
