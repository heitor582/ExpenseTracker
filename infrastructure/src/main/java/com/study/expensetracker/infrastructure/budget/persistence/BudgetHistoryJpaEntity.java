package com.study.expensetracker.infrastructure.budget.persistence;

import com.study.expensetracker.domain.budget.BudgetHistory;
import com.study.expensetracker.domain.budget.BudgetID;
import com.study.expensetracker.infrastructure.configuration.annotations.GeneratedJpaOnly;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

//#TODO Review this
@Entity
@Table(name = "budgets_history")
public class BudgetHistoryJpaEntity {
    @Id
    private String id;
    @Column(nullable = false)
    private int month;
    @Column(nullable = false)
    private int year;
    @Column(nullable = false)
    private BigDecimal actualValue;
    @Column(nullable = false)
    private BigDecimal maxValue;

    @GeneratedJpaOnly
    public BudgetHistoryJpaEntity() {}

    public BudgetHistoryJpaEntity(
            final String id,
            final int month,
            final int year,
            final BigDecimal actualValue,
            final BigDecimal maxValue
    ) {
        this.id = id;
        this.month = month;
        this.year = year;
        this.actualValue = actualValue;
        this.maxValue = maxValue;
    }

    public static BudgetHistoryJpaEntity from(final BudgetHistory history){
        return new BudgetHistoryJpaEntity(
                history.getId().getValue(),
                history.getMonth(),
                history.getYear(),
                history.getActualValue(),
                history.getMaxValue()
        );
    }

    public BudgetHistory toAggregate() {
        return BudgetHistory.newBudgetHistory(
                BudgetID.from(this.id),
                this.month,
                this.year,
                this.actualValue,
                this.maxValue
        );
    }
}
