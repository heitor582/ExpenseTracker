package com.study.expensetracker.application.budget.create;

import java.math.BigDecimal;

public record CreateBudgetCommand(
        String name,
        BigDecimal maxValue
) {
    public static CreateBudgetCommand with(final String name, final BigDecimal maxValue){
        return new CreateBudgetCommand(name, maxValue);
    }
}
