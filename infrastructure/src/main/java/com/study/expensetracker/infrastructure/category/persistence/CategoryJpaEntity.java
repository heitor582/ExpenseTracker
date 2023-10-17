package com.study.expensetracker.infrastructure.category.persistence;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryID;
import com.study.expensetracker.domain.category.CategoryType;
import com.study.expensetracker.infrastructure.budget.persistence.BudgetJpaEntity;
import com.study.expensetracker.infrastructure.configuration.GeneratedJpaOnly;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "categories")
public class CategoryJpaEntity {
    @Id
    private String id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType type;
    @Column(nullable = false)
    private BigDecimal actualValue;
    @ManyToOne
    @JoinColumn(name = "budget_id", nullable = false)
    private BudgetJpaEntity budget;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant updatedAt;

    @GeneratedJpaOnly
    public CategoryJpaEntity() {
    }

    public CategoryJpaEntity(
            final String id,
            final CategoryType type,
            final BigDecimal actualValue,
            final BudgetJpaEntity budget,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        this.id = id;
        this.type = type;
        this.actualValue = actualValue;
        this.budget = budget;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CategoryJpaEntity from(final Category category) {
        return new CategoryJpaEntity(
                category.getId().getValue(),
                category.getType(),
                category.getActualValue(),
                BudgetJpaEntity.from(category.getBudget()),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

    public Category toAggregate(){
        return Category.with(
                CategoryID.from(id),
                type,
                actualValue,
                budget.toAggregate(),
                createdAt,
                updatedAt
        );
    }
}
