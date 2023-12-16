package com.study.expensetracker.domain.budget;


import com.study.expensetracker.domain.pagination.Pagination;
import com.study.expensetracker.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface BudgetGateway {
    Budget create(final Budget budget);
    Budget update(final Budget budget);
    List<Budget> update(final List<Budget> budget);
    Pagination<Budget> list(final SearchQuery query);
    Optional<Budget> findBy(final BudgetID budgetID);
    List<Budget> findALl();
}
