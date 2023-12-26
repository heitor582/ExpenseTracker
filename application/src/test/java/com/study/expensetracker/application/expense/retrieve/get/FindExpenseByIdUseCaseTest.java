package com.study.expensetracker.application.expense.retrieve.get;

import com.study.expensetracker.application.UseCaseTest;
import com.study.expensetracker.domain.budget.Budget;
import com.study.expensetracker.domain.category.Category;
import com.study.expensetracker.domain.category.CategoryType;
import com.study.expensetracker.domain.exceptions.NotFoundException;
import com.study.expensetracker.domain.expense.Expense;
import com.study.expensetracker.domain.expense.ExpenseGateway;
import com.study.expensetracker.domain.expense.ExpenseID;
import com.study.expensetracker.domain.expense.PaymentMethod;
import com.study.expensetracker.domain.utils.InstantUtils;
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

class FindExpenseByIdUseCaseTest extends UseCaseTest {
    @Mock
    private ExpenseGateway expenseGateway;
    @InjectMocks
    private DefaultFindExpenseByIdUseCase useCase;

    @Test
    public void givenAValidId_whenCallsGetExpense_shouldReturnIt() {
        final Budget budget = Budget.newBudget("test", BigDecimal.ZERO);
        final var category = Category.newCategory("test", CategoryType.WITHDRAW, Optional.of(budget));
        final var expense = Expense.newExpense("test", "", BigDecimal.ZERO, PaymentMethod.PIX, category, InstantUtils.now());
        final var id = expense.getId();

        when(expenseGateway.findBy(id)).thenReturn(Optional.of(expense));

        final var output = useCase.execute(id.getValue());

        assertEquals(output.id(), expense.getId());
        assertEquals(output.name(), expense.getName());
        assertEquals(output.description(), expense.getDescription());
        assertEquals(output.amount(), expense.getAmount());
        assertEquals(output.categoryID(), expense.getCategory().getId());
        assertEquals(output.createdAt(), expense.getCreatedAt());

        verify(expenseGateway, only()).findBy(eq(id));
    }

    @Test
    public void givenAnInvalidId_whenCallsGetExpense_shouldThrowsNotFoundException() {
        final var id = ExpenseID.unique();
        final var expectedErrorMessage = "%s with ID %s was not found".formatted(Expense.class.getSimpleName(), id.getValue());

        when(expenseGateway.findBy(id)).thenReturn(Optional.empty());

        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(id.getValue()));

        assertEquals(expectedErrorMessage, exception.getMessage());

        verify(expenseGateway, only()).findBy(eq(id));
    }
}