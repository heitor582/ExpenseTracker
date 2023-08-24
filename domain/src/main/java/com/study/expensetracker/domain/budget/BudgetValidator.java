package com.study.expensetracker.domain.budget;

import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.validation.Error;
import com.study.expensetracker.domain.validation.ValidationHandler;
import com.study.expensetracker.domain.validation.Validator;

public class BudgetValidator extends Validator {
    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 255;
    private final Budget budget;
    public BudgetValidator(final Budget budget, final ValidationHandler handler) {
        super(handler);
        this.budget = budget;
    }

    @Override
    public void validate() {
    }
}
