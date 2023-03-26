package de.cofinpro.guessing.decisiontree;

import de.cofinpro.guessing.io.ResourceProvider;
import de.cofinpro.guessing.nlp.Animal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Depth First Search derived action class, that creates an ASCII-text representation of the knowledge tree.
 * This action crucially depends on the DFS-Algorithm used in its super class to traverse Yes-Nodes first.
 */
public class PrintTreeAction extends KnowledgeTreeDfs {

    private final StringBuilder builder = new StringBuilder("\n ");

    public PrintTreeAction(Node knowledgeTree) {
        super(knowledgeTree);
    }

    /**
     * overriden DFS-hook method, that adds a representation line for every node that is processed using the builder field.
     * @param current the current node's information, i.e. its element and its position stack containing the
     *                Yes-No-path to this node.
     */
    @Override
    protected void processNode(TraversalNodeInfo current) {
        if (current.position().isEmpty()) { // root node
            addRootLine(current.element().question());
        } else {
            addNodeLine(current);
        }
    }

    private void addNodeLine(TraversalNodeInfo current) {
        builder.append("  ");
        var iter = current.position().descendingIterator();
        while (iter.hasNext()) {
            var branch = iter.next();
            if (iter.hasNext()) {
                addInnerSymbol(branch);
            } else {
                addLastSymbol(branch);
            }
        }
        builder.append(" ");
        builder.append(current.element() instanceof Animal animal
                ? animal.withIndefiniteArticle()
                : current.element().question());
        builder.append("\n");
    }

    private void addLastSymbol(TraversalNodeInfo.Branch branch) {
        builder.append(branch == TraversalNodeInfo.Branch.YES ? Symbol.YES_LAST.getValue() : Symbol.NO_LAST.getValue());
    }

    private void addInnerSymbol(TraversalNodeInfo.Branch branch) {
        builder.append(branch == TraversalNodeInfo.Branch.YES ? Symbol.YES.getValue() : Symbol.NO.getValue());
    }

    private void addRootLine(String question) {
        builder.append(Symbol.NO_LAST.getValue()).append(" ").append(question).append("\n");
    }

    /**
     * public entry point to this Action, that traverses the tree and returns the Ascii tree representation.
     */
    public String getResult() {
        super.traverseTree();
        return builder.toString();
    }


    /**
     * Symbol enum class representing the tree lines and end pieces of the printed tree.
     */
    @Getter
    @RequiredArgsConstructor
    private enum Symbol {
        YES(ResourceProvider.INSTANCE.get("tree.print.vertical").charAt(0)),
        NO(' '),
        YES_LAST(ResourceProvider.INSTANCE.get("tree.print.branch").charAt(0)),
        NO_LAST(ResourceProvider.INSTANCE.get("tree.print.corner").charAt(0));

        private final char value;
    }
}
