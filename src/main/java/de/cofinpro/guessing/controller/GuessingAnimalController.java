package de.cofinpro.guessing.controller;

import de.cofinpro.guessing.decisiontree.Node;
import de.cofinpro.guessing.io.DataStorage;
import de.cofinpro.guessing.nlp.Bye;
import de.cofinpro.guessing.nlp.Greeting;
import de.cofinpro.guessing.io.ConsolePrinter;
import de.cofinpro.guessing.nlp.Noun;
import picocli.CommandLine;

import java.io.IOException;
import java.util.Scanner;

/**
 * controller class, which performs the interactive guessing game.
 */
public class GuessingAnimalController {

    private final ConsolePrinter consolePrinter;
    private final Scanner scanner;
    private final DataStorage dataStorage = new DataStorage();

    public GuessingAnimalController(ConsolePrinter consolePrinter, Scanner scanner) {
        this.consolePrinter = consolePrinter;
        this.scanner = scanner;
    }

    /**
     * entry point method, that starts the learning round.
     */
    public void start(String[] args) throws IOException {
        sayGreeting();
        var decisionTree = loadOrInitDecisionTree(args);
        sayWelcome();
        decisionTree = new GuessingGame(decisionTree, consolePrinter, scanner).play();
        dataStorage.saveGameTree(decisionTree);
        sayBye();
    }

    private Node loadOrInitDecisionTree(String[] args) throws IOException {
        new CommandLine(dataStorage).setOptionsCaseInsensitive(true)
                .setCaseInsensitiveEnumValuesAllowed(true)
                .parseArgs(args);
        return dataStorage.loadTree().orElseGet(this::promptForAnimal);
    }

    private Node promptForAnimal() {
        consolePrinter.printInfo("I want to learn about animals.\nWhich animal do you like most?");
        return new Node(Noun.from(scanner.nextLine()));
    }

    private void sayGreeting() {
        consolePrinter.printInfo("{}!\n", new Greeting().text());
    }

    private void sayWelcome() {
        consolePrinter.printInfo("\nWelcome to the animal expert system!\n");
    }

    private void sayBye() {
        consolePrinter.printInfo(new Bye().text());
    }
}
