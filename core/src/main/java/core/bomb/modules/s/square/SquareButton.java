package core.bomb.modules.s.square;

import core.bomb.Widget;
import core.bomb.abstractions.ButtonType;
import org.jetbrains.annotations.NotNull;
import tools.number.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static core.bomb.Widget.IndicatorFilter.LIT;
import static core.bomb.Widget.IndicatorFilter.UNLIT;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static tools.filter.RegexFilter.NUMBER_PATTERN;
import static tools.filter.RegexFilter.filter;
import static tools.string.StringFormat.FIRST_LETTER_CAPITAL;

public class SquareButton extends Widget implements ButtonType {
    //Button colors
    public static final int BLUE = 0, YELLOW = 1, DARK_GRAY = 2, WHITE = 3;
    //Held button light colors
    public static final int ORANGE = 0, GREEN = 1, CYAN = 2;

    private static final Set<String> COLOR_WORDS = new TreeSet<>(asList("Purple", "Indigo", "Maroon", "Jade"));

    public static String solve(int buttonColor, @NotNull String buttonText) throws IllegalArgumentException {
        checkSerialCode();
        validateButtonColor(buttonColor);
        buttonText = FIRST_LETTER_CAPITAL.apply(buttonText);

        if (buttonColor == BLUE && numDoubleAs > numDBatteries) return HOLD;
        if ((buttonColor == BLUE || buttonColor == YELLOW) && matchesGreatestSerialCodeNumber(buttonText))
            return TAP;
        if ((buttonColor == BLUE || buttonColor == YELLOW) && COLOR_WORDS.contains(buttonText)) return HOLD;
        if (buttonText.isEmpty()) return TAP + " when the the two seconds digits on the timer match";
        if (
                (buttonColor != DARK_GRAY && buttonText.length() > countIndicators(LIT)) ||
                        (countIndicators(UNLIT) >= 2 && hasVowelInSerialCode())
        ) return TAP;

        return HOLD;
    }

    private static void validateButtonColor(int buttonColor) throws IllegalArgumentException {
        if (buttonColor < BLUE || buttonColor > WHITE)
            throw new IllegalArgumentException("Invalid button color");
    }

    private static boolean matchesGreatestSerialCodeNumber(String buttonText) {
        return Arrays.stream(filter(serialCode, NUMBER_PATTERN).split(""))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0) == buttonText.length();
    }

    public static String solveForHeldButton(boolean isFlashing, int lightColor) throws IllegalArgumentException {
        if (lightColor < ORANGE || lightColor > CYAN)
            throw new IllegalArgumentException("Invalid light color");

        return isFlashing ?
                handleFlashingLight(lightColor) :
                handleSolidLight(lightColor);
    }

    private static String handleFlashingLight(int lightColor){
        if (lightColor == ORANGE)
            return "Release when the number of seconds is 0 or prime\nPossible number combos: 0, " +
                    generatePrimeSeconds();
        if (lightColor == GREEN)
            return "Release one second after two seconds digits add up to a multiple 4\n" +
                    "Possible number combos: " + possibleCombinations(sum -> sum % 4 == 3);

        return "Release when the number of seconds remaining is a multiple of 7\n"
                + generateMultiplesOfSeven();
    }

    private static String handleSolidLight(int lightColor) {
        StringBuilder sb = new StringBuilder("Release when the two seconds digits add up to ");
        IntPredicate numberToSum;

        if (lightColor == ORANGE) {
            sb.append("3 or 13");
            numberToSum = sum -> sum == 3 || sum == 13;
        } else if (lightColor == GREEN) {
            sb.append("5");
            numberToSum = sum -> sum == 5;
        } else {
            sb.append("7");
            numberToSum = sum -> sum == 7;
        }

        return sb.append("\nPossible number combos: ")
                .append(possibleCombinations(numberToSum))
                .toString();
    }

    private static String possibleCombinations(IntPredicate numberToSum) {
        List<String> possibilities = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                int sum = i + j;
                if (numberToSum.test(sum))
                    possibilities.add(String.valueOf(sum));
            }
        }
        return String.join(", ", possibilities);
    }

    private static String generatePrimeSeconds() {
        return IntStream.rangeClosed(0, 60)
                .filter(MathUtils::isPrime)
                .mapToObj(String::valueOf)
                .collect(joining(", "));
    }

    private static String generateMultiplesOfSeven() {
        if (numStartingMinutes == 0) return "Number of starting minutes not set.\n" +
                "Cannot discern valid multiples of 7";

        return "Possible combos: " + IntStream.rangeClosed(1, numStartingMinutes * 60)
                .filter(second -> second % 7 == 0)
                .mapToObj(second -> (second/60) + ":" + (second%60))
                .collect(joining(", "));
    }
}
