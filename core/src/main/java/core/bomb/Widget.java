package core.bomb;

import core.bomb.enumerations.Indicator;
import core.bomb.enumerations.Port;
import core.bomb.enumerations.TrinarySwitch;
import core.bomb.modules.dh.forget_me.ForgetMeNot;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

import static tools.filter.RegexFilter.CHAR_FILTER;
import static tools.filter.RegexFilter.EMPTY_FILTER_RESULTS;
import static tools.filter.RegexFilter.NUMBER_PATTERN;
import static tools.filter.RegexFilter.SERIAL_CODE_PATTERN;
import static tools.filter.RegexFilter.VOWEL_FILTER;
import static tools.filter.RegexFilter.filter;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 * Widget class carries all the important widgets of the current bomb.
 * This class is extended by the Module classes, and all bomb widgets are accessible by those classes,
 * as well as the MainController to add/subtract to the widgets.
 */
public class Widget {
    protected static boolean isSouvenirActive, isForgetMeNotActive;
    protected static int numDoubleAs, numDBatteries, numHolders, numModules, numPortPlates, numStartingMinutes;
    protected static String serialCode = "", twoFactor = "";

    protected static final Indicator[] INDICATOR_ARRAY = Indicator.values();

    private static int[] portArray = {0, 0, 0, 0, 0, 0};

    public static void setNumHolders(int numHolders) {
        if (numHolders >= 0) {
            Widget.numHolders = numHolders;
        }
    }

    public static void setStartTime(int startTime) {
        if (startTime >= 0)
            numStartingMinutes = startTime;
    }

    public static void setSerialCode(String serialCode) {
        Widget.serialCode = serialCode;
        updatesModules();
    }

    private static void updatesModules() {
        if (isForgetMeNotActive) ForgetMeNot.updateLargestValueInSerial();
    }

    public static void setDoubleAs(int doubleAs) {
        if (doubleAs >= 0) {
            numDoubleAs = doubleAs;
        }
    }

    public static void setDBatteries(int dBatteries) {
        if (dBatteries >= 0) {
            numDBatteries = dBatteries;
        }
    }

    public static void setIndicator(@NotNull TrinarySwitch state, @NotNull Indicator which) {
        INDICATOR_ARRAY[which.ordinal()].setState(state);
    }

    public static void setNumModules(int numModules) {
        if (numModules >= 0) {
            Widget.numModules = numModules;
        }
    }

    public static void setNumberOfPlates(int plates) {
        if (plates >= 0) {
            numPortPlates = plates;
        }
    }

    public static void setTwoFactor(String twoFactor) {
        Widget.twoFactor = twoFactor;
    }

    public static void setIsSouvenirActive(boolean set) {
        isSouvenirActive = set;
    }

    public static void setIsForgetMeNotActive(boolean set) {
        isForgetMeNotActive = set;
        updatesModules();
    }

    public static void setPortValue(@NotNull Port which, int newValue) {
        portArray[which.ordinal()] = newValue;
    }

    public static int getNumHolders() {
        return numHolders;
    }

    public static int getNumModules() {
        return numModules;
    }

    public static int getPortQuantity(@NotNull Port which) {
        return portArray[which.ordinal()];
    }

    public static int countPortTypes() {
        return (int) stream(portArray)
                .filter(port -> port > 0)
                .count();
    }

    public static boolean getIsForgetMeNotActive() {
        return isForgetMeNotActive;
    }

    public static boolean getIsSouvenirActive() {
        return isSouvenirActive;
    }

    public static int getAllBatteries() {
        return numDBatteries + numDoubleAs;
    }

    public static int getNumPortPlates() {
        return numPortPlates;
    }

    public static String getTwoFactor() {
        return twoFactor;
    }

    public static void checkSerialCode() throws IllegalArgumentException {
        SERIAL_CODE_PATTERN.loadText(serialCode);
        if (!SERIAL_CODE_PATTERN.matchesRegex())
            throw new IllegalArgumentException("""
                Serial Code is required
                Please check formatting on Widget page""");
    }

    protected static boolean hasEvenNumberInSerialCode() {
        String sample = filter(serialCode, NUMBER_PATTERN);

        for (char numberChar : sample.toCharArray()) {
            if (numberChar % 2 == 0)
                return true;
        }
        return false;
    }

    /**
     * Finds the last number in the Serial Code
     *
     * @return An int of the last digit from a String
     */
    protected static int getSerialCodeLastDigit() {
        return Character.getNumericValue(serialCode.charAt(serialCode.length() - 1));
    }

    /**
     * Looks to if any listed Indicators are on the current bomb
     *
     * @param indicators The array of possible Indicators
     * @return True if any Indicator is found
     */
    protected static boolean hasFollowingIndicators(Indicator @NotNull ... indicators) {
        for (Indicator current : indicators) {
            if (hasIndicator(current)) return true;
        }
        return false;
    }

    /**
     * Checks to see if a specified Indicator is on the bomb, whether lit or unlit
     *
     * @param ind The Indicator to check
     * @return True if the Indicator is found
     */
    protected static boolean hasIndicator(@NotNull Indicator ind) {
        return hasLitIndicator(ind) || hasUnlitIndicator(ind);
    }

    /**
     * Checks to see if a specified lit Indicator is on the bomb
     *
     * @param ind The Indicator to check
     * @return True if the lit Indicator is found
     */
    protected static boolean hasLitIndicator(@NotNull Indicator ind) {
        return INDICATOR_ARRAY[ind.ordinal()].getState() == TrinarySwitch.ON;
    }

    /**
     * Checks to see if a specified unlit Indicator is on the bomb
     *
     * @param ind The Indicator to check
     * @return True if the unlit Indicator is found
     */
    protected static boolean hasUnlitIndicator(@NotNull Indicator ind) {
        return INDICATOR_ARRAY[ind.ordinal()].getState() == TrinarySwitch.OFF;
    }

    protected static boolean hasVowelInSerialCode() {
        return !EMPTY_FILTER_RESULTS.test(serialCode, VOWEL_FILTER);
    }

    /**
     * Counts the number of letters that appear in the Serial Code
     *
     * @return The number of letters
     */
    protected static int countLettersInSerialCode() {
        return filter(serialCode, CHAR_FILTER).length();
    }

    /**
     * Counts the number of numbers that appear in the Serial Code
     *
     * @return The number of numbers
     */
    protected static int countNumbersInSerialCode() {
        return filter(serialCode, NUMBER_PATTERN).length();
    }

    /**
     * Checks to see if the bomb contains more that the required amount of a specified ports
     *
     * @param port    The port to check
     * @param howMany The required amount
     * @return True if the bomb contains more the required amount
     */
    protected static boolean hasMorePortsThanSpecified(@NotNull Port port, int howMany) {
        return portArray[port.ordinal()] > howMany;
    }

    protected static int calculateTotalPorts() {
        return stream(portArray).sum();
    }

    protected static EnumSet<Indicator> getFilteredSetOfIndicators(IndicatorFilter filter) {
        EnumSet<Indicator> allIndicators = EnumSet.allOf(Indicator.class);

        List<Indicator> tempList = allIndicators.stream()
                .filter(indicator -> filter.test(indicator.getState()))
                .collect(toList());

        return tempList.isEmpty() ?
                EnumSet.noneOf(Indicator.class) :
                EnumSet.copyOf(tempList);
    }

    protected static boolean doesPortExists(@NotNull Port port) {
        return portArray[port.ordinal()] > 0;
    }

    /**
     * Counts all indicators, whether lit, unlit or all if specified
     *
     * @param filter Indicates what indicators should be counted, whether ON, OFF or both
     * @return The number of indicators
     */
    protected static int countIndicators(IndicatorFilter filter) {
        return getFilteredSetOfIndicators(filter).size();
    }

    public static void resetProperties() {
        numDoubleAs = 0;
        numDBatteries = 0;
        numHolders = 0;
        numModules = 0;
        numPortPlates = 0;
        numStartingMinutes = 0;
        serialCode = "";
        twoFactor = "";
        portArray = new int[]{0, 0, 0, 0, 0, 0};

        for (Indicator ind : INDICATOR_ARRAY)
            ind.setState(TrinarySwitch.UNKNOWN);
    }

    public enum IndicatorFilter implements Predicate<TrinarySwitch> {
        LIT {
            @Override
            public boolean test(TrinarySwitch state) {
                return state == TrinarySwitch.ON;
            }
        }, UNLIT {
            @Override
            public boolean test(TrinarySwitch state) {
                return state == TrinarySwitch.OFF;
            }
        }, ALL_PRESENT {
            @Override
            public boolean test(TrinarySwitch state) {
                return state != TrinarySwitch.UNKNOWN;
            }
        }
    }
}

