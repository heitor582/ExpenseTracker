package com.study.expensetracker.domain.category;


import java.util.List;
import java.util.Optional;

public interface CategoryGateway {
    Category create(final Category category);
    Category update(final Category category);
    List<Category> list();
    Optional<Category> findBy(final CategoryID categoryId);
}
