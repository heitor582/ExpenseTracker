package com.study.expensetracker.infrastructure.configuration.usecases;

import com.study.expensetracker.application.expense.create.CreateExpenseUseCase;
import com.study.expensetracker.application.expense.create.DefaultCreateExpenseUseCase;
import com.study.expensetracker.application.expense.retrieve.get.DefaultFindExpenseByIdUseCase;
import com.study.expensetracker.application.expense.retrieve.get.FindExpenseByIdUseCase;
import com.study.expensetracker.application.expense.retrieve.list.DefaultListExpenseUseCase;
import com.study.expensetracker.application.expense.retrieve.list.ListExpenseUseCase;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.expense.ExpenseGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ExpenseUseCaseConfig {
    private final ExpenseGateway expenseGateway;
    private final CategoryGateway categoryGateway;


    public ExpenseUseCaseConfig(
            final ExpenseGateway expenseGateway,
            final CategoryGateway categoryGateway
    ) {
        this.expenseGateway = Objects.requireNonNull(expenseGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Bean
    public CreateExpenseUseCase createExpenseUseCase(){
        return new DefaultCreateExpenseUseCase(expenseGateway, categoryGateway);
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
