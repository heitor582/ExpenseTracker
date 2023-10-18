package com.study.expensetracker.infrastructure.api.controllers;

import com.study.expensetracker.application.expense.create.CreateExpenseCommand;
import com.study.expensetracker.application.expense.create.CreateExpenseOutput;
import com.study.expensetracker.application.expense.create.CreateExpenseUseCase;
import com.study.expensetracker.application.expense.retrieve.get.FindExpenseByIdUseCase;
import com.study.expensetracker.infrastructure.api.ExpenseAPI;
import com.study.expensetracker.infrastructure.expense.models.CreateExpenseRequest;
import com.study.expensetracker.infrastructure.expense.models.ExpenseResponse;
import com.study.expensetracker.infrastructure.expense.models.ExpenseSimpleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class ExpenseController implements ExpenseAPI {
    private final CreateExpenseUseCase createExpenseUseCase;
    private final FindExpenseByIdUseCase findExpenseByIdUseCase;

    public ExpenseController(final CreateExpenseUseCase createExpenseUseCase, final FindExpenseByIdUseCase findExpenseByIdUseCase) {
        this.createExpenseUseCase = Objects.requireNonNull(createExpenseUseCase);
        this.findExpenseByIdUseCase = Objects.requireNonNull(findExpenseByIdUseCase);
    }

    @Override
    public ResponseEntity<ExpenseSimpleResponse> create(final CreateExpenseRequest input) {
        final CreateExpenseCommand command = CreateExpenseCommand.with(
                input.name(),
                input.description(),
                input.createdAt(),
                input.amount(),
                input.categoryID()
        );

        final CreateExpenseOutput output = this.createExpenseUseCase.execute(command);

        return ResponseEntity.created(URI.create("/expenses/" + output.id())).body(ExpenseSimpleResponse.from(output));
    }

    @Override
    public ResponseEntity<ExpenseResponse> getById(String id) {
        return ResponseEntity.ok(ExpenseResponse.from(this.findExpenseByIdUseCase.execute(id)));
    }
}
