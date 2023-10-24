package com.study.expensetracker.domain.expense;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.pagination.Pagination;
import com.study.expensetracker.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface ExpenseGateway {
    Expense create(final Expense expense);
    Pagination<Expense> list(final SearchQuery query);
    Optional<Expense> findBy(final ExpenseID expenseID);
}
