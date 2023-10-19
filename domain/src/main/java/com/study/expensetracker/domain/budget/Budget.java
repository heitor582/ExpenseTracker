package com.study.expensetracker.domain.budget;

import com.study.expensetracker.domain.AggregateRoot;
import com.study.expensetracker.domain.exceptions.NotificationException;
import com.study.expensetracker.domain.utils.InstantUtils;
import com.study.expensetracker.domain.validation.Error;
import com.study.expensetracker.domain.validation.ValidationHandler;
import com.study.expensetracker.domain.validation.handler.Notification;

import java.math.BigDecimal;
import java.time.Instant;

public class Budget extends AggregateRoot<BudgetID> {
    private String name;
    private BigDecimal actualValue;
    private final BigDecimal maxValue;
    private final Instant createdAt;
    private Instant updatedAt;

    private Budget(
            final BudgetID budgetID,
            final String name,
            final BigDecimal actualValue,
            final BigDecimal maxValue,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        super(budgetID);
        this.name = name;
        this.actualValue = actualValue;
        this.maxValue = maxValue;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        selfValidate("Failed to create a Aggregate Budget");
    }

    public static Budget newBudget(final String name, final BigDecimal maxValue){
        final Instant now = InstantUtils.now();
        return new Budget(
                BudgetID.unique(),
                name,
                BigDecimal.ZERO,
                maxValue,
                now,
                now
        );
    }

    public static Budget with(
            final BudgetID budgetID,
            final String name,
            final BigDecimal actualValue,
            final BigDecimal maxValue,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new Budget(budgetID, name, actualValue, maxValue, createdAt, updatedAt);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new BudgetValidator(this, handler).validate();
    }

    public Budget update(final String name) {
        this.name = name;
        this.update();
        return this;
    }

    public Budget addValue(final BigDecimal value) {
        if(value.signum() < 0) {
            final String errorMessage = "Cannot add a negative number to a budget";
            throw new NotificationException(errorMessage, Notification.create().append(new Error(errorMessage)));
        }

        this.actualValue = this.actualValue.add(value);
        this.update();
        return this;
    }

    private void update() {
        this.updatedAt = InstantUtils.now();
        selfValidate("Failed to update an Aggregate Budget");
    }

    public String getName() {
        return name;
    }
    public BigDecimal getActualValue() {
        return actualValue;
    }
    public BigDecimal getMaxValue() {
        return maxValue;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
