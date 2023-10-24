package com.study.expensetracker.application.category.retrieve.list;

import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.pagination.Pagination;
import com.study.expensetracker.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListCategoryUseCase extends ListCategoryUseCase{
    private final CategoryGateway categoryGateway;

    public DefaultListCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final SearchQuery query) {
        return this.categoryGateway.list(query).map(CategoryListOutput::from);
    }
}
