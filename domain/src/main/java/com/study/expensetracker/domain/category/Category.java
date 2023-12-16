package com.study.expensetracker.domain.category;

import com.study.expensetracker.domain.AggregateRoot;
import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.exceptions.NotificationException;
import com.study.expensetracker.domain.utils.InstantUtils;
import com.study.expensetracker.domain.validation.Error;
import com.study.expensetracker.domain.validation.ValidationHandler;
import com.study.expensetracker.domain.validation.handler.Notification;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public class Category extends AggregateRoot<CategoryID> {
    private String name;
    private final CategoryType type;
    private BigDecimal actualValue;
    private final Optional<Budget> budget;
    private final Instant createdAt;
    private Instant updatedAt;

    private Category(
            final CategoryID categoryID,
            final String name,
            final CategoryType type,
            final BigDecimal actualValue,
            final Optional<Budget> budget,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        super(categoryID);
        this.type = type;
        this.name = name;
        this.actualValue = actualValue;
        this.budget = budget;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        selfValidate("Failed to create a Aggregate Category");
    }

    public static Category with(
            final CategoryID categoryID,
            final String name,
            final CategoryType type,
            final BigDecimal actualValue,
            final Optional<Budget> budget,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new Category(
                categoryID,
                name,
                type,
                actualValue,
                budget,
                createdAt,
                updatedAt
        );
    }

    public static Category newCategory(
            final String name,
            final CategoryType type,
            final Optional<Budget> budget
    ) {
        final Instant now = InstantUtils.now();
        return new Category(
                CategoryID.unique(),
                name,
                type,
                BigDecimal.ZERO,
                budget,
                now,
                now
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public Category update(final String name) {
        this.name = name;
        this.update();
        return this;
    }

    public Category update(final BigDecimal actualValue) {
        this.actualValue = actualValue;
        this.update();
        return this;
    }

    public Category addValue(final BigDecimal value) {
        if(value.signum() < 0) {
            final String errorMessage = "Cannot add a negative number to a category";
            throw new NotificationException(errorMessage, Notification.create().append(new Error(errorMessage)));
        }

        this.actualValue = actualValue.add(value);
        budget.ifPresent(b -> b.addValue(value));
        update();
        return this;
    }

    private void update() {
        this.updatedAt = InstantUtils.now();
        selfValidate("Failed to update an Aggregate Category");
    }

    public String getName() {
        return name;
    }

    public CategoryType getType() {
        return type;
    }
    public BigDecimal getActualValue() {
        return actualValue;
    }
    public Optional<Budget> getBudget() {
        return budget;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
