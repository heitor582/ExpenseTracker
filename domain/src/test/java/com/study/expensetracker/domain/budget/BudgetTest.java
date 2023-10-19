package com.study.expensetracker.domain.budget;

import com.study.expensetracker.domain.UnitTest;
import com.study.expensetracker.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BudgetTest extends UnitTest {
    @Test
    public void givenAValidParams_whenCallsNewBudget_thenInstantiateIt() {
        final var expectedName = "test";
        final var expectedMaxValue = BigDecimal.valueOf(20L);

        final var budget = Budget.newBudget(expectedName, expectedMaxValue);

        assertNotNull(budget);
        assertNotNull(budget.getId());

        assertEquals(expectedName, budget.getName());
        assertEquals(expectedMaxValue, budget.getMaxValue());
        assertEquals(BigDecimal.ZERO, budget.getActualValue());
        assertNotNull(budget.getCreatedAt());
        assertNotNull(budget.getUpdatedAt());
        assertEquals(budget.getCreatedAt(), budget.getUpdatedAt());

    }

    @Test
    public void givenANegativeValue_whenCallsBudgetAddValue_thenShouldThrowNotificationException() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Cannot add a negative number to a budget";

        final var exception = assertThrows(NotificationException.class, () ->
                Budget.newBudget("test", BigDecimal.valueOf(20L)).addValue(BigDecimal.valueOf(-1)));

        assertEquals(expectedErrorCount, exception.getErrors().size());
        assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    public void givenAPositiveValue_whenCallsBudgetAddValue_thenShouldNotThrowAnyException() {
        final var expectedActualValue = BigDecimal.valueOf(20L);
        final var budget = assertDoesNotThrow(() -> Budget.newBudget("test", BigDecimal.valueOf(20L)).addValue(expectedActualValue));

        assertEquals(expectedActualValue, budget.getActualValue());
        assertTrue(budget.getUpdatedAt().isAfter(budget.getCreatedAt()));
    }

    @Test
    public void givenAValidName_whenCallsUpdate_thenShouldNotThrowAnyException() {
        final var expectedName = "test2";
        final var budget = assertDoesNotThrow(() -> Budget.newBudget("test", BigDecimal.valueOf(20L)).update(expectedName));

        assertEquals(expectedName, budget.getName());
        assertTrue(budget.getUpdatedAt().isAfter(budget.getCreatedAt()));
    }

    @ParameterizedTest
    @CsvSource({
            ",name should not be null",
            "'',name should not be empty",
            "aa,name must be between 3 and 255 characters",
            " a,name must be between 3 and 255 characters",
    })
    public void givenAnInvalidName_whenCallsUpdate_thenShouldThrowNotificationException(
            final String expectedName,
            final String expectedErrorMessage
    ) {
        final var expectedErrorCount = 1;

        final var exception = assertThrows(NotificationException.class, () ->
                Budget.newBudget("test", BigDecimal.valueOf(20L)).update(expectedName));

        assertEquals(expectedErrorCount, exception.getErrors().size());
        assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @ParameterizedTest
    @CsvSource({
            ",1,name should not be null",
            "'',1,name should not be empty",
            "aa,1,name must be between 3 and 255 characters",
            " a,1,name must be between 3 and 255 characters",
            "aaa,-1,max value cannot be negative",
    })
    public void givenAnInvalidParams_whenCallsNewBudget_shouldReceivedANotificationException(
            final String expectedName,
            final BigDecimal maxValue,
            final String expectedErrorMessage
    ){
        final var expectedErrorCount = 1;

        final var exception = assertThrows(NotificationException.class,
                () -> Budget.newBudget(expectedName, maxValue));

        assertEquals(expectedErrorCount, exception.getErrors().size());
        assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }
}