package com.study.expensetracker.application.category.create;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.budget.BudgetID;
import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.category.CategoryType;
import com.study.expensetracker.domain.exceptions.NotFoundException;
import com.study.expensetracker.domain.exceptions.NotificationException;
import com.study.expensetracker.domain.validation.handler.Notification;

import java.util.Objects;
import java.util.Optional;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {
    private final CategoryGateway categoryGateway;
    private final BudgetGateway budgetGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway, final BudgetGateway budgetGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.budgetGateway = Objects.requireNonNull(budgetGateway);
    }

    @Override
    public CreateCategoryOutput execute(final CreateCategoryCommand command) {
        final BudgetID budgetID = BudgetID.from(command.budgetId().orElse(""));
        final Optional<Budget> budget = this.budgetGateway.findBy(budgetID);

        final Notification notification = Notification.create();

        final Category category = notification.validate(() -> Category.newCategory(
                command.name(),
                CategoryType.valueOf(command.type()),
                budget
        ));

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate Category", notification);
        }

        return CreateCategoryOutput.from(this.categoryGateway.create(category));
    }
}
