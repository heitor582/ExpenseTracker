package com.study.expensetracker.infrastructure.category.persistence;

import com.study.expensetracker.domain.category.CategoryHistory;
import com.study.expensetracker.domain.category.CategoryID;
import com.study.expensetracker.infrastructure.configuration.annotations.GeneratedJpaOnly;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

//#TODO Review this

@Entity
@Table(name = "categories_history")
public class CategoryHistoryJpaEntity {
    @Id
    private String id;
    @Column(nullable = false)
    private int month;
    @Column(nullable = false)
    private int year;
    @Column(nullable = false)
    private BigDecimal actualValue;

    @GeneratedJpaOnly
    public CategoryHistoryJpaEntity() {}

    public CategoryHistoryJpaEntity(
            final String id,
            final int month,
            final int year,
            final BigDecimal actualValue
    ) {
        this.id = id;
        this.month = month;
        this.year = year;
        this.actualValue = actualValue;
    }

    public static CategoryHistoryJpaEntity from(final CategoryHistory history){
        return new CategoryHistoryJpaEntity(
                history.getId().getValue(),
                history.getMonth(),
                history.getYear(),
                history.getActualValue()
        );
    }

    public CategoryHistory toAggregate() {
        return CategoryHistory.newCategoryHistory(
                CategoryID.from(this.id),
                this.month,
                this.year,
                this.actualValue
        );
    }
}
