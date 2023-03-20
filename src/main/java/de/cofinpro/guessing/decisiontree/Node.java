package de.cofinpro.guessing.decisiontree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.cofinpro.guessing.nlp.QuestionProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Node structure of a binary decision tree with parent connection and elements implementing QuestionProvider.
 */
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Node {

    private QuestionProvider element;
    @Setter
    @JsonIgnore
    private Node parent;
    private Node yesNode;
    private Node noNode;

    public Node(QuestionProvider element) {
        this.element = element;
    }

    @JsonIgnore
    public boolean isLeaf() {
        return yesNode == null && noNode == null;
    }

    @JsonIgnore
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
