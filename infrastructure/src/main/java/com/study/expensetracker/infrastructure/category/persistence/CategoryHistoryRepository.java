package com.study.expensetracker.infrastructure.category.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryHistoryRepository extends JpaRepository<CategoryHistoryJpaEntity, String> {
    Optional<CategoryHistoryJpaEntity> findByCategoryIdAndMonthAndYear(String categoryId, int month, int year);
}
