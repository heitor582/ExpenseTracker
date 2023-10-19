package com.study.expensetracker.domain.budget;

import com.study.expensetracker.domain.validation.Error;
import com.study.expensetracker.domain.validation.ValidationHandler;
import com.study.expensetracker.domain.validation.Validator;

import java.math.BigDecimal;

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
        checkNameConstraints();
        checkMaxValueConstraints();
    }

    private void checkNameConstraints() {
        final String name = this.budget.getName();
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

    private void checkMaxValueConstraints() {
        final BigDecimal maxValue = this.budget.getMaxValue();

        if(maxValue.signum() < 0){
            this.validationHandler().append(new Error("max value cannot be negative"));
        }
    }
}
