package sh.miles.pineapple.chat.style;

import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.ReflectionUtils;

import java.awt.Color;
import java.lang.invoke.MethodHandle;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class ChatColorUtils {

    private static final MethodHandle CHAT_COLOR_CONSTRUCTOR;
    private static final Map<String, ChatColor> BY_NAME;

    static {
        CHAT_COLOR_CONSTRUCTOR = ReflectionUtils.getConstructor(ChatColor.class, new Class<?>[]{String.class, String.class, int.class});
        BY_NAME = (Map<String, ChatColor>) ReflectionUtils.getField(ChatColor.class, "BY_NAME", Map.class);
    }

    private static ChatColor create(String hex, final int rgb) {
        try {
            return (ChatColor) CHAT_COLOR_CONSTRUCTOR.invokeExact(hex, hex, rgb);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    @NotNull
    public static ChatColor create(@NotNull final Color color) {
        String string = color.toString();
        try {
            return (ChatColor) CHAT_COLOR_CONSTRUCTOR.invokeExact(string, string, color.getRGB());
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    @NotNull
    public static ChatColor from(@NotNull final String string) {
        if (string.charAt(0) == '#') {
            return fromHexString(string);
        } else {
            return fromNamedColor(string);
        }
    }

    public static ChatColor fromHexString(@NotNull final String string) {
        if (string.length() > 7) {
            throw new IllegalArgumentException("Unable to parse malformed hex string");
        }

        return create(string, Integer.parseInt(string.substring(1), 16));
    }

    public static ChatColor fromNamedColor(@NotNull final String string) {
        return BY_NAME.get(string.toUpperCase());
    }

    @NotNull
    public static ChatColor[] createLinearGradient(@NotNull final Color start, @NotNull final Color end, final int step) {
        ChatColor[] colors = new ChatColor[step];
        int stepR = Math.abs(start.getRed() - end.getRed()) / (step - 1);
        int stepG = Math.abs(start.getGreen() - end.getGreen()) / (step - 1);
        int stepB = Math.abs(start.getBlue() - end.getBlue()) / (step - 1);
        int[] direction = new int[]{
                start.getRed() < end.getRed() ? +1 : -1,
                start.getGreen() < end.getGreen() ? +1 : -1,
                start.getBlue() < end.getBlue() ? +1 : -1
        };

        for (int i = 0; i < step; i++) {
            colors[i] = create(new Color(start.getRed() + ((stepR * i) * direction[0]), start.getGreen() + ((stepG * i) * direction[1]), start.getBlue() + ((stepB * i) * direction[2])));
        }

        return colors;
    }

}
