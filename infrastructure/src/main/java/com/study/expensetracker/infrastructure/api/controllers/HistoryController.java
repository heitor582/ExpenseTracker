package com.study.expensetracker.infrastructure.api.controllers;

import com.study.expensetracker.application.history.update.UpdateHistory;
import com.study.expensetracker.infrastructure.api.HistoryAPI;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class HistoryController implements HistoryAPI {
    private final UpdateHistory updateHistory;

    public HistoryController(final UpdateHistory updateHistory) {
        this.updateHistory = Objects.requireNonNull(updateHistory);
    }

    @Override
    public void update() {
        this.updateHistory.execute();
    }
}
