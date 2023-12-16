package com.study.expensetracker.domain.budget;

import com.study.expensetracker.domain.History;
import com.study.expensetracker.domain.category.CategoryID;

import java.math.BigDecimal;

public class BudgetHistory extends History<BudgetID> {
    private final BigDecimal maxValue;

    protected BudgetHistory(
            final BudgetID id,
            final int month,
            final int year,
            final BigDecimal actualValue,
            final BigDecimal maxValue
    ) {
        super(id, month, year, actualValue);
        this.maxValue = maxValue;
    }

    public static BudgetHistory newBudgetHistory(
            final BudgetID id,
            final int month,
            final int year,
            final BigDecimal actualValue,
            final BigDecimal maxValue
    ) {
        return new BudgetHistory(id, month, year, actualValue, maxValue);
    }

    public BigDecimal getMaxValue() {
        return maxValue;
    }
}
