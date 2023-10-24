package com.study.expensetracker.domain.budget;


import com.study.expensetracker.domain.pagination.Pagination;
import com.study.expensetracker.domain.pagination.SearchQuery;

import java.util.Optional;

public interface BudgetGateway {
    Budget create(final Budget budget);
    Budget update(final Budget budget);
    Pagination<Budget> list(final SearchQuery query);
    Optional<Budget> findBy(final BudgetID budgetID);
}
