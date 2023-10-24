package com.study.expensetracker.application.category.retrieve.get;

import com.study.expensetracker.application.UseCaseTest;
import com.study.expensetracker.application.category.create.DefaultCreateCategoryUseCase;
import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.budget.BudgetGateway;
import com.study.expensetracker.domain.budget.BudgetID;
import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryGateway;
import com.study.expensetracker.domain.category.CategoryID;
import com.study.expensetracker.domain.category.CategoryType;
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

class FindCategoryByIdUseCaseTest extends UseCaseTest {
    @Mock
    private CategoryGateway categoryGateway;

    @InjectMocks
    private DefaultFindCategoryByIdUseCase useCase;


    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnIt() {
        final Budget budget = Budget.newBudget("test", BigDecimal.ZERO);
        final var category = Category.newCategory("test", CategoryType.WITHDRAW, budget);
        final var id = category.getId();

        when(categoryGateway.findBy(id)).thenReturn(Optional.of(category));

        final var output = useCase.execute(id.getValue());

        assertEquals(output.id(), category.getId());
        assertEquals(output.name(), category.getName());
        assertEquals(output.type(), category.getType());
        assertEquals(output.budgetID(), category.getBudget().getId());
        assertEquals(output.actualValue(), category.getActualValue());
        assertEquals(output.createdAt(), category.getCreatedAt());
        assertEquals(output.updatedAt(), category.getUpdatedAt());

        verify(categoryGateway, only()).findBy(eq(id));
    }

    @Test
    public void givenAnInvalidId_whenCallsGetCategory_shouldThrowsNotFoundException() {
        final var id = CategoryID.unique();
        final var expectedErrorMessage = "%s with ID %s was not found".formatted(Category.class.getSimpleName(), id.getValue());

        when(categoryGateway.findBy(id)).thenReturn(Optional.empty());

        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(id.getValue()));

        assertEquals(expectedErrorMessage, exception.getMessage());

        verify(categoryGateway, only()).findBy(eq(id));
    }
}