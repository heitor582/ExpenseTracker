package com.study.expensetracker.infrastructure.configuration.usecases;

import com.study.expensetracker.application.history.create.CreateHistoryUseCase;
import com.study.expensetracker.application.history.create.DefaultCreateHistoryUseCaseUseCase;
import com.study.expensetracker.application.history.update.DefaultUpdateHistoryUseCaseUseCase;
import com.study.expensetracker.application.history.update.UpdateHistoryUseCase;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.budget.BudgetHistoryGateway;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.category.CategoryHistoryGateway;
import com.study.expensetracker.domain.expense.ExpenseGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class HistoryUseCaseConfig {
    private final ExpenseGateway expenseGateway;
    private final CategoryGateway categoryGateway;
    private final BudgetGateway budgetGateway;
    private final CategoryHistoryGateway categoryHistoryGateway;
    private final BudgetHistoryGateway budgetHistoryGateway;

    public HistoryUseCaseConfig(
            ExpenseGateway expenseGateway, final CategoryGateway categoryGateway,
            final BudgetGateway budgetGateway,
            final CategoryHistoryGateway categoryHistoryGateway,
            final BudgetHistoryGateway budgetHistoryGateway
    ) {
        this.expenseGateway = Objects.requireNonNull(expenseGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.budgetGateway = Objects.requireNonNull(budgetGateway);
        this.categoryHistoryGateway = Objects.requireNonNull(categoryHistoryGateway);
        this.budgetHistoryGateway = Objects.requireNonNull(budgetHistoryGateway);
    }

    @Bean
    public UpdateHistoryUseCase updateHistory() {
        return new DefaultUpdateHistoryUseCaseUseCase(budgetGateway, categoryGateway, budgetHistoryGateway, categoryHistoryGateway);
    }

    @Bean
    public CreateHistoryUseCase createHistory() {
        return new DefaultCreateHistoryUseCaseUseCase(budgetGateway, categoryGateway, budgetHistoryGateway, categoryHistoryGateway, expenseGateway);
    }
}
