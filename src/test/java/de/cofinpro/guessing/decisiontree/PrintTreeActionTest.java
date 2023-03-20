package de.cofinpro.guessing.decisiontree;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PrintTreeActionTest {
    private static final String TEST_JSON_PATH = "src/test/resources/test.json";
    private static final String EXPECTED_TREE = """
             
             └ Can it climb trees?
              ├ Is it a domestic animal?
              │├ Does it have long arms?
              ││├ an ape
              ││└ a cat
              │└ Is it an insect?
              │ ├ a bug
              │ └ Is it a bird?
              │  ├ a blackbird
              │  └ a wolf
              └ Can it lay eggs?
               ├ a chicken
               └ a badger
            """;

    @Test
    void whenTestJson_TreePrintedCorrectly() throws IOException {
        var objectMapper = new JsonMapper();
        var tree = objectMapper.readValue(new File(TEST_JSON_PATH), Node.class);
        var action = new PrintTreeAction(tree);
        assertEquals(EXPECTED_TREE, action.getResult());
    }
}