package com.study.expensetracker.infrastructure.api.controllers;

import com.study.expensetracker.application.category.create.CreateCategoryCommand;
import com.study.expensetracker.application.category.create.CreateCategoryOutput;
import com.study.expensetracker.application.category.create.CreateCategoryUseCase;
import com.study.expensetracker.application.category.retrieve.get.FindCategoryByIdUseCase;
import com.study.expensetracker.application.category.retrieve.list.ListCategoryUseCase;
import com.study.expensetracker.application.category.update.UpdateCategoryCommand;
import com.study.expensetracker.application.category.update.UpdateCategoryUseCase;
import com.study.expensetracker.domain.pagination.Pagination;
import com.study.expensetracker.domain.pagination.SearchQuery;
import com.study.expensetracker.infrastructure.api.CategoryAPI;
import com.study.expensetracker.infrastructure.category.models.CategoryListResponse;
import com.study.expensetracker.infrastructure.category.models.CategoryResponse;
import com.study.expensetracker.infrastructure.category.models.CategorySimpleResponse;
import com.study.expensetracker.infrastructure.category.models.CreateCategoryRequest;
import com.study.expensetracker.infrastructure.category.models.UpdateCategoryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class CategoryController implements CategoryAPI {
    private final CreateCategoryUseCase createCategoryUseCase;
    private final FindCategoryByIdUseCase findCategoryByIdUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final ListCategoryUseCase listCategoryUseCase;

    public CategoryController(
            final CreateCategoryUseCase createCategoryUseCase,
            final FindCategoryByIdUseCase findCategoryByIdUseCase,
            final UpdateCategoryUseCase updateCategoryUseCase,
            final ListCategoryUseCase listCategoryUseCase
    ) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.findCategoryByIdUseCase = Objects.requireNonNull(findCategoryByIdUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.listCategoryUseCase = Objects.requireNonNull(listCategoryUseCase);
    }

    @Override
    public ResponseEntity<CategorySimpleResponse> create(final CreateCategoryRequest input) {
        final CreateCategoryCommand command = CreateCategoryCommand.with(
                input.name(),
                input.type(),
                input.budgetId()
        );

        final CreateCategoryOutput output = this.createCategoryUseCase.execute(command);

        return ResponseEntity.created(URI.create("/categories/" + output.id())).body(CategorySimpleResponse.from(output));
    }

    @Override
    public ResponseEntity<CategoryResponse> getById(final String id) {
        return ResponseEntity.ok(CategoryResponse.from(this.findCategoryByIdUseCase.execute(id)));
    }

    @Override
    public ResponseEntity<CategorySimpleResponse> updateById(final String id, final UpdateCategoryRequest input) {
        return ResponseEntity.ok(CategorySimpleResponse.from(
                this.updateCategoryUseCase.execute(UpdateCategoryCommand.with(id, input.name()))));
    }

    @Override
    public ResponseEntity<Pagination<CategoryListResponse>> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return ResponseEntity.ok(this.listCategoryUseCase.execute(new SearchQuery(page, perPage, search, sort, direction))
                .map(CategoryListResponse::from));
    }
}
