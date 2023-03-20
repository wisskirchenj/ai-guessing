package de.cofinpro.guessing.decisiontree;

import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * abstract traversal class using Depth First Search algorithm, that traverses the YES-branch before the NO-Branch.
 * Derived Action classes override the abstract processNode() method, that provides a TraversalNodeInfo-instance,
 * with the element of the node and a position stack - the node's yes-no-path.
 */
public abstract class KnowledgeTreeDfs {

    @Getter
    private final Node tree;
    private final Deque<TraversalNodeInfo.Branch> nodePositionStack = new ArrayDeque<>();

    protected KnowledgeTreeDfs(Node tree) {
        this.tree = tree;
    }

    protected void traverseTree() {
        recursiveTraverse(tree);
    }

    private void recursiveTraverse(Node node) {
        processNode(new TraversalNodeInfo(node.getElement(), nodePositionStack));
        if (node.isLeaf()) {
            return;
        }
        nodePositionStack.push(TraversalNodeInfo.Branch.YES);
        recursiveTraverse(node.getYesNode());
        nodePositionStack.pop();
        nodePositionStack.push(TraversalNodeInfo.Branch.NO);
        recursiveTraverse(node.getNoNode());
        nodePositionStack.pop();
    }

    protected abstract void processNode(TraversalNodeInfo current);
}
