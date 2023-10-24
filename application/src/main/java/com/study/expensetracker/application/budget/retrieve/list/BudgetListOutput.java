package com.study.expensetracker.application.budget.retrieve.list;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetID;

import java.math.BigDecimal;
import java.time.Instant;

public record BudgetListOutput(
        BudgetID budgetID,
        String name,
        BigDecimal actualValue,
        BigDecimal maxValue,
        Instant createdAt,
        Instant updatedAt
) {
    public static BudgetListOutput from(final Budget budget) {
        return new BudgetListOutput(
          budget.getId(),
          budget.getName(),
          budget.getActualValue(),
          budget.getMaxValue(),
          budget.getCreatedAt(),
          budget.getUpdatedAt()
        );
    }
}
