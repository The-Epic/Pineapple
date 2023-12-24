package sh.miles.pineapple.chat.parse;

import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.PineappleComponentBuilder;
import sh.miles.pineapple.chat.node.BaseNode;
import sh.miles.pineapple.chat.node.BaseNodeParser;
import sh.miles.pineapple.chat.node.TagNode;
import sh.miles.pineapple.chat.node.TextNode;
import sh.miles.pineapple.chat.tag.AbstractTag;
import sh.miles.pineapple.chat.tag.ColorTag;
import sh.miles.pineapple.chat.tag.DecorationTag;
import sh.miles.pineapple.chat.tag.IteratingTag;
import sh.miles.pineapple.chat.tag.TagBuilder;

public final class ParserContext {

    public static final ParserContext INSTANCE = new ParserContext();

    private ParserContext() {
    }

    @NotNull
    public BaseComponent parse(final String message) {
        final PineappleComponentBuilder builder = PineappleComponentBuilder.empty();
        final BaseNode root = BaseNodeParser.parseTree(message);
        parse(root, builder);
        return builder.build();
    }

    private static void parse(@NotNull final BaseNode root, @NotNull final PineappleComponentBuilder builder) {
        parse(root, null, builder, new StyleStack());
    }

    private static void parse(@NotNull final BaseNode root, @Nullable AbstractTag rootTag, @NotNull final PineappleComponentBuilder builder, @NotNull final StyleStack styleStack) {
        for (final BaseNode child : root.getChildren()) {
            AbstractTag tag = null;
            if (child instanceof TagNode tagNode) {
                styleStack.push((tag = TagBuilder.build(tagNode)));
            }

            if (child instanceof TextNode textNode) {
                applyStyle(builder, textNode, styleStack);
            }

            parse(child, tag, builder, styleStack);
        }

        if (rootTag != null) {
            styleStack.pop(rootTag);
        }
    }

    private static void applyStyle(@NotNull final PineappleComponentBuilder builder, @NotNull final TextNode textNode, @NotNull final StyleStack styleStack) {
        final String text = textNode.getText();
        final ColorTag color = styleStack.peekColors();
        if (color instanceof IteratingTag iteratingTag) {
            for (int i = 0; i < text.length(); i++) {
                builder.append(String.valueOf(text.charAt(i)), PineappleComponentBuilder.FormatRetention.NONE);
                styleStack.applyTopColor(builder, INSTANCE);
                styleStack.applyTopClick(builder, INSTANCE);
                styleStack.applyTopHover(builder, INSTANCE);
                iteratingTag.next();
                for (final DecorationTag decoration : styleStack.getDecorations()) {
                    decoration.apply(builder, INSTANCE);
                }
            }
        } else {
            builder.append(textNode.getText(), PineappleComponentBuilder.FormatRetention.NONE);
            styleStack.applyTopColor(builder, INSTANCE);
            styleStack.applyTopClick(builder, INSTANCE);
            styleStack.applyTopHover(builder, INSTANCE);
            for (final DecorationTag decoration : styleStack.getDecorations()) {
                decoration.apply(builder, INSTANCE);
            }
        }
    }

}
