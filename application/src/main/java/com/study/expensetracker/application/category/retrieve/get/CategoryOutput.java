package com.study.expensetracker.application.category.retrieve.get;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetID;
import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryID;
import com.study.expensetracker.domain.category.CategoryType;

import java.math.BigDecimal;
import java.time.Instant;

public record CategoryOutput(
        CategoryID id,
        String name,
        CategoryType type,
        BigDecimal actualValue,
        BudgetID budgetID,
        Instant createdAt,
        Instant updatedAt
) {
    public static CategoryOutput from(final Category category) {
        return new CategoryOutput(
            category.getId(),
            category.getName(),
            category.getType(),
            category.getActualValue(),
            category.getBudget().map(Budget::getId).orElse(BudgetID.from("")),
            category.getCreatedAt(),
            category.getUpdatedAt()
        );
    }
}