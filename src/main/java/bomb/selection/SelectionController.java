package bomb.selection;

import java.util.EnumSet;

public class SelectionController {
    private final EnumSet<Puzzle> puzzleSet;



    public SelectionController() {
        puzzleSet = EnumSet.noneOf(Puzzle.class);
    }

    public EnumSet<Puzzle> getPuzzleSet() {
        return puzzleSet;
    }
}
