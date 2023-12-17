package com.study.expensetracker.domain.budget;

import com.study.expensetracker.domain.History;

import java.math.BigDecimal;

public class BudgetHistory extends History<BudgetID> {
    private final BigDecimal maxValue;

    protected BudgetHistory(
            final Long id,
            final BudgetID budgetId,
            final int month,
            final int year,
            final BigDecimal actualValue,
            final BigDecimal maxValue
    ) {
        super(id, budgetId, month, year, actualValue);
        this.maxValue = maxValue;
    }

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public static BudgetHistory newBudgetHistory(
            final BudgetID budgetId,
            final int month,
            final int year,
            final BigDecimal actualValue,
            final BigDecimal maxValue
    ) {
        return new BudgetHistory(0L, budgetId, month, year, actualValue, maxValue);
    }

    public static BudgetHistory with(
            final Long id,
            final BudgetID budgetId,
            final int month,
            final int year,
            final BigDecimal actualValue,
            final BigDecimal maxValue
    ) {
        return new BudgetHistory(id, budgetId, month, year, actualValue, maxValue);
    }
}
