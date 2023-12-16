package com.study.expensetracker.infrastructure.expense;

import com.study.expensetracker.domain.expense.Expense;
import com.study.expensetracker.domain.expense.ExpenseGateway;
import com.study.expensetracker.domain.expense.ExpenseID;
import com.study.expensetracker.domain.pagination.Pagination;
import com.study.expensetracker.domain.pagination.SearchQuery;
import com.study.expensetracker.infrastructure.configuration.annotations.InvoiceCreatedQueue;
import com.study.expensetracker.infrastructure.expense.persistence.ExpenseJpaEntity;
import com.study.expensetracker.infrastructure.expense.persistence.ExpenseRepository;
import com.study.expensetracker.infrastructure.services.EventService;
import com.study.expensetracker.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ExpenseSQLGateway implements ExpenseGateway {
    private final ExpenseRepository repository;
    private final EventService eventService;

    public ExpenseSQLGateway(final ExpenseRepository repository, @InvoiceCreatedQueue EventService eventService) {
        this.repository = Objects.requireNonNull(repository);
        this.eventService = eventService;
    }

    @Override
    public Expense create(final Expense expense) {
        final Expense newExpense = this.repository.save(ExpenseJpaEntity.from(expense)).toAggregate();
        newExpense.onInvoiceCreated();
        newExpense.publishDomainEvents(this.eventService::send);
        return newExpense;
    }

    @Override
    public Pagination<Expense> list(final SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var specification = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(terms -> SpecificationUtils
                        .<ExpenseJpaEntity>like("name", terms))
                .orElse(null);

        final var pageResult = this.repository.findAll(Specification.where(specification), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(ExpenseJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public Optional<Expense> findBy(final ExpenseID expenseID) {
        return this.repository.findById(expenseID.getValue()).map(ExpenseJpaEntity::toAggregate);
    }
}
