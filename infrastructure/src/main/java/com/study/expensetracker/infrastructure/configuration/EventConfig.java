package com.study.expensetracker.infrastructure.configuration;

import com.study.expensetracker.infrastructure.configuration.annotations.InvoiceCreatedQueue;
import com.study.expensetracker.infrastructure.configuration.properties.amqp.QueueProperties;
import com.study.expensetracker.infrastructure.services.EventService;
import com.study.expensetracker.infrastructure.services.impl.RabbitEventService;
import com.study.expensetracker.infrastructure.services.local.InMemoryEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
public class EventConfig {

    @Bean
    @InvoiceCreatedQueue
    @Profile({"development"})
    EventService localInvoiceCreatedEventService() {
        return new InMemoryEventService();
    }

    @Bean
    @InvoiceCreatedQueue
    @ConditionalOnMissingBean
    EventService InvoiceCreatedEventService(
            @InvoiceCreatedQueue final QueueProperties props,
            final RabbitOperations ops
    ) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}