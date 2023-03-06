package de.cofinpro.guessing.controller;

import de.cofinpro.guessing.io.ConsolePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuessingRoundIT {

    @Mock
    private Scanner scanner;

    @Mock
    private ConsolePrinter printer;

    private GuessingRound guessingRound;

    @BeforeEach
    void setup() {
        guessingRound = new GuessingRound(printer, scanner);
    }

    @Test
    void stage1_example1_modified() {
        when(scanner.nextLine()).thenReturn("CAT", "  Yeah");
        guessingRound.start();
        verify(printer).printInfo("Is it {}?", "a cat");
        verify(printer).printInfo("You answered: {}\n", "Yes");
    }

    @Test
    void stage1_example2() {
        when(scanner.nextLine()).thenReturn("unicorn", "Oops..", "Nope");
        guessingRound.start();
        verify(printer).printInfo("Is it {}?", "an unicorn");
        verify(printer).printInfo("You answered: {}\n", "No");
        verify(printer, times(3)).printInfo(anyString(), anyString());
        verify(printer, times(3)).printInfo(anyString());
    }

    @Test
    void stage1_example3() {
        when(scanner.nextLine()).thenReturn("a unicorn", "Sure!");
        guessingRound.start();
        verify(printer).printInfo("Is it {}?", "a unicorn");
        verify(printer).printInfo("You answered: {}\n", "Yes");
    }
}