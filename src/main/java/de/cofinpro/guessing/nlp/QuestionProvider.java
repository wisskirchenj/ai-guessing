package de.cofinpro.guessing.nlp;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Implementing classes are NLP elements, that can provide a question.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        defaultImpl = Noun.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Noun.class),
        @JsonSubTypes.Type(value = DistinguishingFact.class, name = "fact")
})
public interface QuestionProvider {

    String question();

    String asStatement();
}
