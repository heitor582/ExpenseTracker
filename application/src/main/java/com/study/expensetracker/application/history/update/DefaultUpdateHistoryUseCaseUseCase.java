package com.study.expensetracker.application.history.update;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.budget.BudgetHistory;
import com.study.expensetracker.domain.budget.BudgetHistoryGateway;
import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.category.CategoryHistory;
import com.study.expensetracker.domain.category.CategoryHistoryGateway;
import com.study.expensetracker.domain.utils.InstantUtils;

import java.util.Calendar;
import java.util.List;

public class DefaultUpdateHistoryUseCaseUseCase extends UpdateHistoryUseCase {
    private final BudgetGateway budgetGateway;
    private final CategoryGateway categoryGateway;
    private final BudgetHistoryGateway budgetHistoryGateway;
    private final CategoryHistoryGateway categoryHistoryGateway;

    public DefaultUpdateHistoryUseCaseUseCase(
            final BudgetGateway budgetGateway,
            final CategoryGateway categoryGateway,
            final BudgetHistoryGateway budgetHistoryGateway,
            final CategoryHistoryGateway categoryHistoryGateway
    ) {
        this.budgetGateway = budgetGateway;
        this.categoryGateway = categoryGateway;
        this.budgetHistoryGateway = budgetHistoryGateway;
        this.categoryHistoryGateway = categoryHistoryGateway;
    }


    @Override
    public Void execute() {
        final int month = InstantUtils.getCalendar().get(Calendar.MONTH);
        final int year = InstantUtils.getCalendar().get(Calendar.YEAR);

        updateBudgetHistory(month, year);
        updateCategoryHistory(month, year);

        return null;
    }

    public void updateBudgetHistory(final int month, final int year) {
        final List<Budget> oldBudgets = budgetGateway.findALl();
        oldBudgets.forEach(budget -> {
            final BudgetHistory newBudgetHistory = budgetHistoryGateway.findByBudgetID(budget.getId(), month, year)
                    .orElse(BudgetHistory.newBudgetHistory(budget.getId(), month, year, budget.getActualValue(), budget.getMaxValue()));
            budgetHistoryGateway.update(newBudgetHistory);
            budget.update(newBudgetHistory.getActualValue(), newBudgetHistory.getMaxValue());
        });

        budgetGateway.update(oldBudgets);
    }

    public void updateCategoryHistory(final int month, final int year) {
        final List<Category> oldCategories = categoryGateway.findALl();
        oldCategories.forEach(category -> {
            final CategoryHistory newCategoryHistory = categoryHistoryGateway.findByCategoryID(category.getId(), month, year)
                    .orElse(CategoryHistory.newCategoryHistory(category.getId(), month, year, category.getActualValue()));
            categoryHistoryGateway.update(newCategoryHistory);
            category.update(newCategoryHistory.getActualValue());
        });

        categoryGateway.update(oldCategories);
    }
}
