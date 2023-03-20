package de.cofinpro.guessing.decisiontree;

import de.cofinpro.guessing.nlp.DistinguishingFact;
import de.cofinpro.guessing.nlp.Noun;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Depth First Search derived action class, that searches the knowledge tree for a given animal and returns its facts.
 */
public class SearchAnimalAction extends KnowledgeTreeDfs {

    private final List<String> facts = new ArrayList<>();
    private Noun toSearch;

    public SearchAnimalAction(Node knowledgeTree) {
        super(knowledgeTree);
    }

    /**
     * DFS-hook, that fills the facts field as soon as the node for the animal toSearch arrives.
     */
    @Override
    protected void processNode(TraversalNodeInfo current) {
        if (current.element().equals(toSearch)) {
            fillAnimalFacts(current.position());
        }
    }

    /**
     * by use of the tree position deque, the knowledge tree is trversed to the leaf and each statement is added to
     * the facts list field - positive or negated as is true for this animal.
     * @param treePosition deque with yes-no-path to the animal leaf.
     */
    private void fillAnimalFacts(Deque<TraversalNodeInfo.Branch> treePosition) {
        var currentNode = super.getTree();
        var positionIterator = treePosition.descendingIterator();
        while (!currentNode.isLeaf()) {
            var trueForCurrentFactAndAnimal = positionIterator.next() == TraversalNodeInfo.Branch.YES;
            var factNode = (DistinguishingFact) currentNode.getElement();
            facts.add(trueForCurrentFactAndAnimal  ? factNode.asStatement() : factNode.asNegativeStatement());
            currentNode = trueForCurrentFactAndAnimal ? currentNode.getYesNode() : currentNode.getNoNode();
        }
    }

    /**
     * searches knowledge tree for given animal and collects all its facts if found.
     * @param searchAnimal the animal to search for.
     * @return empty list if animal not found - else its facts as String list.
     */
    public List<String> getFacts(Noun searchAnimal) {
        toSearch = searchAnimal;
        super.traverseTree();
        return facts;
    }
}
