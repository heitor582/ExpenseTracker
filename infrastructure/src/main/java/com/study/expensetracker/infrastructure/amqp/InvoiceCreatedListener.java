package com.study.expensetracker.infrastructure.amqp;

import com.study.expensetracker.application.history.create.CreateHistoryUseCase;
import com.study.expensetracker.domain.expense.InvoiceCreated;
import com.study.expensetracker.infrastructure.configuration.json.Json;
import com.study.expensetracker.infrastructure.expense.models.InvoiceCreatedMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class InvoiceCreatedListener {
    static final String LISTENER_ID = "invoiceCreatedListener";

    private final CreateHistoryUseCase createHistoryUseCase;

    public InvoiceCreatedListener(final CreateHistoryUseCase createHistoryUseCase) {
        this.createHistoryUseCase = Objects.requireNonNull(createHistoryUseCase);
    }

    @RabbitListener(id = LISTENER_ID, queues = "${amqp.queues.create-invoice.queue}")
    public void onInvoiceCreatedMessage(@Payload final String message) {
        final var aResult = Json.readValue(message, InvoiceCreatedMessage.class);

        final var aCmd = new InvoiceCreated(
                aResult.id(),
                aResult.occurredOn()
        );

        this.createHistoryUseCase.execute(aCmd);
    }
}