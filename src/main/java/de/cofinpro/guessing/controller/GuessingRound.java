package de.cofinpro.guessing.controller;

import de.cofinpro.guessing.model.AnimalProcessor;
import de.cofinpro.guessing.model.Bye;
import de.cofinpro.guessing.model.ClarificationQuestion;
import de.cofinpro.guessing.model.Greeting;
import de.cofinpro.guessing.io.ConsolePrinter;
import de.cofinpro.guessing.model.YesNoProcessor;

import java.util.Scanner;

/**
 * controller class, which performs the interactive guessing round on the user's input.
 */
public class GuessingRound {

    private final ConsolePrinter consolePrinter;
    private final Scanner scanner;

    public GuessingRound(ConsolePrinter consolePrinter, Scanner scanner) {
        this.consolePrinter = consolePrinter;
        this.scanner = scanner;
    }

    /**
     * entry point method, that starts the guessing round.
     */
    public void start() {
        sayGreeting();
        var animalInput = promptForAnimal();
        askGuessingQuestion(animalInput);
        answerLoop();
        sayBye();
    }

    private void answerLoop() {
        var answerProcessor = new YesNoProcessor();
        var parsedAnswer = answerProcessor.from(scanner.nextLine());
        while (!YesNoProcessor.YES_NO_SET.contains(parsedAnswer)) {
            consolePrinter.printInfo(new ClarificationQuestion().text());
            parsedAnswer = answerProcessor.from(scanner.nextLine());
        }
        consolePrinter.printInfo("You answered: {}\n", parsedAnswer);
    }

    private void askGuessingQuestion(String animalInput) {
        var animal = new AnimalProcessor();
        consolePrinter.printInfo("Is it {}?", animal.from(animalInput));
    }

    private String promptForAnimal() {
        consolePrinter.printInfo("Enter an animal: ");
        return scanner.nextLine();
    }

    private void sayGreeting() {
        var greeting = new Greeting();
        consolePrinter.printInfo("{}\n", greeting.text());
    }

    private void sayBye() {
        var bye = new Bye();
        consolePrinter.printInfo(bye.text());
    }
}
