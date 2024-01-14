package sh.miles.pineapple.chat.parse;

import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.bungee.PineappleComponentBuilder;
import sh.miles.pineapple.chat.tag.ColorTag;
import sh.miles.pineapple.chat.tag.DecorationTag;
import sh.miles.pineapple.chat.tag.IteratingTag;

import java.util.Map;

public class ParserContext {

    private final Map<String, Object> replacements;
    private final StyleStack styleStack;

    public ParserContext(@NotNull final Map<String, Object> replacements, StyleStack styleStack) {
        this.replacements = replacements;
        this.styleStack = styleStack;
    }

    public BaseComponent parse(@NotNull final String message, @NotNull final ParserContext context) {
        return PineappleParser.parse(message, context);
    }

    /**
     * Gets a replacement from this ParserContext
     *
     * @param key the key
     * @return the replacement if exists
     */
    public Object getReplacement(String key) {
        final Object value = replacements.get(key);
        if (value == null) {
            throw new NullPointerException("Unable to find key %s for replacement".formatted(key));
        }
        return replacements.get(key);
    }

    public StyleStack getStyleStack() {
        return styleStack;
    }

    /**
     * Applies a style to the given builder and text source
     *
     * @param builder the component builder
     * @param text    the original text source
     * @param context the parser context
     */
    public void applyStyle(@NotNull final PineappleComponentBuilder builder, @NotNull final String text, ParserContext context) {
        final ColorTag color = styleStack.peekColors();
        if (color instanceof IteratingTag iteratingTag) {
            for (int i = 0; i < text.length(); i++) {
                builder.append(String.valueOf(text.charAt(i)), PineappleComponentBuilder.FormatRetention.NONE);
                styleStack.applyTopColor(builder, context);
                styleStack.applyTopClick(builder, context);
                styleStack.applyTopHover(builder, context);
                iteratingTag.next();
                for (final DecorationTag decoration : styleStack.getDecorations()) {
                    decoration.apply(builder, context);
                }
            }
        } else {
            builder.append(text, PineappleComponentBuilder.FormatRetention.NONE);
            styleStack.applyTopColor(builder, context);
            styleStack.applyTopClick(builder, context);
            styleStack.applyTopHover(builder, context);
            for (final DecorationTag decoration : styleStack.getDecorations()) {
                decoration.apply(builder, context);
            }
        }
    }

    /**
     * Applies the style stack to the given component, besides iterating tags which can not be applied to components
     *
     * @param builder the builder
     * @param component the component
     * @param context the context
     */
    public void applyStyle(@NotNull final PineappleComponentBuilder builder, @NotNull final BaseComponent component, ParserContext context) {
        builder.append(component, PineappleComponentBuilder.FormatRetention.NONE);
        styleStack.applyTopColor(builder, context);
        styleStack.applyTopClick(builder, context);
        styleStack.applyTopHover(builder, context);
        for (final DecorationTag decoration : styleStack.getDecorations()) {
            decoration.apply(builder, context);
        }
    }
}

