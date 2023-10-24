package com.study.expensetracker.application.budget.retrieve.list;

import com.study.expensetracker.application.UseCase;
import com.study.expensetracker.domain.pagination.Pagination;
import com.study.expensetracker.domain.pagination.SearchQuery;

public abstract class ListBudgetUseCase extends UseCase<SearchQuery, Pagination<BudgetListOutput>> {
}
