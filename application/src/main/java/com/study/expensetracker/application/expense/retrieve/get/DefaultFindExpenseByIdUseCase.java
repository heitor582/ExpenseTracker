package com.study.expensetracker.application.expense.retrieve.get;

import com.study.expensetracker.domain.exceptions.NotFoundException;
import com.study.expensetracker.domain.expense.Expense;
import com.study.expensetracker.domain.expense.ExpenseGateway;
import com.study.expensetracker.domain.expense.ExpenseID;

import java.util.Objects;

public class DefaultFindExpenseByIdUseCase extends FindExpenseByIdUseCase {
    private final ExpenseGateway expenseGateway;

    public DefaultFindExpenseByIdUseCase(final ExpenseGateway expenseGateway) {
        this.expenseGateway = Objects.requireNonNull(expenseGateway);
    }

    @Override
    public ExpenseOutput execute(final String id) {
        final ExpenseID expenseID = ExpenseID.from(id);
        return this.expenseGateway.findBy(expenseID).map(ExpenseOutput::from)
                .orElseThrow(() -> NotFoundException.with(Expense.class, expenseID));
    }
}
