package sh.miles.pineapple;

import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public final class ColorUtils {

    private ColorUtils(){
        throw new UnsupportedOperationException("no");
    }

    public static Color[] createLinearGradient(@NotNull final Color start, @NotNull final Color end, final int step) {
        Color[] colors = new Color[step];
        int stepR = Math.abs(start.getRed() - end.getRed()) / (step - 1);
        int stepG = Math.abs(start.getGreen() - end.getGreen()) / (step - 1);
        int stepB = Math.abs(start.getBlue() - end.getBlue()) / (step - 1);
        int[] direction = new int[] {
                start.getRed() < end.getRed() ? +1 : -1,
                start.getGreen() < end.getGreen() ? +1 : -1,
                start.getBlue() < end.getBlue() ? +1 : -1
        };

        for (int i = 0; i < step; i++) {
            colors[i] = new Color(start.getRed() + ((stepR * i) * direction[0]), start.getGreen() + ((stepG * i) * direction[1]), start.getBlue() + ((stepB * i) * direction[2]));
        }

        return colors;
    }

}
