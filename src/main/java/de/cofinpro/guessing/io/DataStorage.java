package de.cofinpro.guessing.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import de.cofinpro.guessing.decisiontree.Node;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * class that does all the persisting and loading of the game's decision tree. The dataType parameter is set from
 * the command line using Apache's picocli library.
 */
public class DataStorage {

    private static final String FILE_PREFIX = "animals.";
    private ObjectMapper objectMapper;

    @SuppressWarnings("unused")
    @CommandLine.Option(names = "-type", description = "specify the data type (JSON, XML, YAML)",
            defaultValue = "JSON")
    private DataType dataType;

    public Optional<Node> loadTree() throws IOException {
        initializeObjectMapper();
        return Optional.ofNullable(Files.exists(Path.of(getFilename()))
                ? objectMapper.readValue(new File(getFilename()), Node.class) : null);
    }

    private String getFilename() {
        return FILE_PREFIX + dataType.toString().toLowerCase();
    }

    private void initializeObjectMapper() {
        objectMapper = switch (dataType) {
            case JSON -> new JsonMapper();
            case XML -> new XmlMapper();
            case YAML -> new YAMLMapper();
        };
    }

    public void saveGameTree(Node decisionTree) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(getFilename()), decisionTree);
    }

    enum DataType {
        JSON,
        XML,
        YAML
    }
}
