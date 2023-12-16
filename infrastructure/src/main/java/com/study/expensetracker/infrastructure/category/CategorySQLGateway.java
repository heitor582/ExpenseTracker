package com.study.expensetracker.infrastructure.category;

import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.category.CategoryID;
import com.study.expensetracker.domain.pagination.Pagination;
import com.study.expensetracker.domain.pagination.SearchQuery;
import com.study.expensetracker.infrastructure.category.persistence.CategoryJpaEntity;
import com.study.expensetracker.infrastructure.category.persistence.CategoryRepository;
import com.study.expensetracker.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public List<Category> update(final List<Category> categories) {
        return repository.saveAll(categories.stream().map(CategoryJpaEntity::from).toList())
                .stream().map(CategoryJpaEntity::toAggregate).toList();
    }

    @Override
    public Pagination<Category> list(final SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var specification = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(terms -> SpecificationUtils
                        .<CategoryJpaEntity>like("name", terms))
                .orElse(null);

        final var pageResult = this.repository.findAll(Specification.where(specification), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public Optional<Category> findBy(final CategoryID categoryId) {
        return this.repository.findById(categoryId.getValue()).map(CategoryJpaEntity::toAggregate);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll().stream().map(CategoryJpaEntity::toAggregate).toList();
    }

    @Override
    public List<Category> findALl() {
        return repository.findAll().stream().map(CategoryJpaEntity::toAggregate).toList();
    }

    private Category save(final Category category){
        return this.repository.save(CategoryJpaEntity.from(category)).toAggregate();
    }
}
