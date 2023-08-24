package com.study.expensetracker.domain.exceptions;

import com.study.expensetracker.domain.validation.handler.Notification;

public class NotificationException extends DomainException{
    public NotificationException(String message, Notification notification) {
        super(message, notification.getErrors());
    }
}
