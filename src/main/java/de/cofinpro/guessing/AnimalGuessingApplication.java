package de.cofinpro.guessing;

import de.cofinpro.guessing.controller.GuessingAnimalController;
import de.cofinpro.guessing.io.ConsolePrinter;
import lombok.SneakyThrows;

import java.util.Locale;
import java.util.Scanner;

public class AnimalGuessingApplication {

    @SneakyThrows
    public static void main(String[] args) {
        Locale.setDefault(Locale.of(System.getProperty("user.language")));
        new GuessingAnimalController(new ConsolePrinter(), new Scanner(System.in)).start(args);
    }
}
