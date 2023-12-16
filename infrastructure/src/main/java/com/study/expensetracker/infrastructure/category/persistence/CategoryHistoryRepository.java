package com.study.expensetracker.infrastructure.category.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//#TODO Review this

@Repository
public interface CategoryHistoryRepository extends JpaRepository<CategoryHistoryJpaEntity, String> {
    Optional<CategoryHistoryJpaEntity> findByIdAndMonthAndYear(String categoryId, int month, int year);
}
