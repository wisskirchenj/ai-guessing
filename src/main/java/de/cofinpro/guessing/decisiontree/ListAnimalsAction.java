package de.cofinpro.guessing.decisiontree;

import de.cofinpro.guessing.nlp.Noun;

import java.util.ArrayList;
import java.util.List;

/**
 * Depth First Search derived action class, that collects all animal names in the knowledge tree.
 */
public class ListAnimalsAction extends KnowledgeTreeDfs {

    private final List<String> animals = new ArrayList<>();

    public ListAnimalsAction(Node knowledgeTree) {
        super(knowledgeTree);
    }

    /**
     * entry point to this action, that process the knowledge tree and collects all animal names.
     */
    public List<String> getAnimals() {
        super.traverseTree();
        return animals;
    }

    /**
     * DFS hook, that adds an animal to the list, if the current node contains one.
     */
    @Override
    protected void processNode(TraversalNodeInfo current) {
        if (current.element() instanceof Noun animal) {
            animals.add(animal.text());
        }
    }
}
