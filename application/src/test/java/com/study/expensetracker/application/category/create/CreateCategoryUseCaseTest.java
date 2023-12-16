package com.study.expensetracker.application.category.create;

import com.study.expensetracker.application.UseCaseTest;
import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.category.CategoryType;
import com.study.expensetracker.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
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

class CreateCategoryUseCaseTest extends UseCaseTest {
    @Mock
    private CategoryGateway categoryGateway;
    @Mock
    private BudgetGateway budgetGateway;

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnIt() {
        final var expectedName = "test";
        final var expectedType = CategoryType.WITHDRAW;

        final Budget budget = Budget.newBudget(expectedName, BigDecimal.ZERO);

        when(budgetGateway.findBy(budget.getId())).thenReturn(Optional.of(budget));

        final var command = CreateCategoryCommand.with(expectedName, expectedType.name(), budget.getId().getValue().describeConstable());

        when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

        useCase.execute(command);

        verify(categoryGateway).create(argThat(category ->
                Objects.nonNull(category.getId())
                        && Objects.equals(expectedName, category.getName())
                        && Objects.equals(expectedType, category.getType())
                        && Objects.equals(BigDecimal.ZERO, category.getActualValue())
                        && Objects.equals(budget, category.getBudget().get())
                        && Objects.nonNull(category.getCreatedAt())
                        && Objects.nonNull(category.getUpdatedAt())
        ));
    }

    @ParameterizedTest
    @CsvSource({
            ",name should not be null",
            "'',name should not be empty",
            "aa,name must be between 3 and 255 characters",
            " a,name must be between 3 and 255 characters"
    })
    public void givenAInValidName_whenCallsCreateCategory_shouldThrowsNotificationException(
            final String expectedName,
            final String expectedErrorMessage
    ) {
        final var expectedErrorCount = 1;

        final var expectedType = CategoryType.WITHDRAW;

        final Budget budget = Budget.newBudget("test", BigDecimal.ZERO);

        when(budgetGateway.findBy(budget.getId())).thenReturn(Optional.of(budget));

        final var command = CreateCategoryCommand.with(expectedName, expectedType.name(), budget.getId().getValue().describeConstable());

        final var exception = assertThrows(NotificationException.class, () -> useCase.execute(command));

        assertEquals(expectedErrorCount, exception.getErrors().size());
        assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());

        verify(categoryGateway, never()).create(any());
    }
}