package com.study.expensetracker.domain.category;

import com.study.expensetracker.domain.UnitTest;
import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryTest extends UnitTest {

    @Test
    public void givenAValidParams_whenCallsNewCategory_thenInstantiateIt() {
        final var expectedName = "BILLS";
        final var expectedType = CategoryType.WITHDRAW;
        final var expectedAmount = BigDecimal.ZERO;
        final var expectedBudget = Budget.newBudget("test", BigDecimal.valueOf(100L));

        final var category = Category.newCategory(expectedName, expectedType, Optional.of(expectedBudget));

        assertNotNull(category);
        assertNotNull(category.getId());

        assertEquals(expectedName, category.getName());
        assertEquals(expectedType, category.getType());
        assertEquals(expectedAmount, category.getActualValue());
        assertEquals(expectedBudget, category.getBudget().get());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
        assertEquals(category.getCreatedAt(), category.getUpdatedAt());

    }

    @ParameterizedTest
    @CsvSource({
            ",name should not be null",
            "'',name should not be empty",
            "aa,name must be between 3 and 255 characters",
            " a,name must be between 3 and 255 characters",
    })
    public void givenAnInvalidParams_whenCallsNewCategory_shouldReceivedANotificationException(
            final String expectedName,
            final String expectedErrorMessage
    ){
        final var expectedBudget = Budget.newBudget("test", BigDecimal.valueOf(100L));
        final var expectedErrorCount = 1;

        final var exception = assertThrows(NotificationException.class,
                () -> Category.newCategory(expectedName, CategoryType.WITHDRAW, Optional.of(expectedBudget)));

        assertEquals(expectedErrorCount, exception.getErrors().size());
        assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    public void givenANegativeValue_whenCallsCategoryAddValue_thenShouldThrowNotificationException() {
        final var budget = Budget.newBudget("test", BigDecimal.valueOf(100L));
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Cannot add a negative number to a category";

        final var exception = assertThrows(NotificationException.class, () ->
                Category.newCategory("test", CategoryType.WITHDRAW, Optional.of(budget)).addValue(BigDecimal.valueOf(-1)));

        assertEquals(expectedErrorCount, exception.getErrors().size());
        assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    public void givenAPositiveValue_whenCallsCategoryAddValue_thenShouldNotThrowAnyException() {
        final var budget = Budget.newBudget("test", BigDecimal.valueOf(100L));
        final var expectedActualValue = BigDecimal.valueOf(20L);
        final var category = assertDoesNotThrow(() -> Category.newCategory("test", CategoryType.WITHDRAW, Optional.of(budget)).addValue(expectedActualValue));

        assertEquals(expectedActualValue, category.getActualValue());
        assertEquals(expectedActualValue, budget.getActualValue());
        assertTrue(category.getUpdatedAt().isAfter(category.getCreatedAt()));
    }

    @Test
    public void givenAValidName_whenCallsUpdate_thenShouldNotThrowAnyException() {
        final var budget = Budget.newBudget("test", BigDecimal.valueOf(100L));
        final var expectedName = "test2";
        final var category = assertDoesNotThrow(() -> Category.newCategory(expectedName, CategoryType.WITHDRAW, Optional.of(budget)).update(expectedName));

        assertEquals(expectedName, category.getName());
        assertTrue(category.getUpdatedAt().isAfter(category.getCreatedAt()));
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
        final var budget = Budget.newBudget("test", BigDecimal.valueOf(100L));
        final var expectedErrorCount = 1;

        final var exception = assertThrows(NotificationException.class, () ->
                Category.newCategory("test", CategoryType.WITHDRAW, Optional.of(budget)).update(expectedName));

        assertEquals(expectedErrorCount, exception.getErrors().size());
        assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }
}