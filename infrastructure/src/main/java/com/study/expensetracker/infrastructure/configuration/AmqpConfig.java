package com.study.expensetracker.infrastructure.configuration;

import com.study.expensetracker.infrastructure.configuration.annotations.InvoiceCreatedQueue;
import com.study.expensetracker.infrastructure.configuration.properties.amqp.QueueProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Bean
    @ConfigurationProperties("amqp.queues.create-invoice")
    @InvoiceCreatedQueue
    QueueProperties invoiceCreatedQueueProperties() {
        return new QueueProperties();
    }

}