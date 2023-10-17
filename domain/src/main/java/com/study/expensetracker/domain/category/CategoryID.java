package com.study.expensetracker.domain.category;

import com.study.expensetracker.domain.Identifier;
import com.study.expensetracker.domain.utils.IdUtils;

import java.util.Objects;

public class CategoryID extends Identifier {
    private final String value;

    private CategoryID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static CategoryID from(final String id) {
        return new CategoryID(id);
    }

    public static CategoryID unique() {
        return CategoryID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CategoryID that = (CategoryID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
