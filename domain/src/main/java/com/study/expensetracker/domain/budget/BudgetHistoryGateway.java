package com.study.expensetracker.domain.budget;

import java.util.List;
import java.util.Optional;

public interface BudgetHistoryGateway {
    void update(final BudgetHistory budgetHistory);
    Optional<BudgetHistory> findByBudgetID(final BudgetID id, final int month, final int year);
}
