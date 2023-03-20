package de.cofinpro.guessing.controller;

import de.cofinpro.guessing.decisiontree.ListAnimalsAction;
import de.cofinpro.guessing.decisiontree.Node;
import de.cofinpro.guessing.decisiontree.PrintTreeAction;
import de.cofinpro.guessing.decisiontree.SearchAnimalAction;
import de.cofinpro.guessing.decisiontree.TreeStatisticsAction;
import de.cofinpro.guessing.io.ConsolePrinter;
import de.cofinpro.guessing.nlp.Noun;

import java.util.stream.Collectors;

/**
 * controller class that organizes all menu options of the application - except the guessing game. Those are
 * comprised as "tree exploring actions", which are instantiated and performed form this controller class, that also
 * cares about the console printing of the results.
 */
public class KnowledgeTreeExplorer {
    private final Node knowledgeTree;
    private final ConsolePrinter consolePrinter;

    public KnowledgeTreeExplorer(Node knowledgeTree, ConsolePrinter consolePrinter) {
        this.knowledgeTree = knowledgeTree;
        this.consolePrinter = consolePrinter;
    }

    public void listAnimals() {
        var animals = new ListAnimalsAction(knowledgeTree).getAnimals();
        var animalsText = " - " + animals.stream().sorted().collect(Collectors.joining("\n - "));
        consolePrinter.printInfo("Here are the animals I know:\n{}", animalsText);
    }

    public void searchAnimalAndPrintInfo(Noun searchAnimal) {
        var facts = new SearchAnimalAction(knowledgeTree).getFacts(searchAnimal);
        if (facts.isEmpty()) {
            consolePrinter.printInfo("No facts about the {}.", searchAnimal.text());
        } else {
            consolePrinter.printInfo("Facts about the {}:\n - {}", searchAnimal.text(),
                    String.join("\n - ", facts));
        }
    }

    public void printStatistics() {
        consolePrinter.printInfo("The Knowledge Tree stats\n");
        var stats = new TreeStatisticsAction(knowledgeTree).getStats();
        consolePrinter.printInfo(stats.getFormatted());
    }

    public void printTree() {
        consolePrinter.printInfo(new PrintTreeAction(knowledgeTree).getResult());
    }
}
