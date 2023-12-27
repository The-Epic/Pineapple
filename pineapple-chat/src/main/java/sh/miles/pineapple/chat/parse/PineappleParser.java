package sh.miles.pineapple.chat.parse;

import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.chat.bungee.PineappleComponentBuilder;
import sh.miles.pineapple.chat.node.BaseNode;
import sh.miles.pineapple.chat.node.BaseNodeParser;
import sh.miles.pineapple.chat.node.TagNode;
import sh.miles.pineapple.chat.node.TextNode;
import sh.miles.pineapple.chat.tag.AbstractTag;
import sh.miles.pineapple.chat.tag.TagBuilder;

import java.util.HashMap;
import java.util.Map;

public final class PineappleParser {

    private PineappleParser() {
    }

    @NotNull
    public static BaseComponent parse(@NotNull final String message) {
        return parse(message, new ParserContext(new HashMap<>(), new StyleStack()));
    }

    @NotNull
    public static BaseComponent parse(@NotNull final String message, @NotNull final Map<String, Object> replacements) {
        return parse(message, new ParserContext(replacements, new StyleStack()));
    }

    static BaseComponent parse(@NotNull final String message, @NotNull final ParserContext context) {
        final BaseNode root = BaseNodeParser.parseTree(message, context);
        final PineappleComponentBuilder builder = PineappleComponentBuilder.empty();
        parse(root, builder, context);
        return builder.build();
    }

    private static void parse(@NotNull final BaseNode root, @NotNull final PineappleComponentBuilder builder, ParserContext context) {
        parse(root, null, builder, context);
    }

    private static void parse(@NotNull final BaseNode root, @Nullable AbstractTag rootTag, @NotNull final PineappleComponentBuilder builder, ParserContext context) {
        for (final BaseNode child : root.getChildren()) {
            AbstractTag tag = null;
            if (child instanceof TagNode tagNode) {
                context.getStyleStack().push((tag = TagBuilder.build(tagNode)));
            }

            if (child instanceof TextNode textNode) {
                context.applyStyle(builder, textNode.getText(), context);
            }

            parse(child, tag, builder, context);
        }

        if (rootTag != null) {
            context.getStyleStack().pop(rootTag);
        }
    }

}
