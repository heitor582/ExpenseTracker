package com.study.expensetracker.infrastructure.expense.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.expensetracker.application.expense.create.CreateExpenseOutput;

public record ExpenseSimpleResponse(
        @JsonProperty("id") String id
) {
    public static ExpenseSimpleResponse from(final String id){
        return new ExpenseSimpleResponse(id);
    }
    public static ExpenseSimpleResponse from(final CreateExpenseOutput output){
        return from(output.id());
    }
}
