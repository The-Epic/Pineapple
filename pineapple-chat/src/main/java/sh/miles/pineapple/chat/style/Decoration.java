package sh.miles.pineapple.chat.style;

import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

public enum Decoration {
    OBFUSCATED {
        @Override
        public void apply(final @NotNull BaseComponent component, boolean flag) {
            component.setObfuscated(flag);
        }
    },
    BOLD {
        @Override
        public void apply(final @NotNull BaseComponent component, final boolean flag) {
            component.setBold(flag);
        }
    },
    STRIKETHROUGH {
        @Override
        public void apply(final @NotNull BaseComponent component, final boolean flag) {
            component.setStrikethrough(flag);
        }
    },
    UNDERLINED {
        @Override
        public void apply(final @NotNull BaseComponent component, final boolean flag) {
            component.setUnderlined(flag);
        }
    },
    ITALIC {
        @Override
        public void apply(final @NotNull BaseComponent component, final boolean flag) {
            component.setItalic(flag);
        }
    },
    RESET {
        @Override
        public void apply(final @NotNull BaseComponent component, final boolean flag) {
            component.setReset(flag);
        }
    };

    public abstract void apply(@NotNull final BaseComponent component, boolean flag);
}
