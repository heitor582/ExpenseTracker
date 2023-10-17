package com.study.expensetracker.domain.category;

import com.study.expensetracker.domain.AggregateRoot;
import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.utils.InstantUtils;
import com.study.expensetracker.domain.validation.ValidationHandler;

import java.math.BigDecimal;
import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> {
    private final CategoryType type;
    private BigDecimal actualValue;
    private final Budget budget;
    private final Instant createdAt;
    private Instant updatedAt;

    private Category(
            final CategoryID categoryID,
            final CategoryType type,
            final BigDecimal actualValue,
            final Budget budget,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        super(categoryID);
        this.type = type;
        this.actualValue = actualValue;
        this.budget = budget;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        selfValidate("Failed to create a Aggregate Category");
    }

    public static Category with(
            final CategoryID categoryID,
            final CategoryType type,
            final BigDecimal actualValue,
            final Budget budget,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new Category(
                categoryID,
                type,
                actualValue,
                budget,
                createdAt,
                updatedAt
        );
    }

    public static Category newCategory(
            final CategoryType type,
            final BigDecimal actualValue,
            final Budget budget
    ) {
        return new Category(
                CategoryID.unique(),
                type,
                actualValue,
                budget,
                InstantUtils.now(),
                InstantUtils.now()
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {

    }

    public Category addValue(final BigDecimal value) {
        this.actualValue = actualValue.add(value);
        budget.addValue(value);
        update();
        return this;
    }

    private void update() {
        this.updatedAt = InstantUtils.now();
        selfValidate("Failed to update an Aggregate Category");
    }

    public CategoryType getType() {
        return type;
    }
    public BigDecimal getActualValue() {
        return actualValue;
    }
    public Budget getBudget() {
        return budget;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
