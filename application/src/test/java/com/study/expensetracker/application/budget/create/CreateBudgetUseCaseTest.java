package com.study.expensetracker.application.budget.create;

import com.study.expensetracker.application.UseCaseTest;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateBudgetUseCaseTest extends UseCaseTest {
    @Mock
    private BudgetGateway budgetGateway;

    @InjectMocks
    private DefaultCreateBudgetUseCase useCase;

    @Test
    public void givenAValidCommand_whenCallsCreateBudget_shouldReturnIt() {
        final var expectedName = "test";
        final var expectedMaxValue = BigDecimal.valueOf(20L);
        final var expectedActualValue = BigDecimal.ZERO;

        final var command = CreateBudgetCommand.with(expectedName, expectedMaxValue);

        when(budgetGateway.create(any())).thenAnswer(returnsFirstArg());

        useCase.execute(command);

        verify(budgetGateway).create(argThat(budget ->
                Objects.nonNull(budget.getId())
                    && Objects.equals(expectedName, budget.getName())
                    && Objects.equals(expectedMaxValue, budget.getMaxValue())
                    && Objects.equals(expectedActualValue, budget.getActualValue())
                    && Objects.nonNull(budget.getCreatedAt())
                    && Objects.nonNull(budget.getUpdatedAt())
        ));
    }

    @ParameterizedTest
    @CsvSource({
            ",1,name should not be null",
            "'',1,name should not be empty",
            "aa,1,name must be between 3 and 255 characters",
            " a,1,name must be between 3 and 255 characters",
            "aaa,-1,max value cannot be negative",
    })
    public void givenAInValidName_whenCallsCreateBudget_shouldThrowsNotificationException(
            final String expectedName,
            final BigDecimal maxValue,
            final String expectedErrorMessage
    ) {
        final var expectedErrorCount = 1;

        final var command = CreateBudgetCommand.with(expectedName, maxValue);

        final var exception = assertThrows(NotificationException.class, () -> useCase.execute(command));

        assertEquals(expectedErrorCount, exception.getErrors().size());
        assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());

        verify(budgetGateway, never()).create(any());
    }
}