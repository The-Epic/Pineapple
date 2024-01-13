package sh.miles.pineapple;

import java.util.ArrayList;
import java.util.List;

/**
 * A Collection of Utilities useful for string manipulation
 *
 * @since 1.0.0-SNAPSHOT
 */
public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("no");
    }

    /**
     * Splits over a character instead of using regex
     *
     * @param string       the string to split
     * @param character    the character to split over
     * @param ignoreQuotes whether or not to ignore the split character in single quotes
     * @return the split string
     * @since 1.0.0-SNAPSHOT
     */
    public static List<String> split(String string, char character, boolean ignoreQuotes) {
        List<String> split = new ArrayList<>();
        char at;
        int last = 0;
        boolean escaped = false;
        for (int i = 0; i < string.length(); i++) {
            at = string.charAt(i);
            if (at == character && !escaped) {
                split.add(string.substring(last, i));
                last = i + 1;
            }

            if (at == '\'') {
                escaped = !escaped;
            }
        }

        if (last < string.length()) {
            split.add(string.substring(last));
        }

        return split;
    }

    /**
     * Splits over a character instead of using regex
     *
     * @param character the character to split over
     * @param string    the string to split
     * @return the split string
     * @since 1.0.0-SNAPSHOT
     */
    public static List<String> split(String string, char character) {
        List<String> split = new ArrayList<>();
        char at;
        int last = 0;
        for (int i = 0; i < string.length(); i++) {
            at = string.charAt(i);
            if (at == character) {
                split.add(string.substring(last, i));
                last = i + 1;
            }
        }

        if (last < string.length()) {
            split.add(string.substring(last));
        }

        return split;
    }
}
