package core.bomb.enumerations;

import static tools.string.StringFormat.FIRST_LETTER_CAPITAL;

public enum ButtonResult {
    TAP, HOLD;

    @Override
    public String toString() {
        return FIRST_LETTER_CAPITAL.apply(name());
    }
}
