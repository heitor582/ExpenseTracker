package com.study.expensetracker.infrastructure.api.controllers;

import com.study.expensetracker.application.history.update.UpdateHistoryUseCase;
import com.study.expensetracker.infrastructure.api.HistoryAPI;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class HistoryController implements HistoryAPI {
    private final UpdateHistoryUseCase updateHistoryUseCase;

    public HistoryController(final UpdateHistoryUseCase updateHistoryUseCase) {
        this.updateHistoryUseCase = Objects.requireNonNull(updateHistoryUseCase);
    }

    @Override
    public void update() {
        this.updateHistoryUseCase.execute();
    }
}
