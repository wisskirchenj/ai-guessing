package de.cofinpro.guessing.decisiontree;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ListAnimalsActionTest {

    private static final String TEST_JSON_PATH = "src/test/resources/test.json";
    private static final Set<String> ANIMALS_EXPECTED = Set.of(
            "ape", "blackbird", "badger", "wolf", "cat", "bug", "chicken"
    );

    @Test
    void whenTestJson_AnimalSetReturnedCorrectly() throws IOException {
        var objectMapper = new JsonMapper();
        var tree = objectMapper.readValue(new File(TEST_JSON_PATH), Node.class);
        var action = new ListAnimalsAction(tree);
        assertEquals(ANIMALS_EXPECTED, new HashSet<>(action.getAnimals()));
    }
}