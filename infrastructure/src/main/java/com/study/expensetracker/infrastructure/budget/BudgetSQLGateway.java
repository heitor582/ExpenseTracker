package com.study.expensetracker.infrastructure.budget;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.budget.BudgetID;
import com.study.expensetracker.infrastructure.budget.persistence.BudgetJpaEntity;
import com.study.expensetracker.infrastructure.budget.persistence.BudgetRepository;
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
    public List<Budget> list() {
        return this.repository.findAll().stream().map(BudgetJpaEntity::toAggregate).toList();
    }

    @Override
    public Optional<Budget> findBy(final BudgetID budgetID) {
        return this.repository.findById(budgetID.getValue()).map(BudgetJpaEntity::toAggregate);
    }

    private Budget save(final Budget budget){
        return this.repository.save(BudgetJpaEntity.from(budget)).toAggregate();
    }
}
