package com.study.expensetracker.application.expense.create;

import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.category.CategoryID;
import com.study.expensetracker.domain.exceptions.NotFoundException;
import com.study.expensetracker.domain.exceptions.NotificationException;
import com.study.expensetracker.domain.expense.Expense;
import com.study.expensetracker.domain.expense.ExpenseGateway;
import com.study.expensetracker.domain.utils.InstantUtils;
import com.study.expensetracker.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.Objects;

public class DefaultCreateExpenseUseCase extends CreateExpenseUseCase{
    private final ExpenseGateway expenseGateway;
    private final BudgetGateway budgetGateway;
    private final CategoryGateway categoryGateway;

    public DefaultCreateExpenseUseCase(
            final ExpenseGateway expenseGateway,
            final CategoryGateway categoryGateway,
            final BudgetGateway budgetGateway
    ) {
        this.expenseGateway = Objects.requireNonNull(expenseGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.budgetGateway = budgetGateway;
    }

    @Override
    public CreateExpenseOutput execute(final CreateExpenseCommand command) {

        final CategoryID categoryID = CategoryID.from(command.categoryId());
        final Category category = categoryGateway.findBy(categoryID)
                .orElseThrow(() -> NotFoundException.with(Category.class, categoryID));

        final Notification notification = Notification.create();

        final Expense expense = notification.validate(() -> Expense.newExpense(
                command.name(),
                command.description(),
                command.amount(),
                category,
                Instant.parse(command.createdAt().orElse(InstantUtils.now().toString()))
        ));

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate Expense", notification);
        }

        this.categoryGateway.update(category);
        this.budgetGateway.update(category.getBudget());

        return CreateExpenseOutput.from(this.expenseGateway.create(expense));
    }
}
