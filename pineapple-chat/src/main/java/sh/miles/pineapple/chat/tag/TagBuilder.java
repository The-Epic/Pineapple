package sh.miles.pineapple.chat.tag;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.node.TagNode;
import sh.miles.pineapple.chat.tag.base.AbstractTag;
import sh.miles.pineapple.collection.trie.ExecutorTrie;

import java.util.Optional;
import java.util.Queue;

public final class TagBuilder {

    private static final ExecutorTrie<AbstractTagBuilderFunction> executorTrie;
    private static final AbstractTagBuilderFunction NAMED_COLOR = NamedColorTag::new;
    private static final AbstractTagBuilderFunction NAMED_DECOR = NamedDecorationTag::new;

    static {
        executorTrie = new ExecutorTrie<>();
        executorTrie.insert("color", ColorTag::new);
        executorTrie.insert("gradient", GradientTag::new);
        executorTrie.insert("click", ClickEventTag::new);
        executorTrie.insert("hover", HoverEventTag::new);
        executorTrie.insert("lang", TranslatableTag::new);

        // NamedDecorations
        executorTrie.insert("bold", NAMED_DECOR);
        executorTrie.insert("italic", NAMED_DECOR);
        executorTrie.insert("obfuscated", NAMED_DECOR);
        executorTrie.insert("reset", NAMED_DECOR);
        executorTrie.insert("strike_through", NAMED_DECOR);
        executorTrie.insert("underlined", NAMED_DECOR);
        // NamedColorTags
        executorTrie.insert("aqua", NAMED_COLOR);
        executorTrie.insert("red", NAMED_COLOR);
        executorTrie.insert("black", NAMED_COLOR);
        executorTrie.insert("blue", NAMED_COLOR);
        executorTrie.insert("dark_aqua", NAMED_COLOR);
        executorTrie.insert("dark_blue", NAMED_COLOR);
        executorTrie.insert("dark_gray", NAMED_COLOR);
        executorTrie.insert("dark_green", NAMED_COLOR);
        executorTrie.insert("dark_red", NAMED_COLOR);
        executorTrie.insert("dark_purple", NAMED_COLOR);
        executorTrie.insert("gray", NAMED_COLOR);
        executorTrie.insert("gold", NAMED_COLOR);
        executorTrie.insert("green", NAMED_COLOR);
        executorTrie.insert("light_purple", NAMED_COLOR);
        executorTrie.insert("red", NAMED_COLOR);
        executorTrie.insert("white", NAMED_COLOR);
        executorTrie.insert("yellow", NAMED_COLOR);
    }

    private TagBuilder() {
        throw new UnsupportedOperationException("unable to instantiate this class");
    }

    /**
     * Builds a new Tag from the given TagNode
     *
     * @param tagNode the tagNode to build into an AbstractTag
     * @return the AbstractTag created
     */
    public static AbstractTag build(@NotNull final TagNode tagNode) {
        Optional<AbstractTagBuilderFunction> possibleBuilder = executorTrie.findExecutor(tagNode.getNamespace());
        AbstractTagBuilderFunction builder = possibleBuilder.orElseThrow(() -> new IllegalStateException("unable to parse unknown tag %s".formatted(tagNode.getNamespace())));
        return builder.create(tagNode.getArguments(), tagNode.getFullChildTextLength());
    }

    private interface AbstractTagBuilderFunction {
        AbstractTag create(@NotNull final Queue<String> arguments, int childStringLength);
    }
}
