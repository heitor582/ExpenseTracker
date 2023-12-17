package com.study.expensetracker.infrastructure.budget.persistence;

import com.study.expensetracker.domain.budget.BudgetHistory;
import com.study.expensetracker.domain.budget.BudgetID;
import com.study.expensetracker.infrastructure.configuration.annotations.GeneratedJpaOnly;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "budgets_history")
public class BudgetHistoryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "budget_id", nullable = false)
    private String budgetId;
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
            final Long id,
            final String budgetId,
            final int month,
            final int year,
            final BigDecimal actualValue,
            final BigDecimal maxValue
    ) {
        this.id = id;
        this.budgetId = budgetId;
        this.month = month;
        this.year = year;
        this.actualValue = actualValue;
        this.maxValue = maxValue;
    }

    public static BudgetHistoryJpaEntity from(final BudgetHistory history){
        return new BudgetHistoryJpaEntity(
                history.getId(),
                history.getAggregateId().getValue(),
                history.getMonth(),
                history.getYear(),
                history.getActualValue(),
                history.getMaxValue()
        );
    }

    public BudgetHistory toAggregate() {
        return BudgetHistory.with(
                this.id,
                BudgetID.from(this.budgetId),
                this.month,
                this.year,
                this.actualValue,
                this.maxValue
        );
    }
}
