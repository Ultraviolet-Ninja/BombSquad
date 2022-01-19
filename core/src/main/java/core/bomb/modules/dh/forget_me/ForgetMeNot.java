package core.bomb.modules.dh.forget_me;

import core.bomb.Widget;
import core.bomb.tools.filter.Regex;
import core.bomb.tools.filter.RegexFilter;
import core.bomb.enumerations.Indicator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntUnaryOperator;

import static core.bomb.enumerations.Port.SERIAL;

public class ForgetMeNot extends Widget {
    private static final IntUnaryOperator LEAST_SIG_DIGIT = num -> num % 10;
    private static final IntUnaryOperator MOST_SIG_DIGIT =
            num -> (int) (num / Math.pow(10, Math.floor(Math.log10(num))));
    private static final List<Byte> FINAL_CODE = new ArrayList<>(100);

    private static byte largestSerialCodeNumber = -1;

    public static void add(int stageNumber) throws IllegalStateException {
        if (!isForgetMeNotActive)
            throw new IllegalStateException("Forget Me Not wasn't activated");

        if (largestSerialCodeNumber == -1)
            throw new IllegalStateException("The serial code was not set");

        if (numModules == 0)
            throw new IllegalArgumentException("Need to set the number of modules for this to work");

        FINAL_CODE.add(createNextNumber(stageNumber));
    }

    private static byte createNextNumber(int stageNumber) {
        if (FINAL_CODE.isEmpty())
            return (byte) LEAST_SIG_DIGIT.applyAsInt(createFirstNumber(stageNumber));

        if (FINAL_CODE.size() == 1)
            return (byte) LEAST_SIG_DIGIT.applyAsInt(createSecondNumber(stageNumber));

        return (byte) LEAST_SIG_DIGIT.applyAsInt(createSucceedingNumber(stageNumber));
    }

    private static int createFirstNumber(int stageNumber) {
        if (hasUnlitIndicator(Indicator.CAR))
            return stageNumber + 2;

        int numLitIndicators = countIndicators(IndicatorFilter.LIT);
        int numUnlitIndicators = countIndicators(IndicatorFilter.UNLIT);

        if (numUnlitIndicators > numLitIndicators)
            return stageNumber + 7;

        if (numUnlitIndicators == 0)
            return stageNumber + numLitIndicators;

        return stageNumber + getSerialCodeLastDigit();
    }

    private static int createSecondNumber(int stageNumber) {
        if (doesPortExists(SERIAL) && countNumbersInSerialCode() > 2)
            return stageNumber + largestSerialCodeNumber;

        return stageNumber + FINAL_CODE.get(0) +
                ((FINAL_CODE.get(0) % 2 == 0) ? 1 : -1);
    }

    private static int createSucceedingNumber(int stageNumber) {
        int length = FINAL_CODE.size();
        if (FINAL_CODE.get(length - 1) == 0 || FINAL_CODE.get(length - 2) == 0)
            return stageNumber + largestSerialCodeNumber;

        if (bothPreviousNumbersAreEven())
            return stageNumber + smallestOddDigitInSerialCode();

        return stageNumber + MOST_SIG_DIGIT.applyAsInt(
                FINAL_CODE.get(length - 1) + FINAL_CODE.get(length - 2)
        );
    }

    private static boolean bothPreviousNumbersAreEven() {
        int length = FINAL_CODE.size();
        int firstPrevious = FINAL_CODE.get(length - 1);
        int secondPrevious = FINAL_CODE.get(length - 2);
        return  firstPrevious% 2 == 0 && secondPrevious % 2 == 0;
    }

    private static int smallestOddDigitInSerialCode() {
        Regex singleNumberRegex = new Regex("\\d", serialCode);
        return singleNumberRegex.stream()
                .mapToInt(Integer::parseInt)
                .filter(num -> num % 2 == 1)
                .min()
                .orElse(9);
    }

    public static void updateLargestValueInSerial() {
        RegexFilter.SERIAL_CODE_PATTERN.loadText(serialCode);
        if (!RegexFilter.SERIAL_CODE_PATTERN.matchesRegex()) {
            largestSerialCodeNumber = -1;
            return;
        }

        Regex singleNumberRegex = new Regex("\\d", serialCode);

        if (singleNumberRegex.hasMatch()) {
            for (String num : singleNumberRegex) {
                if (Integer.parseInt(num) > largestSerialCodeNumber)
                    largestSerialCodeNumber = Byte.parseByte(num);
            }
        }
    }

    public static void undoLastStage() {
        int size = FINAL_CODE.size();
        if (size != 0)
            FINAL_CODE.remove(size - 1);
    }

    public static String stringifyFinalCode() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < FINAL_CODE.size(); i++) {
            sb.append(FINAL_CODE.get(i));
            if (i % 3 == 2)
                sb.append("-");
        }
        return sb.toString();
    }

    public static int getStage() {
        return FINAL_CODE.size();
    }

    public static void reset() {
        FINAL_CODE.clear();
        updateLargestValueInSerial();
    }
}
