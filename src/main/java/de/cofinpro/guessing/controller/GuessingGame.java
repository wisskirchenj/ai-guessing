package de.cofinpro.guessing.controller;

import de.cofinpro.guessing.decisiontree.Node;
import de.cofinpro.guessing.io.ConsolePrinter;
import de.cofinpro.guessing.nlp.ClarificationQuestion;
import de.cofinpro.guessing.nlp.DistinguishingFact;
import de.cofinpro.guessing.nlp.JoyExpression;
import de.cofinpro.guessing.nlp.Noun;
import de.cofinpro.guessing.nlp.YesNoAnswer;

import java.util.Scanner;

import static de.cofinpro.guessing.nlp.YesNoAnswer.Choice.YES;

/**
 * class that implements the guessing game, the core of the application. It takes a knowledge tree as constructor
 * argument, that minimally consists of one animal node from the initial question or can contain a loaded tree
 * stored from previous game rounds.
 */
public class GuessingGame {

    private static final String DISTINGUISH_EXAMPLE_TEXT = """
            The examples of a statement:
             - It can fly
             - It has a horn
             - It is a mammal
            """;

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
        consolePrinter.printInfo("Let's play a game!");
        gameLoop();
        return decisionTree;
    }

    private void gameLoop() {
        do {
            consolePrinter.printInfo("You think of an animal, and I guess it.\n" +
                                     "Press enter when you're ready.");
            scanner.nextLine();
            playGame();
        } while (userAnswersYes("Would you like to play again?"));
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
            consolePrinter.printInfo("{} I guessed it :-)", new JoyExpression().text());
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
        return YES.equals(answer.text());
    }

    private Node promptForAnimal() {
        consolePrinter.printInfo("I give up. What animal do you have in mind?");
        return new Node(Noun.from(scanner.nextLine()));
    }

    private void sayLearningJoy() {
        consolePrinter.printInfo("{} I've learned so much about animals!", new JoyExpression().text());
    }
}
