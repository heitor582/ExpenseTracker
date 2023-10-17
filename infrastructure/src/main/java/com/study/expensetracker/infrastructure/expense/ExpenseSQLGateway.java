package com.study.expensetracker.infrastructure.expense;

import com.study.expensetracker.domain.expense.Expense;
import com.study.expensetracker.domain.expense.ExpenseGateway;
import com.study.expensetracker.domain.expense.ExpenseID;
import com.study.expensetracker.infrastructure.expense.persistence.ExpenseJpaEntity;
import com.study.expensetracker.infrastructure.expense.persistence.ExpenseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ExpenseSQLGateway implements ExpenseGateway {
    private final ExpenseRepository repository;

    public ExpenseSQLGateway(final ExpenseRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Expense create(final Expense expense) {
        return this.repository.save(ExpenseJpaEntity.from(expense)).toAggregate();
    }

    @Override
    public List<Expense> list() {
        return this.repository.findAll().stream().map(ExpenseJpaEntity::toAggregate).toList();
    }

    @Override
    public Optional<Expense> findBy(final ExpenseID expenseID) {
        return this.repository.findById(expenseID.getValue()).map(ExpenseJpaEntity::toAggregate);
    }
}
