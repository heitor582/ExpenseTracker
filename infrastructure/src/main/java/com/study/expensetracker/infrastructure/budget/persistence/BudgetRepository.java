package com.study.expensetracker.infrastructure.budget.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetJpaEntity, String> {
    Page<BudgetJpaEntity> findAll(Specification<BudgetJpaEntity> whereClause, Pageable page);
}
