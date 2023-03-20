package de.cofinpro.guessing.decisiontree;

import de.cofinpro.guessing.nlp.QuestionProvider;

import java.util.Deque;

record TraversalNodeInfo(QuestionProvider element, Deque<Branch> position) {

    enum Branch {
        YES,
        NO
    }
}
