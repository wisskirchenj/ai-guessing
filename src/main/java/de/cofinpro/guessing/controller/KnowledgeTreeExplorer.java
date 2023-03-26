package de.cofinpro.guessing.controller;

import de.cofinpro.guessing.decisiontree.ListAnimalsAction;
import de.cofinpro.guessing.decisiontree.Node;
import de.cofinpro.guessing.decisiontree.PrintTreeAction;
import de.cofinpro.guessing.decisiontree.SearchAnimalAction;
import de.cofinpro.guessing.decisiontree.TreeStatisticsAction;
import de.cofinpro.guessing.io.ConsolePrinter;
import de.cofinpro.guessing.io.ResourceProvider;
import de.cofinpro.guessing.nlp.Animal;

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

    /**
     * gets animals from ListAnimalsAction (using Depth-FirstSearch) and prints them out.
     */
    public void listAnimals() {
        var animals = new ListAnimalsAction(knowledgeTree).getAnimals();
        var animalsText = " - " + animals.stream().sorted().collect(Collectors.joining("\n - "));
        consolePrinter.printInfo("{}\n{}", ResourceProvider.INSTANCE.get("tree.list.animals"), animalsText);
    }

    /**
     * searches animal facts using SearchAnimalAction (using Depth-FirstSearch) and prints the result.
     */
    public void searchAnimalAndPrintInfo(Animal searchAnimal) {
        var facts = new SearchAnimalAction(knowledgeTree).getFacts(searchAnimal);
        if (facts.isEmpty()) {
            consolePrinter.printInfo(ResourceProvider.INSTANCE
                    .getFormatted("tree.search.noFacts", searchAnimal.withDefiniteArticle()));
        } else {
            consolePrinter.printInfo("{}\n - {}",
                    ResourceProvider.INSTANCE.getFormatted("tree.search.facts", searchAnimal.withDefiniteArticle()),
                    String.join("\n - ", facts));
        }
    }

    public void printStatistics() {
        consolePrinter.printInfo("{}\n", ResourceProvider.INSTANCE.get("tree.stats.title"));
        var stats = new TreeStatisticsAction(knowledgeTree).getStats();
        consolePrinter.printInfo(stats.getFormatted());
    }

    public void printTree() {
        consolePrinter.printInfo(new PrintTreeAction(knowledgeTree).getResult());
    }
}
