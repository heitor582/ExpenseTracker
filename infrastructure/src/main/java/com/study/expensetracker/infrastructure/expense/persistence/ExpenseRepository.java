package com.study.expensetracker.infrastructure.expense.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseJpaEntity, String> {
}
