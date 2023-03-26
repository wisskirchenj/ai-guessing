package de.cofinpro.guessing.decisiontree;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TreeStatisticsActionTest {
    private static final String TEST_JSON_PATH = "src/test/resources/test.json";
    private final TreeStatisticsAction.Statistics expectedStats
            = new TreeStatisticsAction.Statistics("It can climb trees", 13, 7,
            6, 4, 2, 3.0f);

    private static final String EXPECTED_STAT_TEXT = """
            - root node                    It can climb trees
            - total number of nodes        13
            - total number of animals      7
            - total number of statements   6
            - height of the tree           4
            - minimum depth                2
            - average depth                3,0""";

    @Test
    void whenTestJson_StatisticsReturnedCorrectly() throws IOException {
        var objectMapper = new JsonMapper();
        var tree = objectMapper.readValue(new File(TEST_JSON_PATH), Node.class);
        var action = new TreeStatisticsAction(tree);
        var stats = action.getStats();
        assertEquals(expectedStats, stats);
        assertEquals(EXPECTED_STAT_TEXT, stats.getFormatted());
    }
}