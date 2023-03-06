package de.cofinpro.guessing.controller;

import de.cofinpro.guessing.io.ConsolePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Scanner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
class LearningRoundIT {

    @Mock
    private Scanner scanner;

    @Mock
    private ConsolePrinter printer;

    private LearningRound learningRound;

    @BeforeEach
    void setup() {
        learningRound = new LearningRound(printer, scanner);
    }

    @Test
    void stage1_example1_modified() {
        when(scanner.nextLine()).thenReturn("CAT", "  Yeah");
        learningRound.start();
        verify(printer).printInfo("Is it {}?", "a cat");
        verify(printer).printInfo("You answered: {}\n", "Yes");
    }

    @Test
    void stage1_example2() {
        when(scanner.nextLine()).thenReturn("unicorn", "Oops..", "Nope");
        learningRound.start();
        verify(printer).printInfo("Is it {}?", "an unicorn");
        verify(printer).printInfo("You answered: {}\n", "No");
        verify(printer, times(3)).printInfo(anyString(), anyString());
        verify(printer, times(3)).printInfo(anyString());
    }

    @Test
    void stage1_example3() {
        when(scanner.nextLine()).thenReturn("a unicorn", "Sure!");
        learningRound.start();
        verify(printer).printInfo("Is it {}?", "a unicorn");
        verify(printer).printInfo("You answered: {}\n", "Yes");
    }
}