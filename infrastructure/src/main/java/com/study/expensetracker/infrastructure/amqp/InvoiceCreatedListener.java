package com.study.expensetracker.infrastructure.amqp;

import com.study.expensetracker.application.history.create.CreateHistory;
import com.study.expensetracker.domain.expense.InvoiceCreated;
import com.study.expensetracker.infrastructure.configuration.json.Json;
import com.study.expensetracker.infrastructure.expense.models.InvoiceCreatedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class InvoiceCreatedListener {

    private static final Logger log = LoggerFactory.getLogger(InvoiceCreatedListener.class);

    static final String LISTENER_ID = "invoiceCreatedListener";

    private final CreateHistory createHistory;

    public InvoiceCreatedListener(final CreateHistory createHistory) {
        this.createHistory = Objects.requireNonNull(createHistory);
    }

    @RabbitListener(id = LISTENER_ID, queues = "${amqp.queues.create-invoice.queue}")
    public void onVideoEncodedMessage(@Payload final String message) {
        final var aResult = Json.readValue(message, InvoiceCreatedMessage.class);

        log.error("[message:video.listener.income] [status:completed] [payload:{}]", message);
        final var aCmd = new InvoiceCreated(
                aResult.id(),
                aResult.occurredOn()
        );

        this.createHistory.execute(aCmd);
    }
}