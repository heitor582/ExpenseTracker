package com.study.expensetracker.application.expense.retrieve.list;

import com.study.expensetracker.application.UseCase;
import com.study.expensetracker.domain.pagination.Pagination;
import com.study.expensetracker.domain.pagination.SearchQuery;

public abstract class ListExpenseUseCase extends UseCase<SearchQuery, Pagination<ExpenseListOutput>> {
}
