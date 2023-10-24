package com.study.expensetracker.infrastructure.expense.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseJpaEntity, String> {
    Page<ExpenseJpaEntity> findAll(Specification<ExpenseJpaEntity> whereClause, Pageable page);
}
