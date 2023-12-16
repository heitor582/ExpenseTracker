package com.study.expensetracker.application.expense.create;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.budget.BudgetHistory;
import com.study.expensetracker.domain.budget.BudgetHistoryGateway;
import com.study.expensetracker.domain.budget.BudgetID;
import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.category.CategoryHistory;
import com.study.expensetracker.domain.category.CategoryHistoryGateway;
import com.study.expensetracker.domain.category.CategoryID;
import com.study.expensetracker.domain.exceptions.NotFoundException;
import com.study.expensetracker.domain.exceptions.NotificationException;
import com.study.expensetracker.domain.expense.Expense;
import com.study.expensetracker.domain.expense.ExpenseGateway;
import com.study.expensetracker.domain.utils.InstantUtils;
import com.study.expensetracker.domain.validation.handler.Notification;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.Calendar;
import java.util.Objects;

public class DefaultCreateExpenseUseCase extends CreateExpenseUseCase{
    private final ExpenseGateway expenseGateway;
    private final CategoryGateway categoryGateway;

    public DefaultCreateExpenseUseCase(
            final ExpenseGateway expenseGateway,
            final CategoryGateway categoryGateway
    ) {
        this.expenseGateway = Objects.requireNonNull(expenseGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CreateExpenseOutput execute(final CreateExpenseCommand command) {
        final Instant now = InstantUtils.now();
        final CategoryID categoryID = CategoryID.from(command.categoryId());
        final Category category = categoryGateway.findBy(categoryID)
                .orElseThrow(() -> NotFoundException.with(Category.class, categoryID));

        final Notification notification = Notification.create();

        final Expense expense = notification.validate(() -> Expense.newExpense(
                command.name(),
                command.description(),
                command.amount(),
                category,
                Instant.parse(command.createdAt().orElse(now.toString()))
        ));

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate Expense", notification);
        }

        return CreateExpenseOutput.from(this.expenseGateway.create(expense));
    }
}
