package de.cofinpro.guessing;

import de.cofinpro.guessing.controller.GuessingRound;
import de.cofinpro.guessing.io.ConsolePrinter;

import java.util.Scanner;

public class AnimalGuessingApplication {

    public static void main(String[] args) {
        new GuessingRound(new ConsolePrinter(), new Scanner(System.in)).start();
    }
}
