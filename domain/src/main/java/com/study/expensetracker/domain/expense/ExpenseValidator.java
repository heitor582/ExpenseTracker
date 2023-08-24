package com.study.expensetracker.domain.expense;

import com.study.expensetracker.domain.validation.Error;
import com.study.expensetracker.domain.validation.ValidationHandler;
import com.study.expensetracker.domain.validation.Validator;

public class ExpenseValidator extends Validator {
    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 255;
    private final Expense expense;
    public ExpenseValidator(final Expense expense, final ValidationHandler handler) {
        super(handler);
        this.expense = expense;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final String name = this.expense.getName();
        if (name == null) {
            this.validationHandler().append(new Error("name should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("name should not be empty"));
            return;
        }

        final int nameLength = name.trim().length();

        if (nameLength < NAME_MIN_LENGTH || nameLength > NAME_MAX_LENGTH) {
            this.validationHandler().append(new Error("name must be between 3 and 255 characters"));
        }
    }
}
