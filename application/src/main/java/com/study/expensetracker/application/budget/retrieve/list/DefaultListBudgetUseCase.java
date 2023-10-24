package com.study.expensetracker.application.budget.retrieve.list;

import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.pagination.Pagination;
import com.study.expensetracker.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListBudgetUseCase extends ListBudgetUseCase{
    private final BudgetGateway budgetGateway;

    public DefaultListBudgetUseCase(final BudgetGateway budgetGateway) {
        this.budgetGateway = Objects.requireNonNull(budgetGateway);
    }

    @Override
    public Pagination<BudgetListOutput> execute(final SearchQuery query) {
        return this.budgetGateway.list(query).map(BudgetListOutput::from);
    }
}
