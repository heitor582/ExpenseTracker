package com.study.expensetracker.domain.expense;

import com.study.expensetracker.domain.AggregateRoot;
import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.events.DomainEvent;
import com.study.expensetracker.domain.validation.ValidationHandler;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class Expense extends AggregateRoot<ExpenseID> {
    private final String name;
    private final String description;
    private final BigDecimal amount;
    private final Category category;
    private final PaymentMethod paymentMethod;
    private final Instant createdAt;

    private Expense(
            final ExpenseID id,
            final String name,
            final String description,
            final BigDecimal amount,
            final Category category,
            final PaymentMethod paymentMethod,
            final Instant createdAt,
            final List<DomainEvent> domainEvents
    ) {
        super(id, domainEvents);
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.paymentMethod = paymentMethod;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt should not be null");

        selfValidate("Failed to create a Aggregate Expense");
    }

    public static Expense newExpense(
            final String name,
            final String description,
            final BigDecimal amount,
            final PaymentMethod paymentMethod,
            final Category category,
            final Instant createdAt
    ) {
        return new Expense(ExpenseID.unique(), name, description, amount, category, paymentMethod, createdAt, null);
    }

    public static Expense with(
            final ExpenseID id,
            final String name,
            final String description,
            final BigDecimal amount,
            final PaymentMethod paymentMethod,
            final Category category,
            final Instant createdAt
    ){
        return new Expense(id, name, description, amount, category, paymentMethod, createdAt, null);
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new ExpenseValidator(this, handler).validate();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Category getCategory() {
        return category;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void onInvoiceCreated() {
        this.registerEvent(new InvoiceCreated(id.getValue()));
    }
}
