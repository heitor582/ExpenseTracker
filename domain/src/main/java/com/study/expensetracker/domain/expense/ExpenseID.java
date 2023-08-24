package com.study.expensetracker.domain.expense;

import com.study.expensetracker.domain.Identifier;
import com.study.expensetracker.domain.utils.IdUtils;

import java.util.Objects;

public class ExpenseID extends Identifier {
    private final String value;

    private ExpenseID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static ExpenseID from(final String id) {
        return new ExpenseID(id);
    }

    public static ExpenseID unique() {
        return ExpenseID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ExpenseID that = (ExpenseID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
