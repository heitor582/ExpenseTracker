package com.study.expensetracker.infrastructure.configuration.expense;

import com.study.expensetracker.application.expense.create.CreateExpenseUseCase;
import com.study.expensetracker.application.expense.create.DefaultCreateExpenseUseCase;
import com.study.expensetracker.application.expense.retrieve.get.DefaultFindExpenseByIdUseCase;
import com.study.expensetracker.application.expense.retrieve.get.FindExpenseByIdUseCase;
import com.study.expensetracker.application.expense.retrieve.list.DefaultListExpenseUseCase;
import com.study.expensetracker.application.expense.retrieve.list.ListExpenseUseCase;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.expense.ExpenseGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ExpenseUseCaseConfig {
    private final ExpenseGateway expenseGateway;
    private final CategoryGateway categoryGateway;
    private final BudgetGateway budgetGateway;

    public ExpenseUseCaseConfig(final ExpenseGateway expenseGateway, final CategoryGateway categoryGateway, final BudgetGateway budgetGateway) {
        this.expenseGateway = Objects.requireNonNull(expenseGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.budgetGateway = Objects.requireNonNull(budgetGateway);
    }

    @Bean
    public CreateExpenseUseCase createExpenseUseCase(){
        return new DefaultCreateExpenseUseCase(expenseGateway, categoryGateway, budgetGateway);
    }

    @Bean
    public FindExpenseByIdUseCase findExpenseByIdUseCase() {
        return new DefaultFindExpenseByIdUseCase(expenseGateway);
    }

    @Bean
    public ListExpenseUseCase listExpenseUseCase() {
        return new DefaultListExpenseUseCase(expenseGateway);
    }
}
