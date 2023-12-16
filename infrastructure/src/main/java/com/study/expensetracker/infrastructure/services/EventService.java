package com.study.expensetracker.infrastructure.services;

public interface EventService {
    void send(final Object event);
}
