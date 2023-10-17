package com.study.expensetracker.domain.category;

import com.study.expensetracker.domain.validation.ValidationHandler;
import com.study.expensetracker.domain.validation.Validator;

public class CategoryValidator extends Validator {
    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 255;
    private final Category category;
    public CategoryValidator(final Category category, final ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
    }
}
