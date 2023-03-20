package de.cofinpro.guessing;

import de.cofinpro.guessing.controller.GuessingAnimalController;
import de.cofinpro.guessing.io.ConsolePrinter;
import lombok.SneakyThrows;

import java.util.Scanner;

public class AnimalGuessingApplication {

    @SneakyThrows
    public static void main(String[] args) {
        new GuessingAnimalController(new ConsolePrinter(), new Scanner(System.in)).start(args);
    }
}
