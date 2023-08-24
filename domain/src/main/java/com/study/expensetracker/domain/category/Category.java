package com.study.expensetracker.domain.category;

public enum Category {
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

    Category(final Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.getType();
    }

    private enum Type {
        WITHDRAW {
            @Override
            protected boolean getValue() {
                return false;
            }
        },
        DEPOSIT {
            @Override
            protected boolean getValue() {
                return true;
            }
        };

        protected abstract boolean getValue();
    }
}
