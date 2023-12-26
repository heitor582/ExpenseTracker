package com.study.expensetracker.domain.expense;

import com.study.expensetracker.domain.UnitTest;
import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryType;
import com.study.expensetracker.domain.exceptions.NotificationException;
import com.study.expensetracker.domain.utils.InstantUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpenseTest extends UnitTest {
    @Test
    public void givenAValidParams_whenCallsNewExpense_thenInstantiateIt() {
        final var expectedName = "test";
        final var expectedDescription = "test";
        final var expectedAmount = BigDecimal.valueOf(20L);
        final var expectedCategory = Category.newCategory("test", CategoryType.WITHDRAW, Optional.of(Budget.newBudget("test", BigDecimal.valueOf(100L))));
        final var expectedCreatedAt = InstantUtils.now();

        final var expense = Expense.newExpense(expectedName, expectedDescription, expectedAmount, PaymentMethod.PIX, expectedCategory, expectedCreatedAt);

        assertNotNull(expense);
        assertNotNull(expense.getId());

        assertEquals(expectedName, expense.getName());
        assertEquals(expectedDescription, expense.getDescription());
        assertEquals(expectedAmount, expense.getAmount());
        assertEquals(expectedCategory, expense.getCategory());
        assertEquals(expectedCreatedAt, expense.getCreatedAt());
    }

    @ParameterizedTest
    @CsvSource({
            ",name should not be null",
            "'',name should not be empty",
            "aa,name must be between 3 and 255 characters",
            " a,name must be between 3 and 255 characters",
    })
    public void givenAnInvalidParams_whenCallsNewExpense_shouldReceivedANotificationException(
            final String expectedName,
            final String expectedErrorMessage
    ){
        final var expectedCategory = Category.newCategory("test", CategoryType.WITHDRAW, Optional.of(Budget.newBudget("test", BigDecimal.valueOf(100L))));
        final var expectedErrorCount = 1;

        final var exception = assertThrows(NotificationException.class,
                () -> Expense.newExpense(expectedName, "", BigDecimal.ZERO, PaymentMethod.PIX, expectedCategory, InstantUtils.now()));

        assertEquals(expectedErrorCount, exception.getErrors().size());
        assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }
}