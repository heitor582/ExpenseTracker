package com.study.expensetracker.infrastructure.category.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.expensetracker.application.category.create.CreateCategoryOutput;
import com.study.expensetracker.application.category.update.UpdateCategoryOutput;

public record CategorySimpleResponse(
        @JsonProperty("id") String id
) {
    public static CategorySimpleResponse from(final String id) {
        return new CategorySimpleResponse(id);
    }

    public static CategorySimpleResponse from(final CreateCategoryOutput output) {
        return from(output.id());
    }

    public static CategorySimpleResponse from(final UpdateCategoryOutput output) {
        return from(output.id());
    }
}
