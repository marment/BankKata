package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Spy;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountAcceptanceTest {
    @Spy
    private Account account;

    private ByteArrayOutputStream outputStream;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

@Test
    void testDepositReflectsInBalance() {
        // Arrange
        account.deposit(1000);

        // Act
        account.printStatement();

        // Assert
        verify(account, times(1)).deposit(1000);

        String expectedOutput =
                "date || credit || debit || balance\n" +
                        "2025-01-31 || 1000 || || 1000";

        assertEquals(expectedOutput, outputStream.toString().trim());
    }


    @Test
    void testWithdrawalReflectsInBalance() {
        // Arrange
        account.deposit(1000);
        account.withdraw(500);

        // Act
        account.printStatement();

        // Assert
        verify(account, times(1)).withdraw(500);

        String expectedOutput =
                "date || credit || debit || balance\n" +
                        "2025-01-31 || 1000 || || 1000\n" +
                        "2025-01-31 || || -500 || 500";

        assertEquals(expectedOutput, outputStream.toString().trim());
    }


@Test
    void testDepositsAndWithdrawalsReflectInStatement() {
        // Arrange
        account.deposit(1000);
        account.deposit(2000);
        account.withdraw(500);

        // Act
        account.printStatement();

        // Assert
        verify(account, times(1)).deposit(1000);
        verify(account, times(1)).deposit(2000);
        verify(account, times(1)).withdraw(500);

        String expectedOutput =
                "date || credit || debit || balance\n" +
                        "2025-01-31 || 1000 || || 1000\n" +
                        "2025-01-31 || 2000 || || 3000\n" +
                        "2025-01-31 || || -500 || 2500";

        assertEquals(expectedOutput, outputStream.toString().trim());
    }
}
