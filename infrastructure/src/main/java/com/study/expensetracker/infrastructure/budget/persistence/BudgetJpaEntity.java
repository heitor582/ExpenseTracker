package com.study.expensetracker.infrastructure.budget.persistence;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetID;
import com.study.expensetracker.infrastructure.configuration.GeneratedJpaOnly;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "budgets")
public class BudgetJpaEntity {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal actualValue;
    @Column(nullable = false)
    private BigDecimal maxValue;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant updatedAt;

    @GeneratedJpaOnly
    public BudgetJpaEntity() {
    }

    public BudgetJpaEntity(
            final String id,
            final String name,
            final BigDecimal actualValue,
            final BigDecimal maxValue,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.actualValue = actualValue;
        this.maxValue = maxValue;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static BudgetJpaEntity from(final Budget budget) {
        return new BudgetJpaEntity(
                budget.getId().getValue(),
                budget.getName(),
                budget.getActualValue(),
                budget.getMaxValue(),
                budget.getCreatedAt(),
                budget.getUpdatedAt()
        );
    }

    public Budget toAggregate() {
        return Budget.with(
                BudgetID.from(id),
                name,
                actualValue,
                maxValue,
                createdAt,
                updatedAt
        );
    }
}
