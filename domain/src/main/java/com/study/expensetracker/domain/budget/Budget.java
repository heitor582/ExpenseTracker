package com.study.expensetracker.domain.budget;

import com.study.expensetracker.domain.AggregateRoot;
import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.validation.ValidationHandler;

import java.math.BigDecimal;

public class Budget extends AggregateRoot<BudgetID> {
    private final Category category;
    private final BigDecimal amount;

    private Budget(
            final BudgetID id,
            final Category category,
            final BigDecimal amount
    ) {
        super(id);
        this.category = category;
        this.amount = amount;
    }

    @Override
    public void validate(ValidationHandler handler) {

    }
}
