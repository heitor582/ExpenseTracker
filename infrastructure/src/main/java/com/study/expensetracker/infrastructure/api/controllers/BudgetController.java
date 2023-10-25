package com.study.expensetracker.infrastructure.api.controllers;

import com.study.expensetracker.application.budget.create.CreateBudgetCommand;
import com.study.expensetracker.application.budget.create.CreateBudgetOutput;
import com.study.expensetracker.application.budget.create.CreateBudgetUseCase;
import com.study.expensetracker.application.budget.retrieve.get.FindBudgetByIdUseCase;
import com.study.expensetracker.application.budget.retrieve.list.ListBudgetUseCase;
import com.study.expensetracker.application.budget.update.UpdateBudgetCommand;
import com.study.expensetracker.application.budget.update.UpdateBudgetUseCase;
import com.study.expensetracker.domain.pagination.Pagination;
import com.study.expensetracker.domain.pagination.SearchQuery;
import com.study.expensetracker.infrastructure.api.BudgetAPI;
import com.study.expensetracker.infrastructure.budget.models.BudgetListResponse;
import com.study.expensetracker.infrastructure.budget.models.BudgetResponse;
import com.study.expensetracker.infrastructure.budget.models.BudgetSimpleResponse;
import com.study.expensetracker.infrastructure.budget.models.CreateBudgetRequest;
import com.study.expensetracker.infrastructure.budget.models.UpdateBudgetRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class BudgetController implements BudgetAPI {
    private final CreateBudgetUseCase createBudgetUseCase;
    private final FindBudgetByIdUseCase findBudgetByIdUseCase;
    private final UpdateBudgetUseCase updateBudgetUseCase;
    private final ListBudgetUseCase listBudgetUseCase;

    public BudgetController(
            final CreateBudgetUseCase createBudgetUseCase,
            final FindBudgetByIdUseCase findBudgetByIdUseCase,
            final UpdateBudgetUseCase updateBudgetUseCase,
            final ListBudgetUseCase listBudgetUseCase
    ) {
        this.createBudgetUseCase = Objects.requireNonNull(createBudgetUseCase);
        this.findBudgetByIdUseCase = Objects.requireNonNull(findBudgetByIdUseCase);
        this.updateBudgetUseCase = Objects.requireNonNull(updateBudgetUseCase);
        this.listBudgetUseCase = Objects.requireNonNull(listBudgetUseCase);
    }

    @Override
    public ResponseEntity<BudgetSimpleResponse> create(final CreateBudgetRequest input) {
        final CreateBudgetCommand command = CreateBudgetCommand.with(
                input.name(),
                input.maxValue()
        );

        final CreateBudgetOutput output = this.createBudgetUseCase.execute(command);

        return ResponseEntity.created(URI.create("/budgets/" + output.id())).body(BudgetSimpleResponse.from(output));
    }

    @Override
    public ResponseEntity<BudgetResponse> getById(final String id) {
        return ResponseEntity.ok(BudgetResponse.from(this.findBudgetByIdUseCase.execute(id)));
    }

    @Override
    public ResponseEntity<BudgetSimpleResponse> updateById(final String id, final UpdateBudgetRequest input) {
        return ResponseEntity.ok(BudgetSimpleResponse.from(this.updateBudgetUseCase.execute(UpdateBudgetCommand.with(id, input.name()))));
    }

    @Override
    public ResponseEntity<Pagination<BudgetListResponse>> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        final Pagination<BudgetListResponse> response = this.listBudgetUseCase.execute(
                new SearchQuery(page, perPage, search, sort, direction)
        ).map(BudgetListResponse::from);
        return ResponseEntity.ok(response);
    }
}
