package com.study.expensetracker.application.budget.retrieve.get;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.budget.BudgetID;
import com.study.expensetracker.domain.exceptions.NotFoundException;

import java.util.Objects;

public class DefaultFindBudgetByIdUseCase extends FindBudgetByIdUseCase {
    private final BudgetGateway budgetGateway;

    public DefaultFindBudgetByIdUseCase(final BudgetGateway budgetGateway) {
        this.budgetGateway = Objects.requireNonNull(budgetGateway);
    }

    @Override
    public BudgetOutput execute(final String id) {
        final BudgetID budgetID = BudgetID.from(id);
        return this.budgetGateway.findBy(budgetID).map(BudgetOutput::from)
                .orElseThrow(() -> NotFoundException.with(Budget.class, budgetID));
    }
}
