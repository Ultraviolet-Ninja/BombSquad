package core.bomb.modules.c.colored_switches;

public enum SwitchColor {
    RED("red-switch"), ORANGE("orange-switch"), GREEN("green-switch"), CYAN("cyan-switch"),
    BLUE("blue-switch"), MAGENTA("magenta-switch"), NEUTRAL("reset-state");

    private static final SwitchColor[] COLORS = values();

    private final String cssId;

    SwitchColor(String cssId) {
        this.cssId = cssId;
    }

    public String getCssId() {
        return cssId;
    }

    public static SwitchColor getByIndex(int index) {
        return COLORS[index];
    }
}
