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
 * controller class, which performs the interactive guessing game and the knowledge tree explore menu options.
 */
public class GuessingAnimalController {
    
    private static final String MENU_TEXT = """
            
            What do you want to do:
                        
            1. Play the guessing game
            2. List of all animals
            3. Search for an animal
            4. Calculate statistics
            5. Print the Knowledge Tree
            0. Exit""";

    private final ConsolePrinter consolePrinter;
    private final Scanner scanner;
    private final DataStorage dataStorage = new DataStorage();
    private Node knowledgeTree;

    public GuessingAnimalController(ConsolePrinter consolePrinter, Scanner scanner) {
        this.consolePrinter = consolePrinter;
        this.scanner = scanner;
    }

    /**
     * entry point method, that starts the learning round.
     */
    public void start(String[] args) throws IOException {
        sayGreeting();
        knowledgeTree = loadOrInitKnowledgeTree(args);
        sayWelcome();
        doMenuLoop();
    }

    private void doMenuLoop() throws IOException {
        var choice = "";
        do {
            consolePrinter.printInfo(MENU_TEXT);
            choice = scanner.nextLine().trim();
            switch (choice) {
                case "0" -> sayBye();
                case "1" -> playGuessingGame();
                case "2", "3", "4", "5" -> exploreKnowledgeTree(choice);
                default -> consolePrinter.printInfo("Invalid choice !");
            }
        } while (!"0".equals(choice));
    }

    private void exploreKnowledgeTree(String choice) {
        var explorer = new KnowledgeTreeExplorer(knowledgeTree, consolePrinter);
        switch (choice) {
            case "2" -> explorer.listAnimals();
            case "3" -> searchAnimalAndPrintInfo(explorer);
            case "4" -> explorer.printStatistics();
            case "5" -> explorer.printTree();
            default -> consolePrinter
                    .printError("Internal Error occurred! Received '{}' as menu choice.", choice);
        }
    }

    private void searchAnimalAndPrintInfo(KnowledgeTreeExplorer explorer) {
        consolePrinter.printInfo("Enter the animal:");
        var searchAnimal = Noun.from(scanner.nextLine());
        explorer.searchAnimalAndPrintInfo(searchAnimal);
    }

    private void playGuessingGame() throws IOException {
        knowledgeTree = new GuessingGame(knowledgeTree, consolePrinter, scanner).play();
        dataStorage.saveGameTree(knowledgeTree);
    }

    private Node loadOrInitKnowledgeTree(String[] args) throws IOException {
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
        consolePrinter.printInfo("Welcome to the animal expert system!");
    }

    private void sayBye() {
        consolePrinter.printInfo(new Bye().text());
    }
}
