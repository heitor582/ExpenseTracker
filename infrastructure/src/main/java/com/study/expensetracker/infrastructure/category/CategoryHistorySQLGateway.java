package com.study.expensetracker.infrastructure.category;

import com.study.expensetracker.domain.category.CategoryHistory;
import com.study.expensetracker.domain.category.CategoryHistoryGateway;
import com.study.expensetracker.domain.category.CategoryID;
import com.study.expensetracker.infrastructure.category.persistence.CategoryHistoryJpaEntity;
import com.study.expensetracker.infrastructure.category.persistence.CategoryHistoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

//#TODO Review this
@Component
public class CategoryHistorySQLGateway implements CategoryHistoryGateway {
    private final CategoryHistoryRepository repository;

    public CategoryHistorySQLGateway(final CategoryHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void update(final CategoryHistory categoryHistory) {
        this.repository.save(CategoryHistoryJpaEntity.from(categoryHistory));
    }

    @Override
    public Optional<CategoryHistory> findByCategoryID(final CategoryID categoryID, final int month, final int year) {
        return this.repository.findByIdAndMonthAndYear(categoryID.getValue(), month, year).map(CategoryHistoryJpaEntity::toAggregate);
    }
}
