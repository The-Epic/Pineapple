package sh.miles.pineapple;

import java.util.ArrayList;
import java.util.List;

public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("no");
    }

    /**
     * Splits over a character instead of using regex
     *
     * @param character the character to split over
     * @return the split string
     */
    public static String[] split(String string, char character) {
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

        return split.toArray(String[]::new);
    }
}
