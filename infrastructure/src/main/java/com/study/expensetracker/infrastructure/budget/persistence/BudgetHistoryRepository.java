package com.study.expensetracker.infrastructure.budget.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BudgetHistoryRepository extends JpaRepository<BudgetHistoryJpaEntity, String> {
    Optional<BudgetHistoryJpaEntity> findByBudgetIdAndMonthAndYear(String budgetId, int month, int year);
}
