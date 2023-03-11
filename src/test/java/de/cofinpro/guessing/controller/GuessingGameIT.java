package de.cofinpro.guessing.controller;

import de.cofinpro.guessing.io.ConsolePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Scanner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
class GuessingGameIT {

    @Mock
    private Scanner scanner;

    @Mock
    private ConsolePrinter printer;

    private GuessingGame guessingGame;

    @BeforeEach
    void setup() {
        guessingGame = new GuessingGame(printer, scanner);
    }

    @Test
    void stage3_example() {
        when(scanner.nextLine()).thenReturn("a CAT", "", "No!", "a shark", "it is a mammal", "nope", "no way");
        guessingGame.start();
        verify(printer).printInfo("Is it a cat?");
        verify(printer).printInfo("Is the statement correct for a shark?");
        verify(printer).printInfo("Would you like to play again?");
    }

    @Test
    void stage3_otherGame() {
        when(scanner.nextLine()).thenReturn("a CAT", "", "No!", "a shark", "it is a mammal", "nope",
                "yeah", "", "No", "nope", "the ant", "it is an insect", "yes", "n");
        guessingGame.start();
        verify(printer).printInfo("Is it a cat?");
        verify(printer).printInfo(" - {}", "Is it an insect?");
        verify(printer).printInfo("Is it a mammal?");
        verify(printer).printInfo("Is the statement correct for a shark?");
        verify(printer).printInfo("Is the statement correct for an ant?");
        verify(printer, times(2)).printInfo("Would you like to play again?");
        verify(printer, times(2)).printInfo("I give up. What animal do you have in mind?");
    }
}