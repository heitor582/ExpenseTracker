package com.study.expensetracker.infrastructure.configuration.category;

import com.study.expensetracker.application.category.create.CreateCategoryUseCase;
import com.study.expensetracker.application.category.create.DefaultCreateCategoryUseCase;
import com.study.expensetracker.application.category.retrieve.get.DefaultFindCategoryByIdUseCase;
import com.study.expensetracker.application.category.retrieve.get.FindCategoryByIdUseCase;
import com.study.expensetracker.application.category.update.DefaultUpdateCategoryUseCase;
import com.study.expensetracker.application.category.update.UpdateCategoryUseCase;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CategoryUseCaseConfig {
    private final CategoryGateway categoryGateway;
    private final BudgetGateway budgetGateway;

    public CategoryUseCaseConfig(final CategoryGateway categoryGateway, final BudgetGateway budgetGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.budgetGateway = Objects.requireNonNull(budgetGateway);
    }

    @Bean
    public FindCategoryByIdUseCase findCategoryByIdUseCase() {
        return new DefaultFindCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway, budgetGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }
}
