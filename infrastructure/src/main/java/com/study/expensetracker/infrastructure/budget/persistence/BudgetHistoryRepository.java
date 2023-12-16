package com.study.expensetracker.infrastructure.budget.persistence;

import com.study.expensetracker.infrastructure.category.persistence.CategoryHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//#TODO Review this
@Repository
public interface BudgetHistoryRepository extends JpaRepository<BudgetHistoryJpaEntity, String> {
    Optional<BudgetHistoryJpaEntity> findByIdAndMonthAndYear(String budgetId, int month, int year);
}
