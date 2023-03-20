package de.cofinpro.guessing.decisiontree;

import de.cofinpro.guessing.nlp.Noun;

import java.util.Locale;

/**
 * Depth First Search derived action class, that creates a statistics record of the knowledge tree.
 */
public class TreeStatisticsAction  extends KnowledgeTreeDfs {

    private String rootNode;
    private int numberOfNodes;
    private int numberOfAnimals;
    private int numberOfFacts;
    private int treeHeight;
    private int minAnimalDepth = Integer.MAX_VALUE;
    private int sumAnimalDepth;

    public TreeStatisticsAction(Node knowledgeTree) {
        super(knowledgeTree);
    }

    @Override
    protected void processNode(TraversalNodeInfo current) {
        numberOfNodes++;
        if (current.position().isEmpty()) { // the root node
            rootNode = current.element().asStatement();
        }
        if (current.element() instanceof Noun) {
            numberOfAnimals++;
            evaluateHeightAndDepth(current);
        } else {
            numberOfFacts++;
        }
    }

    private void evaluateHeightAndDepth(TraversalNodeInfo current) {
        var depth = current.position().size();
        treeHeight = Math.max(treeHeight, depth); // enough to look for treeHeight in Animal nodes cause all leaves are
        sumAnimalDepth += depth;
        minAnimalDepth = Math.min(minAnimalDepth, depth);
    }

    /**
     * entry point method to this action. The statistics are collected in instance fields in the processNode-hook of DFS.
     * The average animal depth is calculated and - with other instance fields - returned within a Statistics record.
     */
    public Statistics getStats() {
        super.traverseTree();
        var averageTwoDigitPrecisionTimes10 = sumAnimalDepth * 10 / numberOfAnimals;
        return new Statistics(rootNode, numberOfNodes, numberOfAnimals, numberOfFacts, treeHeight,
                minAnimalDepth, averageTwoDigitPrecisionTimes10 / 10.0f);
    }

    /**
     * immutable Statistics record, that is returned from this Action. A convenient getFormatted() method returns
     * the printout text in the requested format.
     */
    public record Statistics(String rootNode,
                             int numberOfNodes,
                             int numberOfAnimals,
                             int numberOfFacts,
                             int treeHeight,
                             int minAnimalDepth,
                             float averageAnimalDepth
    ) {
        public String getFormatted() {
            return getStatLine(rootNode())
                   + getStatLine("total number of nodes", numberOfNodes())
                   + getStatLine("total number of animals", numberOfAnimals())
                   + getStatLine("total number of statements", numberOfFacts())
                   + getStatLine("height of the tree", treeHeight())
                   + getStatLine("minimum animal's depth", minAnimalDepth())
                   + getStatLine(averageAnimalDepth());
        }

        private String getStatLine(String value) {
            return "- %-29s%s%n".formatted("root node", value);
        }

        private String getStatLine(String label, int value) {
            return "- %-29s%d%n".formatted(label, value);
        }

        private String getStatLine(float value) {
            return String.format(Locale.US, "- %-29s%.1f","average animal's depth", value);
        }
    }
}
