package de.cofinpro.guessing.controller;

import de.cofinpro.guessing.model.ClarificationQuestion;
import de.cofinpro.guessing.model.Noun;
import de.cofinpro.guessing.model.Bye;
import de.cofinpro.guessing.model.DistinguishingFact;
import de.cofinpro.guessing.model.Greeting;
import de.cofinpro.guessing.io.ConsolePrinter;
import de.cofinpro.guessing.model.Ordinal;
import de.cofinpro.guessing.model.YesNoAnswer;

import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * controller class, which performs the interactive learning round with user questionnaire.
 */
public class LearningRound {

    private static final String DISTINGUISH_EXAMPLE_TEXT = """
            The examples of a statement:
             - It can fly
             - It has a horn
             - It is a mammal
            """;

    private final ConsolePrinter consolePrinter;
    private final Scanner scanner;
    private final Noun[] animals = new Noun[2];

    public LearningRound(ConsolePrinter consolePrinter, Scanner scanner) {
        this.consolePrinter = consolePrinter;
        this.scanner = scanner;
    }

    /**
     * entry point method, that starts the learning round.
     */
    public void start() {
        sayGreeting();
        promptForAnimals();
        var distinguishingFact = queryDistinguishingFact();
        sayLearnings(distinguishingFact);
        sayBye();
    }

    private void sayLearnings(DistinguishingFact distinguishingFact) {
        consolePrinter.printInfo("I learned the following facts about animals:");
        IntStream.range(0, 2).forEach(i -> consolePrinter
                .printInfo(distinguishingFact.templateForAnimal(i), animals[i].withDefiniteArticle()));
        consolePrinter.printInfo("I can distinguish these animals by asking the question:");
        consolePrinter.printInfo(distinguishingFact.question());
    }

    private DistinguishingFact queryDistinguishingFact() {
        var fact = promptForDistinguishingFact();
        while (fact == null) {
            consolePrinter.printInfo(DISTINGUISH_EXAMPLE_TEXT);
            fact = promptForDistinguishingFact();
        }
        fact.setTrueForSecondAnimal(promptTrueForSecondAnimal());
        return fact;
    }

    private DistinguishingFact promptForDistinguishingFact() {
        consolePrinter.printInfo("Specify a fact that distinguishes {} from {}.\n"
                + "The sentence should be of the format: 'It can/has/is ...'.",
                animals[0].withIndefiniteArticle(), animals[1].withIndefiniteArticle());
        return DistinguishingFact.from(scanner.nextLine());
    }

    private boolean promptTrueForSecondAnimal() {
        consolePrinter.printInfo("Is it correct for {}?", animals[1].withIndefiniteArticle());
        var answer = YesNoAnswer.from(scanner.nextLine());
        while (answer == null) {
            consolePrinter.printInfo(new ClarificationQuestion().text());
            answer = YesNoAnswer.from(scanner.nextLine());
        }
        return "Yes".equals(answer.text());
    }

    private void promptForAnimals() {
        for (int i = 0; i < 2; i++) {
            animals[i] = Noun.from(promptForAnimal(i));
        }
    }

    private String promptForAnimal(int i) {
        consolePrinter.printInfo("Enter the {} animal: ", Ordinal.of(i + 1).text());
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
