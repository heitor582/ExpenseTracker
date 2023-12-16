package com.study.expensetracker.domain.category;

import java.util.List;
import java.util.Optional;

public interface CategoryHistoryGateway {
    void update(final CategoryHistory categoryHistory);
    Optional<CategoryHistory> findByCategoryID(final CategoryID categoryID, final int month, final int year);
}
