package com.study.expensetracker.application.budget.create;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.exceptions.NotificationException;
import com.study.expensetracker.domain.validation.handler.Notification;

import java.util.Objects;

public class DefaultCreateBudgetUseCase extends CreateBudgetUseCase {
    private final BudgetGateway budgetGateway;

    public DefaultCreateBudgetUseCase(final BudgetGateway budgetGateway) {
        this.budgetGateway = Objects.requireNonNull(budgetGateway);
    }
    @Override
    public CreateBudgetOutput execute(final CreateBudgetCommand command) {
        final Notification notification = Notification.create();

        final Budget budget = notification.validate(() -> Budget.newBudget(
                command.name(),
                command.maxValue()
        ));

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate Budget", notification);
        }

        return CreateBudgetOutput.from(this.budgetGateway.create(budget));
    }
}
