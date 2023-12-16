package com.study.expensetracker.infrastructure.expense.persistence;

import com.study.expensetracker.domain.expense.Expense;
import com.study.expensetracker.domain.expense.ExpenseID;
import com.study.expensetracker.infrastructure.category.persistence.CategoryJpaEntity;
import com.study.expensetracker.infrastructure.configuration.annotations.GeneratedJpaOnly;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "expenses")
public class ExpenseJpaEntity {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(length = 4000)
    private String description;
    @Column(nullable = false)
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryJpaEntity category;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant createdAt;

    @GeneratedJpaOnly
    public ExpenseJpaEntity() {
    }

    public ExpenseJpaEntity(
            final String id,
            final String name,
            final String description,
            final BigDecimal amount,
            final CategoryJpaEntity category,
            final Instant createdAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.createdAt = createdAt;
    }

    public static ExpenseJpaEntity from(final Expense expense){
        return new ExpenseJpaEntity(
                expense.getId().getValue(),
                expense.getName(),
                expense.getDescription(),
                expense.getAmount(),
                CategoryJpaEntity.from(expense.getCategory()),
                expense.getCreatedAt()
        );
    }

    public Expense toAggregate() {
        return Expense.with(
                ExpenseID.from(this.id),
                this.name,
                this.description,
                this.amount,
                this.category.toAggregate(),
                this.createdAt
        );
    }
}
