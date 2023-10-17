package com.study.expensetracker.domain.budget;


import java.util.List;
import java.util.Optional;

public interface BudgetGateway {
    Budget create(final Budget budget);
    Budget update(final Budget budget);
    List<Budget> list();
    Optional<Budget> findBy(final BudgetID budgetID);
}
