package com.study.expensetracker.infrastructure.configuration.budget;

import com.study.expensetracker.application.budget.create.CreateBudgetUseCase;
import com.study.expensetracker.application.budget.create.DefaultCreateBudgetUseCase;
import com.study.expensetracker.application.budget.retrieve.get.DefaultFindBudgetByIdUseCase;
import com.study.expensetracker.application.budget.retrieve.get.FindBudgetByIdUseCase;
import com.study.expensetracker.application.budget.retrieve.list.DefaultListBudgetUseCase;
import com.study.expensetracker.application.budget.retrieve.list.ListBudgetUseCase;
import com.study.expensetracker.application.budget.update.DefaultUpdateBudgetUseCase;
import com.study.expensetracker.application.budget.update.UpdateBudgetUseCase;
import com.study.expensetracker.domain.budget.BudgetGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class BudgetUseCaseConfig {
    private final BudgetGateway budgetGateway;

    public BudgetUseCaseConfig(final BudgetGateway budgetGateway) {
        this.budgetGateway = Objects.requireNonNull(budgetGateway);
    }

    @Bean
    public CreateBudgetUseCase createBudgetUseCase() {
        return new DefaultCreateBudgetUseCase(budgetGateway);
    }

    @Bean
    public FindBudgetByIdUseCase findBudgetByIdUseCase() {
        return new DefaultFindBudgetByIdUseCase(budgetGateway);
    }

    @Bean
    public UpdateBudgetUseCase updateBudgetUseCase() {
        return new DefaultUpdateBudgetUseCase(budgetGateway);
    }

    @Bean
    public ListBudgetUseCase listBudgetUseCase() {
        return new DefaultListBudgetUseCase(budgetGateway);
    }
}
