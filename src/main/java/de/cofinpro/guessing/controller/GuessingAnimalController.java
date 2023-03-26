package de.cofinpro.guessing.controller;

import de.cofinpro.guessing.decisiontree.Node;
import de.cofinpro.guessing.io.DataStorage;
import de.cofinpro.guessing.io.ResourceProvider;
import de.cofinpro.guessing.nlp.Greeting;
import de.cofinpro.guessing.io.ConsolePrinter;
import de.cofinpro.guessing.nlp.Animal;
import picocli.CommandLine;

import java.io.IOException;
import java.util.Scanner;

/**
 * controller class, which performs the interactive guessing game and the knowledge tree explore menu options.
 */
public class GuessingAnimalController {

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
        String choice;
        do {
            consolePrinter.printInfo(getMenuText());
            choice = scanner.nextLine().trim();
            switch (choice) {
                case "0" -> sayBye();
                case "1" -> playGuessingGame();
                case "2", "3", "4", "5" -> exploreKnowledgeTree(choice);
                default -> consolePrinter
                        .printInfo(ResourceProvider.INSTANCE.getFormatted("menu.property.error", 5));
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
        var searchAnimal = promptForAnimal(ResourceProvider.INSTANCE.get("animal.prompt"));
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
        return dataStorage.loadTree().orElseGet(() -> new Node(promptForAnimal("%s%n%s".formatted(
                ResourceProvider.INSTANCE.get("animal.wantLearn"),
                ResourceProvider.INSTANCE.get("animal.askFavorite")
        ))));
    }

    private Animal promptForAnimal(String promptMessage) {
        consolePrinter.printInfo(promptMessage);
        var animal = Animal.from(scanner.nextLine());
        while (animal == null) {
            consolePrinter.printInfo(ResourceProvider.INSTANCE.get("animal.error"));
            animal = Animal.from(scanner.nextLine());
        }
        return animal;
    }

    private void sayGreeting() {
        consolePrinter.printInfo("{}\n", new Greeting().text());
    }

    private void sayWelcome() {
        consolePrinter.printInfo(ResourceProvider.INSTANCE.get("welcome"));
    }

    private void sayBye() {
        consolePrinter.printInfo(ResourceProvider.INSTANCE.getRandom("game.thanks"));
        consolePrinter.printInfo(ResourceProvider.INSTANCE.getRandom("farewell"));
    }

    private String getMenuText() {
        return """
            
            %s
                        
            1. %s
            2. %s
            3. %s
            4. %s
            5. %s
            0. %s""".formatted(
                    ResourceProvider.INSTANCE.get("menu.property.title"),
                    ResourceProvider.INSTANCE.get("menu.entry.play"),
                    ResourceProvider.INSTANCE.get("menu.entry.list"),
                    ResourceProvider.INSTANCE.get("menu.entry.search"),
                    ResourceProvider.INSTANCE.get("menu.entry.statistics"),
                    ResourceProvider.INSTANCE.get("menu.entry.print"),
                    ResourceProvider.INSTANCE.get("menu.property.exit")
        );
    }
}
