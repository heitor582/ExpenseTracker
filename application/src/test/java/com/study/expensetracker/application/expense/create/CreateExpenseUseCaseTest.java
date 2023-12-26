package com.study.expensetracker.application.expense.create;

import com.study.expensetracker.application.UseCaseTest;
import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.category.CategoryType;
import com.study.expensetracker.domain.exceptions.NotificationException;
import com.study.expensetracker.domain.expense.ExpenseGateway;
import com.study.expensetracker.domain.expense.PaymentMethod;
import com.study.expensetracker.domain.utils.InstantUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateExpenseUseCaseTest extends UseCaseTest {
    @Mock
    private CategoryGateway categoryGateway;
    @Mock
    private ExpenseGateway expenseGateway;
    @InjectMocks
    private DefaultCreateExpenseUseCase useCase;

    @Test
    public void givenAValidParams_whenCallsNewExpense_shouldReturnIt() {
        final var expectedName = "test";
        final var expectedDescription = "test";
        final var expectedAmount = BigDecimal.valueOf(20L);
        final var expectedCreatedAt = Instant.parse("2023-07-26T14:13:46.401Z");

        final var expectedCategory = Category.newCategory("test", CategoryType.WITHDRAW, Optional.of(Budget.newBudget("test", BigDecimal.valueOf(100L))));

        when(categoryGateway.findBy(expectedCategory.getId())).thenReturn(Optional.of(expectedCategory));

        final var command = CreateExpenseCommand.with(
                expectedName,
                expectedDescription,
                expectedCreatedAt.toString(),
                PaymentMethod.PIX,
                expectedAmount,
                expectedCategory.getId().getValue()

        );

        when(expenseGateway.create(any())).thenAnswer(returnsFirstArg());

        useCase.execute(command);

        verify(expenseGateway).create(argThat(expense ->
                Objects.nonNull(expense.getId())
                && Objects.equals(expectedName, expense.getName())
                && Objects.equals(expectedDescription, expense.getDescription())
                && Objects.equals(expectedAmount, expense.getAmount())
                && Objects.equals(expectedCategory, expense.getCategory())
                && Objects.equals(expectedCreatedAt, expense.getCreatedAt())
        ));
    }

    @Test
    public void givenAValidParams_whenCallsNewExpenseWithoutACreatedDate_shouldReturnIt() {
        final var expectedName = "test";
        final var expectedDescription = "test";
        final var expectedAmount = BigDecimal.valueOf(20L);

        final var expectedCategory = Category.newCategory("test", CategoryType.WITHDRAW, Optional.of(Budget.newBudget("test", BigDecimal.valueOf(100L))));

        when(categoryGateway.findBy(expectedCategory.getId())).thenReturn(Optional.of(expectedCategory));

        final var command = CreateExpenseCommand.with(
                expectedName,
                expectedDescription,
                null,
                PaymentMethod.PIX,
                expectedAmount,
                expectedCategory.getId().getValue()

        );

        when(expenseGateway.create(any())).thenAnswer(returnsFirstArg());

        useCase.execute(command);

        verify(expenseGateway).create(argThat(expense ->
                Objects.nonNull(expense.getId())
                        && Objects.equals(expectedName, expense.getName())
                        && Objects.equals(expectedDescription, expense.getDescription())
                        && Objects.equals(expectedAmount, expense.getAmount())
                        && Objects.equals(expectedCategory, expense.getCategory())
                        && Objects.nonNull(expense.getCreatedAt())
        ));
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

        when(categoryGateway.findBy(expectedCategory.getId())).thenReturn(Optional.of(expectedCategory));

        final var command = CreateExpenseCommand.with(
                expectedName,
                "",
                InstantUtils.now().toString(),
                PaymentMethod.PIX,
                BigDecimal.ZERO,
                expectedCategory.getId().getValue()

        );

        final var exception = assertThrows(NotificationException.class, () -> useCase.execute(command));

        assertEquals(expectedErrorCount, exception.getErrors().size());
        assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());

        verify(expenseGateway, never()).create(any());
        verify(categoryGateway, never()).update((Category) any());
    }

}