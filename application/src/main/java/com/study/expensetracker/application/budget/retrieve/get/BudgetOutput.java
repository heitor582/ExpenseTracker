package com.study.expensetracker.application.budget.retrieve.get;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetID;

import java.math.BigDecimal;
import java.time.Instant;

public record BudgetOutput(
        BudgetID id,
        String name,
        BigDecimal actualValue,
        BigDecimal maxValue,
        Instant createdAt,
        Instant updatedAt
) {
    public static BudgetOutput from(final Budget budget) {
        return new BudgetOutput(
          budget.getId(),
          budget.getName(),
          budget.getActualValue(),
          budget.getMaxValue(),
          budget.getCreatedAt(),
          budget.getUpdatedAt()
        );
    }
}
