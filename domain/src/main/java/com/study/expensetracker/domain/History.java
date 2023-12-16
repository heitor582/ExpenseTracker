package com.study.expensetracker.domain;

import com.study.expensetracker.domain.exceptions.NotificationException;
import com.study.expensetracker.domain.validation.Error;
import com.study.expensetracker.domain.validation.handler.Notification;

import java.math.BigDecimal;

//#TODO Review this
public abstract class History<ID extends Identifier> {
    private final ID id;
    private final int month;
    private final int year;
    protected BigDecimal actualValue;

    protected History(
            ID id,
            int month,
            int year,
            BigDecimal actualValue
    ) {
        this.id = id;
        this.month = month;
        this.year = year;
        this.actualValue = actualValue;
    }

    public void addValue(final BigDecimal value) {
        if(value.signum() < 0) {
            final String errorMessage = "Cannot add a negative number";
            throw new NotificationException(errorMessage, Notification.create().append(new Error(errorMessage)));
        }

        this.actualValue = actualValue.add(value);
    }

    public ID getId() {
        return id;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public BigDecimal getActualValue() {
        return actualValue;
    }
}
