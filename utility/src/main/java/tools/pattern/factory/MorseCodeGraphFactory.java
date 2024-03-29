package tools.pattern.factory;

import com.opencsv.CSVReader;
import tools.data.structures.graph.ListGraph;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

@SuppressWarnings("ConstantConditions")
public class MorseCodeGraphFactory {
    private static final String FILENAME = "morseCode.csv";

    private MorseCodeGraphFactory() {}

    public static ListGraph<String> createGraph() throws IllegalStateException {
        ListGraph<String> graph = new ListGraph<>(true);
        InputStream in = MorseCodeGraphFactory.class.getResourceAsStream(FILENAME);
        CSVReader reader = new CSVReader(new InputStreamReader(in, UTF_8));

        for (String[] line : reader) {
            String letters = line[1];
            for (String letter : letters.split("_")) {
                graph.addEdge(line[0], letter);
            }
        }

        try {
            reader.close();
            return graph;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
