package com.study.expensetracker.domain.budget;

import com.study.expensetracker.domain.Identifier;
import com.study.expensetracker.domain.utils.IdUtils;

import java.util.Objects;

public class BudgetID extends Identifier {
    private final String value;

    private BudgetID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static BudgetID from(final String id) {
        return new BudgetID(id);
    }

    public static BudgetID unique() {
        return BudgetID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BudgetID that = (BudgetID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
