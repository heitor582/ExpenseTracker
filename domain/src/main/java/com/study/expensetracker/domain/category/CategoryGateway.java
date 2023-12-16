package com.study.expensetracker.domain.category;


import com.study.expensetracker.domain.pagination.Pagination;
import com.study.expensetracker.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface CategoryGateway {
    Category create(final Category category);
    Category update(final Category category);
    List<Category> update(final List<Category> categories);
    Pagination<Category> list(final SearchQuery query);
    Optional<Category> findBy(final CategoryID categoryId);
    List<Category> findAll();
    List<Category> findALl();
}
