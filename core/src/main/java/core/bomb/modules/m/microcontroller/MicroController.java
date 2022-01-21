package core.bomb.modules.m.microcontroller;

import core.bomb.Widget;
import core.bomb.enumerations.Indicator;
import core.bomb.enumerations.Port;
import core.bomb.modules.m.microcontroller.chip.AbstractController;
import core.bomb.tools.filter.Regex;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static core.bomb.tools.filter.RegexFilter.filter;

public class MicroController extends Widget {
    public static List<Color> getPinColors(@NotNull String moduleSerialNumbers, AbstractController controller)
            throws IllegalArgumentException {
        validateInput(moduleSerialNumbers, controller);
        if (containsRequiredNumbers(moduleSerialNumbers))
            return controller.traversePins(0);
        else if (hasLitIndicator(Indicator.SIG) || doesPortExists(Port.RJ45))
            return controller.traversePins(1);
        else if (filter(serialCode, new Regex("[clrx18]")).length() > 0)
            return controller.traversePins(2);
        else if (numbersMatch(moduleSerialNumbers))
            return controller.traversePins(3);
        return controller.traversePins(4);
    }

    private static void validateInput(String moduleSerialNumbers, AbstractController controller) {
        checkSerialCode();
        if (!moduleSerialNumbers.matches("\\d{1,2}"))
            throw new IllegalArgumentException("Module serial number wasn't 2 numbers");
        if (controller == null)
            throw new IllegalArgumentException("Controller Type wasn't set");
    }

    private static boolean containsRequiredNumbers(String moduleSerialNumbers) {
        return moduleSerialNumbers.contains("1") || moduleSerialNumbers.contains("4");
    }

    private static boolean numbersMatch(String moduleSerialNumbers) {
        String lastNum = moduleSerialNumbers.substring(moduleSerialNumbers.length() - 1);
        return lastNum.contains(String.valueOf(getAllBatteries()));
    }
}