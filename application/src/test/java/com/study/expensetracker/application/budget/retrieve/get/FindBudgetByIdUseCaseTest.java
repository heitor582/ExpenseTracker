package com.study.expensetracker.application.budget.retrieve.get;

import com.study.expensetracker.application.UseCaseTest;
import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.budget.BudgetID;
import com.study.expensetracker.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindBudgetByIdUseCaseTest extends UseCaseTest {
    @Mock
    private BudgetGateway budgetGateway;

    @InjectMocks
    private DefaultFindBudgetByIdUseCase useCase;

    @Test
    public void givenAValidId_whenCallsGetBudget_shouldReturnIt() {
        final var budget = Budget.newBudget("test", BigDecimal.ZERO);
        final var id = budget.getId();

        when(budgetGateway.findBy(id)).thenReturn(Optional.of(budget));

        final var output = useCase.execute(id.getValue());

        assertEquals(output.id(), budget.getId());
        assertEquals(output.name(), budget.getName());
        assertEquals(output.maxValue(), budget.getMaxValue());
        assertEquals(output.actualValue(), budget.getActualValue());
        assertEquals(output.createdAt(), budget.getCreatedAt());
        assertEquals(output.updatedAt(), budget.getUpdatedAt());

        verify(budgetGateway, only()).findBy(eq(id));
    }

    @Test
    public void givenAnInvalidId_whenCallsGetBudget_shouldThrowsNotFoundException() {
        final var id = BudgetID.unique();
        final var expectedErrorMessage = "%s with ID %s was not found".formatted(Budget.class.getSimpleName(), id.getValue());

        when(budgetGateway.findBy(id)).thenReturn(Optional.empty());

        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(id.getValue()));

        assertEquals(expectedErrorMessage, exception.getMessage());

        verify(budgetGateway, only()).findBy(eq(id));
    }
}