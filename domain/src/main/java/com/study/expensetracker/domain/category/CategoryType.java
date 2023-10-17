package com.study.expensetracker.domain.category;

public enum CategoryType {
    SHOPPING(Type.WITHDRAW),
    FOOD(Type.WITHDRAW),
    EDUCATION(Type.WITHDRAW),
    BILLS(Type.WITHDRAW),
    UTILITIES(Type.WITHDRAW),
    HEALTH(Type.WITHDRAW),
    ENTERTAINMENTS(Type.WITHDRAW),
    TRANSPORT(Type.WITHDRAW),
    GIFT(Type.WITHDRAW),
    OTHER_OUTPUTS(Type.WITHDRAW),
    SALARY(Type.DEPOSIT),
    OTHER_INCOMES(Type.DEPOSIT);

    private Type type;

    CategoryType(final Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.getType();
    }

    private enum Type {
        WITHDRAW,
        DEPOSIT
    }
}
