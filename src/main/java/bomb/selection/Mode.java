package bomb.selection;

import java.util.EnumSet;
import java.util.List;

public enum Mode {
    CENTURION(), PRAETORIAN();

    final EnumSet<Puzzle> requiredPuzzles;

    Mode(Puzzle ... puzzles) {
        requiredPuzzles = EnumSet.copyOf(List.of(puzzles));
    }
}
