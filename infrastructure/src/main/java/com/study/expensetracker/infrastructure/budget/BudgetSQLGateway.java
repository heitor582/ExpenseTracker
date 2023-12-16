package com.study.expensetracker.infrastructure.budget;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.budget.BudgetID;
import com.study.expensetracker.domain.pagination.Pagination;
import com.study.expensetracker.domain.pagination.SearchQuery;
import com.study.expensetracker.infrastructure.budget.persistence.BudgetJpaEntity;
import com.study.expensetracker.infrastructure.budget.persistence.BudgetRepository;
import com.study.expensetracker.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class BudgetSQLGateway implements BudgetGateway {
    private final BudgetRepository repository;

    public BudgetSQLGateway(final BudgetRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Budget create(final Budget budget) {
        return save(budget);
    }

    @Override
    public Budget update(final Budget budget) {
        return save(budget);
    }

    @Override
    public List<Budget> update(final List<Budget> budget) {
        return repository.saveAll(budget.stream().map(BudgetJpaEntity::from).toList())
                .stream().map(BudgetJpaEntity::toAggregate).toList();
    }

    @Override
    public Pagination<Budget> list(final SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var specification = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(terms -> SpecificationUtils
                        .<BudgetJpaEntity>like("name", terms))
                .orElse(null);

        final var pageResult = this.repository.findAll(Specification.where(specification), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(BudgetJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public Optional<Budget> findBy(final BudgetID budgetID) {
        return this.repository.findById(budgetID.getValue()).map(BudgetJpaEntity::toAggregate);
    }

    @Override
    public List<Budget> findALl() {
        return repository.findAll().stream().map(BudgetJpaEntity::toAggregate).toList();
    }

    private Budget save(final Budget budget){
        return this.repository.save(BudgetJpaEntity.from(budget)).toAggregate();
    }
}
