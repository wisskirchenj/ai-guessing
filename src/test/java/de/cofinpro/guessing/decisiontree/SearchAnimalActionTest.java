package de.cofinpro.guessing.decisiontree;

import com.fasterxml.jackson.databind.json.JsonMapper;
import de.cofinpro.guessing.nlp.Noun;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchAnimalActionTest {
    private static final String TEST_JSON_PATH = "src/test/resources/test.json";
    private SearchAnimalAction action;

    @BeforeEach
    void setup() throws IOException {
        var objectMapper = new JsonMapper();
        var tree = objectMapper.readValue(new File(TEST_JSON_PATH), Node.class);
        action = new SearchAnimalAction(tree);
    }

    @Test
    void whenTestJsonWithAnimalNotIn_EmptyFactsListReturned() {
        assertEquals(List.of(), action.getFacts(Noun.from("a hare")));
    }

    @Test
    void whenTestJsonWithAnimalIn_CorrectFactsListReturned() {
        assertEquals(List.of("It can climb trees.", "It is a domestic animal.", "It doesn't have long arms."),
                action.getFacts(Noun.from("the cat")));
    }
}