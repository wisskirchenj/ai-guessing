package de.cofinpro.guessing.decisiontree;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Node structure of a binary decision tree with parent connection and elements implementing QuestionProvider.
 */
@Getter
@RequiredArgsConstructor
public class Node {

    private final QuestionProvider element;
    @Setter
    private Node parent;
    private Node yesNode;
    private Node noNode;

    public boolean isLeaf() {
        return yesNode == null && noNode == null;
    }

    public String getQuestion() {
        return element.question();
    }

    /**
     * inserts given node as yesNode and sets its parent connection to this.
     * @param node node to insert
     * @return this node
     */
    public Node setYesNode(Node node) {
        yesNode = node;
        node.setParent(this);
        return this;
    }

    /**
     * inserts given node as noNode and sets its parent connection to this.
     * @param node node to insert
     * @return this node
     */
    public Node setNoNode(Node node) {
        noNode = node;
        node.setParent(this);
        return this;
    }
}
