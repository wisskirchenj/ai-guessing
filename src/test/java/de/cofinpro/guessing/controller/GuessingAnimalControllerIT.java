package de.cofinpro.guessing.controller;

import de.cofinpro.guessing.io.ConsolePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
class GuessingAnimalControllerIT {

    @Mock
    private Scanner scanner;

    @Mock
    private ConsolePrinter printer;

    private GuessingAnimalController guessingAnimalController;

    @BeforeEach
    void setup() {
        guessingAnimalController = new GuessingAnimalController(printer, scanner);
    }

    @Test
    void stage3_example() throws IOException {
        Files.deleteIfExists(Path.of("animals.yaml"));
        when(scanner.nextLine()).thenReturn("a CAT", "", "No!", "a shark", "it is a mammal", "nope", "no way");
        guessingAnimalController.start(new String[]{"-type", "yaml"});
        verify(printer).printInfo("Is it a cat?");
        verify(printer).printInfo("Is the statement correct for a shark?");
        verify(printer).printInfo("Would you like to play again?");
        var expectedYaml = readTreeFile("src/test/resources/test.yaml");
        var producedYaml = readTreeFile("animals.yaml");
        assertEquals(expectedYaml, producedYaml);
    }

    @Test
    void stage3_otherGame() throws IOException {
        Files.deleteIfExists(Path.of("animals.xml"));
        when(scanner.nextLine()).thenReturn("a CAT", "", "No!", "a shark", "it is a mammal", "nope",
                "yeah", "", "No", "nope", "the ant", "it is an insect", "yes", "n");
        guessingAnimalController.start(new String[]{"-type", "XML"});
        verify(printer).printInfo("Is it a cat?");
        verify(printer).printInfo(" - {}", "Is it an insect?");
        verify(printer).printInfo("Is it a mammal?");
        verify(printer).printInfo("Is the statement correct for a shark?");
        verify(printer).printInfo("Is the statement correct for an ant?");
        verify(printer, times(2)).printInfo("Would you like to play again?");
        verify(printer, times(2)).printInfo("I give up. What animal do you have in mind?");
        var expectedYaml = readTreeFile("src/test/resources/test.xml");
        var producedYaml = readTreeFile("animals.xml");
        assertEquals(expectedYaml, producedYaml);
    }

    private String readTreeFile(String path) throws IOException {
        return Files.readString(Path.of(path));
    }
}
