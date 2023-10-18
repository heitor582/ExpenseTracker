package com.study.expensetracker.application.category.update;

import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.category.CategoryID;
import com.study.expensetracker.domain.exceptions.NotFoundException;
import com.study.expensetracker.domain.exceptions.NotificationException;
import com.study.expensetracker.domain.validation.handler.Notification;

import java.util.Objects;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }


    @Override
    public UpdateCategoryOutput execute(final UpdateCategoryCommand command) {
        final CategoryID categoryID = CategoryID.from(command.id());
        final Category category = this.categoryGateway.findBy(categoryID)
                .orElseThrow(() -> NotFoundException.with(Category.class, categoryID));

        final Notification notification = Notification.create();

        notification.validate(() -> category.update(command.newType()));

        if (notification.hasError()) {
            throw new NotificationException("Could not update Aggregate Category %s".formatted(category), notification);
        }

        return UpdateCategoryOutput.from(this.categoryGateway.update(category));
    }
}
