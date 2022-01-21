package core.bomb.modules.ab.blind_alley;

import core.bomb.Widget;

import static core.bomb.enumerations.Indicator.BOB;
import static core.bomb.enumerations.Indicator.CAR;
import static core.bomb.enumerations.Indicator.CLR;
import static core.bomb.enumerations.Indicator.FRK;
import static core.bomb.enumerations.Indicator.FRQ;
import static core.bomb.enumerations.Indicator.IND;
import static core.bomb.enumerations.Indicator.MSA;
import static core.bomb.enumerations.Indicator.NSA;
import static core.bomb.enumerations.Indicator.SIG;
import static core.bomb.enumerations.Indicator.SND;
import static core.bomb.enumerations.Indicator.TRN;
import static core.bomb.enumerations.Port.DVI;
import static core.bomb.enumerations.Port.PARALLEL;
import static core.bomb.enumerations.Port.PS2;
import static core.bomb.enumerations.Port.RCA;
import static core.bomb.enumerations.Port.RJ45;
import static core.bomb.enumerations.Port.SERIAL;
import static core.bomb.tools.logic.BitConverter.TO_INT;

/**
 * This class works on the Blind Alley module, updating the internal 2-D array whenever
 * changes to specific widgets are made and exporting that info whenever the user displays
 * the 'Blind Alley' tab pane. Blind Alley in a widget-dependant module that require you to press
 * specific sections of the module based on the indicators, LED's, ports and batteries are on the bomb.
 */
public class BlindAlley extends Widget {
    private static int[][] alleyCat = new int[3][3];

    private static void alleyUpdate() {
        topLeft();
        topMid();
        left();
        middle();
        right();
        bottomLeft();
        bottomMid();
        bottomRight();
    }

    private static void topLeft() {
        alleyCat[0][0] = TO_INT.apply(hasUnlitIndicator(BOB)) + TO_INT.apply(hasLitIndicator(CAR)) +
                TO_INT.apply(hasLitIndicator(IND)) + TO_INT.apply(numHolders % 2 == 0 && numHolders != 0);
    }

    private static void topMid() {
        alleyCat[0][1] = TO_INT.apply(hasUnlitIndicator(NSA)) + TO_INT.apply(hasLitIndicator(FRK)) +
                TO_INT.apply(hasUnlitIndicator(CAR)) + TO_INT.apply(doesPortExists(RJ45));
    }

    private static void left() {
        alleyCat[1][0] = TO_INT.apply(hasUnlitIndicator(FRQ)) + TO_INT.apply(hasUnlitIndicator(IND)) +
                TO_INT.apply(hasUnlitIndicator(TRN)) + TO_INT.apply(doesPortExists(DVI));
    }

    private static void middle() {
        int batterySum = getAllBatteries();
        alleyCat[1][1] = TO_INT.apply(hasUnlitIndicator(SIG)) + TO_INT.apply(hasUnlitIndicator(SND)) +
                TO_INT.apply(hasLitIndicator(NSA)) + TO_INT.apply(batterySum % 2 == 0 && batterySum != 0);
    }

    private static void right() {
        alleyCat[1][2] = TO_INT.apply(hasLitIndicator(BOB)) + TO_INT.apply(hasLitIndicator(CLR)) +
                TO_INT.apply(doesPortExists(PS2)) + TO_INT.apply(doesPortExists(SERIAL));
    }

    private static void bottomLeft() {
        alleyCat[2][0] = TO_INT.apply(hasLitIndicator(FRQ)) + TO_INT.apply(hasLitIndicator(SIG)) +
                TO_INT.apply(hasLitIndicator(TRN)) + TO_INT.apply(hasEvenNumberInSerialCode());
    }

    private static void bottomMid() {
        alleyCat[2][1] = TO_INT.apply(hasUnlitIndicator(FRK)) + TO_INT.apply(hasLitIndicator(MSA)) +
                TO_INT.apply(doesPortExists(PARALLEL)) + TO_INT.apply(hasVowelInSerialCode());
    }

    private static void bottomRight() {
        alleyCat[2][2] = TO_INT.apply(hasUnlitIndicator(CLR)) + TO_INT.apply(hasUnlitIndicator(MSA)) +
                TO_INT.apply(hasLitIndicator(SND)) + TO_INT.apply(doesPortExists(RCA));
    }

    /**
     * Exports the information of which button to press
     *
     * @return A 2D array with the information
     */
    public static int[][] getAlleyCat() {
        alleyUpdate();
        return alleyCat;
    }

    public static void reset() {
        alleyCat = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    }
}
