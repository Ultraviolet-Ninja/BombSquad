package core.bomb.modules.dh.emoji;

import core.bomb.Widget;
import core.bomb.tools.filter.Regex;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import static core.bomb.modules.dh.emoji.Emoji.EMOJI_ARRAY;

/**
 * This class deals with the Emoji Math module.
 * It's simple math, but replacing numbers with text emojis.
 */
public class EmojiMath extends Widget {
    @Language("regexp")
    private static final String EMOJI_PATTERN = "(?:[=:][|()]|[|()][=:]){1,2}";
    private static final Regex VALIDATION;

    static {
        VALIDATION = new Regex("^" + EMOJI_PATTERN + "([+\\-])" + EMOJI_PATTERN + "$");
    }

    /**
     * Calculates the sum/difference from the equation of emojis
     *
     * @param input The equation from the TextField
     * @return The values gathered from the emoji equation
     */
    public static int calculate(@NotNull String input) {
        VALIDATION.loadText(input);
        if (!VALIDATION.matchesRegex()) throw new IllegalArgumentException(input + " does not match pattern");

        boolean toAdd = VALIDATION.captureGroup(1).equals("+");
        String translatedEq = toAdd ?
                translateEmojis(input.split("\\+"), true) :
                translateEmojis(input.split("-"), false);

        return calculateRealNumbers(translatedEq, toAdd);
    }

    private static String translateEmojis(String[] samples, boolean add) {
        StringBuilder result = new StringBuilder();
        boolean flag = true;
        for (String half : samples) {
            if (half.length() == 4) {
                result.append(findEmoji(half.substring(0, half.length() / 2)));
                result.append(findEmoji(half.substring(half.length() / 2)));
            } else result.append(findEmoji(half));

            if (flag) {
                flag = false;
                result.append(add ? "+" : "-");
            }
        }
        return result.toString();
    }

    private static String findEmoji(String emoji) {
        for (Emoji emo : EMOJI_ARRAY) {
            if (emo.getLabel().equals(emoji)) {
                return String.valueOf(emo.ordinal());
            }
        }
        return null;
    }

    private static int calculateRealNumbers(String equation, boolean add) throws NumberFormatException {
        String[] toNum = equation.split(add ? "\\+" : "-");
        int[] nums = new int[toNum.length];
        nums[0] = Integer.parseInt(toNum[0]);
        nums[1] = Integer.parseInt(toNum[1]);
        return add ?
                nums[0] + nums[1] :
                nums[0] - nums[1];
    }
}