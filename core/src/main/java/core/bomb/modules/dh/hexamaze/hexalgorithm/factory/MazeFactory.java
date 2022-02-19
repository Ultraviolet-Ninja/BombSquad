package core.bomb.modules.dh.hexamaze.hexalgorithm.factory;

import core.bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode;
import core.bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape;
import core.bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexWall;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static core.bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape.CIRCLE;
import static core.bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape.DOWN_TRIANGLE;
import static core.bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape.HEXAGON;
import static core.bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape.LEFT_TRIANGLE;
import static core.bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape.RIGHT_TRIANGLE;
import static core.bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape.UP_TRIANGLE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toCollection;

@SuppressWarnings("ConstantConditions")
public class MazeFactory {
    public static @NotNull List<HexNode> createMaze() throws IllegalStateException {
        InputStream in = MazeFactory.class.getResourceAsStream("maze.csv");
        Reader reader = new BufferedReader(new InputStreamReader(in, UTF_8));
        try (CSVReader csvReader = new CSVReader(reader)) {
            return csvReader.readAll().stream()
                    .flatMap(Arrays::stream)
                    .map(line -> line.split(" "))
                    .map(data -> new HexNode(decodeShape(data[1]), decodeWalls(data[0])))
                    .toList();
        } catch (CsvException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static @Nullable HexShape decodeShape(@NotNull String code) {
        return switch(code) {
            case "c" -> CIRCLE;
            case "h" -> HEXAGON;
            case "lt" -> LEFT_TRIANGLE;
            case "rt" -> RIGHT_TRIANGLE;
            case "ut" -> UP_TRIANGLE;
            case "dt" -> DOWN_TRIANGLE;
            default -> null;
        };
    }

    public static @NotNull EnumSet<HexWall> decodeWalls(@NotNull String code) {
        HexWall[] allWalls = HexWall.values();

        return stream(code.split(""))
                .mapToInt(Integer::parseInt)
                .mapToObj(num -> allWalls[num])
                .collect(toCollection(() -> EnumSet.noneOf(HexNode.HexWall.class)));
    }
}
