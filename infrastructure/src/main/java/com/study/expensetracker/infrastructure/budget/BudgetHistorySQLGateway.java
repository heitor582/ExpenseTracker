package com.study.expensetracker.infrastructure.budget;

import com.study.expensetracker.domain.budget.BudgetHistory;
import com.study.expensetracker.domain.budget.BudgetHistoryGateway;
import com.study.expensetracker.domain.budget.BudgetID;
import com.study.expensetracker.infrastructure.budget.persistence.BudgetHistoryJpaEntity;
import com.study.expensetracker.infrastructure.budget.persistence.BudgetHistoryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

//#TODO Review this
@Component
public class BudgetHistorySQLGateway implements BudgetHistoryGateway {
    private final BudgetHistoryRepository repository;

    public BudgetHistorySQLGateway(final BudgetHistoryRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void update(final BudgetHistory budgetHistory) {
        this.repository.save(BudgetHistoryJpaEntity.from(budgetHistory));
    }

    @Override
    public Optional<BudgetHistory> findByBudgetID(
            final BudgetID id,
            final int month,
            final int year
    ) {
        return this.repository.findByBudgetIdAndMonthAndYear(
                id.getValue(),
                month,
                year
        ).map(BudgetHistoryJpaEntity::toAggregate);
    }
}
