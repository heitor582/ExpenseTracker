package com.study.expensetracker.domain.expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseGateway {
    Expense create(final Expense expense);
    List<Expense> list();
    Optional<Expense> findBy(final ExpenseID expenseID);
}
