package com.study.expensetracker.infrastructure.category;

import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.category.CategoryID;
import com.study.expensetracker.infrastructure.category.persistence.CategoryJpaEntity;
import com.study.expensetracker.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class CategorySQLGateway implements CategoryGateway {
    private final CategoryRepository repository;

    public CategorySQLGateway(final CategoryRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Category create(final Category category) {
        return save(category);
    }

    @Override
    public Category update(final Category category) {
        return save(category);
    }

    @Override
    public List<Category> list() {
        return this.repository.findAll().stream().map(CategoryJpaEntity::toAggregate).toList();
    }

    @Override
    public Optional<Category> findBy(final CategoryID categoryId) {
        return this.repository.findById(categoryId.getValue()).map(CategoryJpaEntity::toAggregate);
    }

    private Category save(final Category category){
        return this.repository.save(CategoryJpaEntity.from(category)).toAggregate();
    }
}
