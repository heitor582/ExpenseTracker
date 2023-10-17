package com.study.expensetracker.application.category.retrieve.get;

import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.category.CategoryID;
import com.study.expensetracker.domain.exceptions.NotFoundException;

import java.util.Objects;

public class DefaultFindCategoryByIdUseCase extends FindCategoryByIdUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultFindCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }
    @Override
    public CategoryOutput execute(final String id) {
        final CategoryID categoryID = CategoryID.from(id);
        return this.categoryGateway.findBy(categoryID).map(CategoryOutput::from)
                .orElseThrow(() -> NotFoundException.with(Category.class, categoryID));
    }
}
