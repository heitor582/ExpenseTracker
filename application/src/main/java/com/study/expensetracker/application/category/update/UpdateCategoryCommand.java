package com.study.expensetracker.application.category.update;

public record UpdateCategoryCommand(
        String id,
        String newName
) {
    public static UpdateCategoryCommand with(final String id,  final String newName) {
        return new UpdateCategoryCommand(id, newName);
    }
}
