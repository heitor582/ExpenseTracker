package com.study.expensetracker.application.budget.update;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.budget.BudgetID;
import com.study.expensetracker.domain.exceptions.NotFoundException;
import com.study.expensetracker.domain.exceptions.NotificationException;
import com.study.expensetracker.domain.validation.handler.Notification;

import java.util.Objects;

public class DefaultUpdateBudgetUseCase extends UpdateBudgetUseCase {
    private final BudgetGateway budgetGateway;

    public DefaultUpdateBudgetUseCase(final BudgetGateway budgetGateway) {
        this.budgetGateway = Objects.requireNonNull(budgetGateway);
    }

    @Override
    public UpdateBudgetOutput execute(final UpdateBudgetCommand command) {
        final BudgetID budgetID = BudgetID.from(command.id());
        final Budget budget = this.budgetGateway.findBy(budgetID)
                .orElseThrow(() -> NotFoundException.with(Budget.class, budgetID));

        final Notification notification = Notification.create();

        notification.validate(() -> budget.update(command.newName()));

        if (notification.hasError()) {
            throw new NotificationException("Could not update Aggregate Budget %s".formatted(budgetID), notification);
        }

        return UpdateBudgetOutput.from(this.budgetGateway.update(budget));
    }
}
