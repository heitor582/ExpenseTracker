package com.study.expensetracker.application.expense.retrieve.list;

import com.study.expensetracker.domain.expense.ExpenseGateway;
import com.study.expensetracker.domain.pagination.Pagination;
import com.study.expensetracker.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListExpenseUseCase extends ListExpenseUseCase{
    private final ExpenseGateway expenseGateway;

    public DefaultListExpenseUseCase(final ExpenseGateway expenseGateway) {
        this.expenseGateway = Objects.requireNonNull(expenseGateway);
    }

    @Override
    public Pagination<ExpenseListOutput> execute(final SearchQuery query) {
        return this.expenseGateway.list(query).map(ExpenseListOutput::from);
    }
}
