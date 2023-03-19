package de.cofinpro.guessing.controller;

import de.cofinpro.guessing.decisiontree.Node;
import de.cofinpro.guessing.io.DataStorage;
import de.cofinpro.guessing.nlp.ClarificationQuestion;
import de.cofinpro.guessing.nlp.JoyExpression;
import de.cofinpro.guessing.nlp.Noun;
import de.cofinpro.guessing.nlp.Bye;
import de.cofinpro.guessing.nlp.DistinguishingFact;
import de.cofinpro.guessing.nlp.Greeting;
import de.cofinpro.guessing.io.ConsolePrinter;
import de.cofinpro.guessing.nlp.YesNoAnswer;
import picocli.CommandLine;

import java.io.IOException;
import java.util.Scanner;

/**
 * controller class, which performs the interactive guessing game.
 */
public class GuessingGame {

    private static final String DISTINGUISH_EXAMPLE_TEXT = """
            The examples of a statement:
             - It can fly
             - It has a horn
             - It is a mammal
            """;

    private final ConsolePrinter consolePrinter;
    private final Scanner scanner;
    private final DataStorage dataStorage = new DataStorage();
    private Node decisionTree;

    public GuessingGame(ConsolePrinter consolePrinter, Scanner scanner) {
        this.consolePrinter = consolePrinter;
        this.scanner = scanner;
    }

    /**
     * entry point method, that starts the learning round.
     */
    public void start(String[] args) throws IOException {
        sayGreeting();
        decisionTree = loadOrInitDecisionTree(args);
        sayLearningJoy();
        consolePrinter.printInfo("Let's play a game!");
        gameLoop();
        dataStorage.saveGameTree(decisionTree);
        sayBye();
    }

    private Node loadOrInitDecisionTree(String[] args) throws IOException {
        new CommandLine(dataStorage).setOptionsCaseInsensitive(true)
                .setCaseInsensitiveEnumValuesAllowed(true)
                .parseArgs(args);
        return dataStorage.loadTree().orElseGet(() -> promptForAnimal("Which animal do you like most?"));
    }

    private void gameLoop() {
        do {
            consolePrinter.printInfo("You think of an animal, and I guess it.\n" +
                                     "Press enter when you're ready.");
            scanner.nextLine();
            play();
        } while (userAnswersYes("Would you like to play again?"));
    }

    private void play() {
        var currentNode = decisionTree;
        while (!currentNode.isLeaf()) {
            final var positiveDecision = userAnswersYes(currentNode.getQuestion());
            currentNode = positiveDecision ? currentNode.getYesNode() : currentNode.getNoNode();
        }
        handleGuessedAnswer(currentNode, userAnswersYes(currentNode.getQuestion()));
    }

    private void handleGuessedAnswer(Node currentNode, boolean guessed) {
        if (guessed) {
            consolePrinter.printInfo("{} I guessed it :-)", new JoyExpression().text());
        } else {
            addAnimal(currentNode);
        }
    }

    private void addAnimal(Node currentNode) {
        var parentForFactNode = currentNode.getParent();
        var yesPosition = parentForFactNode != null && parentForFactNode.getYesNode() == currentNode;
        var newAnimalNode = promptForAnimal("I give up. What animal do you have in mind?");
        var distinguishingFactNode = buildDistinguishingFactNode(currentNode, newAnimalNode);
        insertIntoDecisionTree(distinguishingFactNode, parentForFactNode, yesPosition);
    }

    private void insertIntoDecisionTree(Node factNode, Node parent, boolean yesPosition) {
        if (parent == null) {
            decisionTree = factNode;
            return;
        }
        if (yesPosition) {
            parent.setYesNode(factNode);
        } else {
            parent.setNoNode(factNode);
        }
    }

    private void sayLearnings(DistinguishingFact distinguishingFact) {
        consolePrinter.printInfo("I learned the following facts about animals:");
        consolePrinter.printInfo(distinguishingFact.learnings());
        consolePrinter.printInfo("I can distinguish these animals by asking the question:");
        consolePrinter.printInfo(" - {}", distinguishingFact.question());
        sayLearningJoy();
    }

    private Node buildDistinguishingFactNode(Node firstAnimalNode, Node secondAnimalNode) {
        var firstAnimal = (Noun) firstAnimalNode.getElement();
        var secondAnimal = (Noun) secondAnimalNode.getElement();
        var distinguishingFact = queryDistinguishingFact(firstAnimal, secondAnimal);
        return new Node(distinguishingFact)
                .setYesNode(distinguishingFact.isTrueForSecondAnimal() ? secondAnimalNode : firstAnimalNode)
                .setNoNode(distinguishingFact.isTrueForSecondAnimal() ? firstAnimalNode : secondAnimalNode);
    }

    private DistinguishingFact queryDistinguishingFact(Noun firstAnimal, Noun secondAnimal) {
        var fact = promptForDistinguishingFact(firstAnimal, secondAnimal);
        while (fact == null) {
            consolePrinter.printInfo(DISTINGUISH_EXAMPLE_TEXT);
            fact = promptForDistinguishingFact(firstAnimal, secondAnimal);
        }
        var trueForSecond = userAnswersYes("Is the statement correct for %s?"
                .formatted(secondAnimal.withIndefiniteArticle()));
        fact.setTrueForSecondAnimal(trueForSecond);
        sayLearnings(fact);
        return fact;
    }

    private DistinguishingFact promptForDistinguishingFact(Noun first, Noun second) {
        consolePrinter.printInfo("""
                Specify a fact that distinguishes {} from {}.
                The sentence should satisfy one of the following templates:
                - It can ...
                - It has ...
                - It is a/an ...""",
                first.withIndefiniteArticle(), second.withIndefiniteArticle());
        return DistinguishingFact.from(scanner.nextLine(), first, second);
    }

    private boolean userAnswersYes(String question) {
        consolePrinter.printInfo(question);
        var answer = YesNoAnswer.from(scanner.nextLine());
        while (answer == null) {
            consolePrinter.printInfo(new ClarificationQuestion().text());
            answer = YesNoAnswer.from(scanner.nextLine());
        }
        return "Yes".equals(answer.text());
    }

    private Node promptForAnimal(String promptMessage) {
        consolePrinter.printInfo(promptMessage);
        return new Node(Noun.from(scanner.nextLine()));
    }

    private void sayLearningJoy() {
        consolePrinter.printInfo("{} I've learned so much about animals!", new JoyExpression().text());
    }

    private void sayGreeting() {
        consolePrinter.printInfo("{}\n\nI want to learn about animals.", new Greeting().text());
    }

    private void sayBye() {
        consolePrinter.printInfo(new Bye().text());
    }
}
