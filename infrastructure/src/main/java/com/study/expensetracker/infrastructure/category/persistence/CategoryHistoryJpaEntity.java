package com.study.expensetracker.infrastructure.category.persistence;

import com.study.expensetracker.domain.category.CategoryHistory;
import com.study.expensetracker.domain.category.CategoryID;
import com.study.expensetracker.infrastructure.configuration.annotations.GeneratedJpaOnly;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "categories_history")
public class CategoryHistoryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_id", nullable = false)
    private String categoryId;
    @Column(nullable = false)
    private int month;
    @Column(nullable = false)
    private int year;
    @Column(nullable = false)
    private BigDecimal actualValue;

    @GeneratedJpaOnly
    public CategoryHistoryJpaEntity() {}

    public CategoryHistoryJpaEntity(
            final Long id,
            final String categoryId,
            final int month,
            final int year,
            final BigDecimal actualValue
    ) {
        this.id = id;
        this.categoryId = categoryId;
        this.month = month;
        this.year = year;
        this.actualValue = actualValue;
    }

    public static CategoryHistoryJpaEntity from(final CategoryHistory history){
        return new CategoryHistoryJpaEntity(
                history.getId(),
                history.getAggregateId().getValue(),
                history.getMonth(),
                history.getYear(),
                history.getActualValue()
        );
    }

    public CategoryHistory toAggregate() {
        return CategoryHistory.with(
                this.id,
                CategoryID.from(this.categoryId),
                this.month,
                this.year,
                this.actualValue
        );
    }
}
