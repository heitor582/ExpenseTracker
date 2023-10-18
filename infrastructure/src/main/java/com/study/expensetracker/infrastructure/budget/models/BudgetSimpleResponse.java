package com.study.expensetracker.infrastructure.budget.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.expensetracker.application.budget.create.CreateBudgetOutput;
import com.study.expensetracker.application.budget.update.UpdateBudgetOutput;

public record BudgetSimpleResponse(
        @JsonProperty("id") String id
) {
    public static BudgetSimpleResponse from(final String id){
        return new BudgetSimpleResponse(id);
    }

    public static BudgetSimpleResponse from(final CreateBudgetOutput output){
        return from(output.id());
    }

    public static BudgetSimpleResponse from(final UpdateBudgetOutput output){
        return from(output.id());
    }
}
