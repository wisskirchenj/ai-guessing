package de.cofinpro.guessing.controller;

import de.cofinpro.guessing.decisiontree.Node;
import de.cofinpro.guessing.io.ConsolePrinter;
import de.cofinpro.guessing.io.ResourceProvider;
import de.cofinpro.guessing.nlp.Animal;
import de.cofinpro.guessing.nlp.DistinguishingFact;
import de.cofinpro.guessing.nlp.YesNoAnswer;

import java.util.Scanner;
import java.util.function.Supplier;

import static de.cofinpro.guessing.nlp.YesNoAnswer.Choice.YES;

/**
 * class that implements the guessing game, the core of the application. It takes a knowledge tree as constructor
 * argument, that minimally consists of one animal node from the initial question or can contain a loaded tree
 * stored from previous game rounds.
 */
public class GuessingGame {

    private Node decisionTree;
    private final ConsolePrinter consolePrinter;
    private final Scanner scanner;

    public GuessingGame(Node decisionTree, ConsolePrinter consolePrinter, Scanner scanner) {
        this.decisionTree = decisionTree;
        this.consolePrinter = consolePrinter;
        this.scanner = scanner;
    }

    /**
     * entry point method for a game instance, that does the game loop over possibly several guessing rounds. Since the
     * knowledge tree may be expanded in AI-manor during the game rounds, it is returned to the calling controller.
     */
    public Node play() {
        consolePrinter.printInfo(ResourceProvider.INSTANCE.get("game.letsPlay"));
        gameLoop();
        return decisionTree;
    }

    private void gameLoop() {
        do {
            consolePrinter.printInfo("%s%n%s".formatted(ResourceProvider.INSTANCE.get("game.think"),
                                     ResourceProvider.INSTANCE.get("game.enter")));
            scanner.nextLine();
            playGame();
        } while (userAnswersYes(ResourceProvider.INSTANCE.getRandom("game.again")));
    }

    /**
     * method implementing a single guessing round. The tree is traversed from root according to user answers.
     * The animal may be guessed or no - in latter case the new animal and distinguishing fact is added to the tree.
     */
    private void playGame() {
        var currentNode = decisionTree;
        while (!currentNode.isLeaf()) {
            final var positiveDecision = userAnswersYes(currentNode.getQuestion());
            currentNode = positiveDecision ? currentNode.getYesNode() : currentNode.getNoNode();
        }
        handleGuessedAnswer(currentNode, userAnswersYes(currentNode.getQuestion()));
    }

    private void handleGuessedAnswer(Node currentNode, boolean guessed) {
        if (guessed) {
            consolePrinter.printInfo("{} {}", ResourceProvider.INSTANCE.getRandom("animal.nice"),
                    ResourceProvider.INSTANCE.getRandom("game.win"));
        } else {
            addAnimal(currentNode);
        }
    }

    private void addAnimal(Node currentNode) {
        var parentForFactNode = currentNode.getParent();
        var placeAsYes = parentForFactNode != null && parentForFactNode.getYesNode() == currentNode;
        var newAnimalNode = promptForAnimal();
        var distinguishingFactNode = buildDistinguishingFactNode(currentNode, newAnimalNode);
        insertIntoDecisionTree(distinguishingFactNode, parentForFactNode, placeAsYes);
    }

    private void insertIntoDecisionTree(Node factNode, Node parent, boolean placeAsYes) {
        if (parent == null) {
            decisionTree = factNode;
            return;
        }
        if (placeAsYes) {
            parent.setYesNode(factNode);
        } else {
            parent.setNoNode(factNode);
        }
    }

    private void sayLearnings(DistinguishingFact distinguishingFact) {
        consolePrinter.printInfo(ResourceProvider.INSTANCE.get("game.learned"));
        consolePrinter.printInfo(distinguishingFact.learnings());
        consolePrinter.printInfo(ResourceProvider.INSTANCE.get("game.distinguish"));
        consolePrinter.printInfo(" - {}", distinguishingFact.question());
        sayLearningJoy();
    }

    private Node buildDistinguishingFactNode(Node firstAnimalNode, Node secondAnimalNode) {
        var firstAnimal = (Animal) firstAnimalNode.getElement();
        var secondAnimal = (Animal) secondAnimalNode.getElement();
        var distinguishingFact = queryDistinguishingFact(firstAnimal, secondAnimal);
        return new Node(distinguishingFact)
                .setYesNode(distinguishingFact.isTrueForSecondAnimal() ? secondAnimalNode : firstAnimalNode)
                .setNoNode(distinguishingFact.isTrueForSecondAnimal() ? firstAnimalNode : secondAnimalNode);
    }

    private DistinguishingFact queryDistinguishingFact(Animal firstAnimal, Animal secondAnimal) {
        var fact = promptUntilValid("statement.error",
                () -> promptForDistinguishingFact(firstAnimal, secondAnimal));
        var trueForSecond = userAnswersYes(
                ResourceProvider.INSTANCE.getFormatted("game.isCorrect", secondAnimal.withIndefiniteArticle()));
        fact.setTrueForSecondAnimal(trueForSecond);
        sayLearnings(fact);
        return fact;
    }

    private DistinguishingFact promptForDistinguishingFact(Animal first, Animal second) {
        consolePrinter.printInfo(ResourceProvider.INSTANCE.getFormatted("statement.prompt",
                first.withIndefiniteArticle(), second.withIndefiniteArticle()));
        return DistinguishingFact.from(scanner.nextLine(), first, second);
    }

    private boolean userAnswersYes(String question) {
        consolePrinter.printInfo(question);
        var answer = promptUntilValid("ask.again", () -> YesNoAnswer.from(scanner.nextLine()));
        return YES.equals(answer.text());
    }

    private Node promptForAnimal() {
        consolePrinter.printInfo(ResourceProvider.INSTANCE.get("game.giveUp"));
        var animal = promptUntilValid("animal.error", () -> Animal.from(scanner.nextLine()));
        return new Node(animal);
    }

    private <T> T promptUntilValid(String promptErrorMessageKey, Supplier<T> factory) {
        T answer = factory.get();
        while (answer == null) {
            consolePrinter.printInfo(ResourceProvider.INSTANCE.getRandom(promptErrorMessageKey));
            answer = factory.get();
        }
        return answer;
    }

    private void sayLearningJoy() {
        consolePrinter.printInfo("{} {}", ResourceProvider.INSTANCE.getRandom("animal.nice"),
                ResourceProvider.INSTANCE.get("animal.learnedMuch"));
    }
}
