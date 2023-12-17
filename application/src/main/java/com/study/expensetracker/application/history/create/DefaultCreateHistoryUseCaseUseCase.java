package com.study.expensetracker.application.history.create;

import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.budget.BudgetHistory;
import com.study.expensetracker.domain.budget.BudgetHistoryGateway;
import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.category.CategoryHistory;
import com.study.expensetracker.domain.category.CategoryHistoryGateway;
import com.study.expensetracker.domain.exceptions.NotFoundException;
import com.study.expensetracker.domain.expense.Expense;
import com.study.expensetracker.domain.expense.ExpenseGateway;
import com.study.expensetracker.domain.expense.ExpenseID;
import com.study.expensetracker.domain.expense.InvoiceCreated;
import com.study.expensetracker.domain.utils.InstantUtils;

import java.math.BigDecimal;
import java.util.Calendar;

public class DefaultCreateHistoryUseCaseUseCase extends CreateHistoryUseCase {
    private final ExpenseGateway expenseGateway;
    private final BudgetGateway budgetGateway;
    private final CategoryGateway categoryGateway;
    private final BudgetHistoryGateway budgetHistoryGateway;
    private final CategoryHistoryGateway categoryHistoryGateway;

    public DefaultCreateHistoryUseCaseUseCase(
            final BudgetGateway budgetGateway,
            final CategoryGateway categoryGateway,
            final BudgetHistoryGateway budgetHistoryGateway,
            final CategoryHistoryGateway categoryHistoryGateway,
            final ExpenseGateway expenseGateway
    ) {
        this.budgetGateway = budgetGateway;
        this.categoryGateway = categoryGateway;
        this.budgetHistoryGateway = budgetHistoryGateway;
        this.categoryHistoryGateway = categoryHistoryGateway;
        this.expenseGateway = expenseGateway;
    }

    @Override
    public void execute(final InvoiceCreated invoiceCreated) {
        final ExpenseID expenseID = ExpenseID.from(invoiceCreated.id());
        final Expense expense = this.expenseGateway.findBy(expenseID).orElseThrow(() -> NotFoundException.with(Expense.class, expenseID));
        final Category category = expense.getCategory();
        category.addValue(expense.getAmount());

        if(InstantUtils.isTheSameMonthAndYear(expense.getCreatedAt())){
            this.categoryGateway.update(category);
            category.getBudget().ifPresent(this.budgetGateway::update);
        } else {
            final int month = InstantUtils.getCalendar(expense.getCreatedAt()).get(Calendar.MONTH) + 1;
            final int year = InstantUtils.getCalendar(expense.getCreatedAt()).get(Calendar.YEAR);
            category.getBudget().ifPresent(budget -> {
                final BudgetHistory budgetHistory = this.budgetHistoryGateway.findByBudgetID(budget.getId(), month, year)
                        .orElse(BudgetHistory.newBudgetHistory(budget.getId(), month, year, BigDecimal.ZERO, budget.getMaxValue()));
                budgetHistory.addValue(expense.getAmount());
                this.budgetHistoryGateway.update(budgetHistory);
            });

            final CategoryHistory categoryHistory = this.categoryHistoryGateway.findByCategoryID(category.getId(), month, year)
                    .orElse(CategoryHistory.newCategoryHistory(category.getId(), month, year, BigDecimal.ZERO));
            categoryHistory.addValue(expense.getAmount());
            this.categoryHistoryGateway.update(categoryHistory);
        }
    }
}
