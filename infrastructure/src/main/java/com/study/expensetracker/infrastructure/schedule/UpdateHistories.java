package com.study.expensetracker.infrastructure.schedule;


import com.study.expensetracker.application.history.update.UpdateHistoryUseCase;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class UpdateHistories {
    private final UpdateHistoryUseCase updateHistoryUseCase;

    public UpdateHistories(final UpdateHistoryUseCase updateHistoryUseCase) {
        this.updateHistoryUseCase = updateHistoryUseCase;
    }

    @Scheduled(cron = "0 1 1 * *")
    public void resetAggregateByHistoryEveryFirstDayOfTheMonth() {
        this.updateHistoryUseCase.execute();
    }
}
