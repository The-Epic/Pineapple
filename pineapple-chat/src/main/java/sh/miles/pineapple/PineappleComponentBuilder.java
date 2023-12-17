package sh.miles.pineapple;


import com.google.common.base.Preconditions;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Better component building implementation for BungeeChat ComponentBuilder that rids the use of BaseComponent arrays
 * which are outdated and ineffective. Due to the pending PR I have taken it upon myself to use this feature it may be
 * removed in the future see {@link net.md_5.bungee.api.chat.ComponentBuilder} for a better description of use.
 * <p>
 * This class will be deprecated for removal in 2 minor versions after the BungeeChat Component Builder build removal.
 */
@SuppressWarnings("deprecation")
public final class PineappleComponentBuilder {

    /**
     * The position for the current part to modify. Modified cursors will automatically reset to the last part after
     * appending new components. Default value at -1 to assert that the builder has no parts.
     */
    private int cursor;
    private final List<BaseComponent> parts;
    private BaseComponent dummy;

    private PineappleComponentBuilder(@NotNull final BaseComponent[] components) {
        this.cursor = -1;
        this.dummy = null;
        this.parts = new ArrayList<>();
        for (BaseComponent component : components) {
            this.parts.add(component.duplicate());
        }
        resetCursor();
    }

    private PineappleComponentBuilder() {
        this.parts = new ArrayList<>();
    }

    private PineappleComponentBuilder(@NotNull final net.md_5.bungee.api.chat.ComponentBuilder original) {
        this(original.getParts().toArray(BaseComponent[]::new));
    }

    private PineappleComponentBuilder(@NotNull final PineappleComponentBuilder original) {
        this(original.parts.toArray(BaseComponent[]::new));
    }

    private PineappleComponentBuilder(@NotNull final BaseComponent component) {
        this(new BaseComponent[]{component});
    }

    private PineappleComponentBuilder(@NotNull final String text) {
        this(new TextComponent(text));
    }

    /**
     * Gets parts
     *
     * @return parts
     */
    public List<BaseComponent> getParts() {
        return this.parts;
    }

    /**
     * Gets cursor
     *
     * @return cursor
     */
    public int getCursor() {
        return this.cursor;
    }

    private BaseComponent getDummy() {
        if (dummy == null) {
            dummy = new BaseComponent() {
                @Override
                public BaseComponent duplicate() {
                    return this;
                }
            };
        }
        return dummy;
    }

    /**
     * Resets the cursor to index of the last element.
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#resetCursor()}
     */
    public void resetCursor() {
        cursor = parts.size() - 1;
    }

    /**
     * Appends a component to the builder and makes it the current target for formatting. The component will have all
     * the formatting from previous part.
     *
     * @param component the component to append
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#append(BaseComponent)}
     */
    public PineappleComponentBuilder append(BaseComponent component) {
        return append(component, FormatRetention.ALL);
    }

    /**
     * Appends a component to the builder and makes it the current target for formatting. You can specify the amount of
     * formatting retained from previous part.
     *
     * @param component the component to append
     * @param retention the formatting to retain
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc
     * {@link net.md_5.bungee.api.chat.ComponentBuilder#append(BaseComponent,
     * net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention)} this method must use a method handle for
     * BaseComponent#isReset as it is not natively exposed so expect slightly slower appendages
     */
    public PineappleComponentBuilder append(BaseComponent component, FormatRetention retention) {
        BaseComponent previous = (parts.isEmpty()) ? null : parts.get(parts.size() - 1);
        if (previous == null) {
            previous = dummy;
            dummy = null;
        }
        if (previous != null) {
            component.copyFormatting(previous, retention.toBungee(), false);
        }
        parts.add(component);
        resetCursor();
        return this;
    }

    /**
     * Appends the components to the builder and makes the last element the current target for formatting. The
     * components will have all the formatting from previous part.
     *
     * @param components the components to append
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#append(BaseComponent[])}
     */
    public PineappleComponentBuilder append(BaseComponent[] components) {
        return append(components, FormatRetention.ALL);
    }


    /**
     * Appends the components to the builder and makes the last element the current target for formatting. You can
     * specify the amount of formatting retained from previous part.
     *
     * @param components the components to append
     * @param retention  the formatting to retain
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc
     * {@link net.md_5.bungee.api.chat.ComponentBuilder#append(BaseComponent[],
     * net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention)}
     */
    public PineappleComponentBuilder append(BaseComponent[] components, FormatRetention retention) {
        Preconditions.checkArgument(components.length != 0, "No components to append");

        for (BaseComponent component : components) {
            append(component, retention);
        }

        return this;
    }

    /**
     * Appends the text to the builder and makes it the current target for formatting. The text will have all the
     * formatting from previous part.
     *
     * @param text the text to append
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#append(String)}
     */
    public PineappleComponentBuilder append(String text) {
        return append(text, FormatRetention.ALL);
    }

    /**
     * Appends the text to the builder and makes it the current target for formatting. You can specify the amount of
     * formatting retained from previous part.
     *
     * @param text      the text to append
     * @param retention the formatting to retain
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc
     * {@link net.md_5.bungee.api.chat.ComponentBuilder#append(String,
     * net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention)}
     */
    public PineappleComponentBuilder append(String text, FormatRetention retention) {
        return append(new TextComponent(text), retention);
    }


    /**
     * Allows joining additional components to this builder using the given {@link Joiner} and
     * {@link FormatRetention#ALL}.
     * <p>
     * Simply executes the provided joiner on this instance to facilitate a chain pattern.
     *
     * @param joiner joiner used for operation
     * @return this ComponentBuilder for chaining
     */
    public PineappleComponentBuilder append(Joiner joiner) {
        return joiner.join(this, FormatRetention.ALL);
    }

    /**
     * Allows joining additional components to this builder using the given {@link Joiner}.
     * <p>
     * Simply executes the provided joiner on this instance to facilitate a chain pattern.
     *
     * @param joiner    joiner used for operation
     * @param retention the formatting to retain
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc
     * {@link net.md_5.bungee.api.chat.ComponentBuilder#append(net.md_5.bungee.api.chat.ComponentBuilder.Joiner,
     * net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention)}
     */
    public PineappleComponentBuilder append(Joiner joiner, FormatRetention retention) {
        return joiner.join(this, retention);
    }

    /**
     * Parse text to BaseComponent[] with colors and format, appends the text to the builder and makes it the current
     * target for formatting. The component will have all the formatting from previous part.
     *
     * @param text the text to append
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#appendLegacy(String)}
     */
    public PineappleComponentBuilder appendLegacy(String text) {
        return append(TextComponent.fromLegacyText(text));
    }

    /**
     * Sets the position of the current component to be modified
     *
     * @param pos the cursor position synonymous to an element position for a list
     * @return this ComponentBuilder for chaining
     * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
     *                                   <p>
     *                                   for official javadoc
     *                                   {@link net.md_5.bungee.api.chat.ComponentBuilder#setCursor(int)}
     */
    public PineappleComponentBuilder setCursor(int pos) throws IndexOutOfBoundsException {
        if ((this.cursor != pos) && (pos < 0 || pos >= parts.size())) {
            throw new IndexOutOfBoundsException("Cursor out of bounds (expected between 0 + " + (parts.size() - 1) + ")");
        }

        this.cursor = pos;
        return this;
    }

    /**
     * Remove the component part at the position of given index.
     *
     * @param pos the index to remove at
     * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
     *                                   <p>
     *                                   for official javadoc
     *                                   {@link net.md_5.bungee.api.chat.ComponentBuilder#removeComponent(int)}
     */
    public void removeComponent(int pos) throws IndexOutOfBoundsException {
        if (parts.remove(pos) != null) {
            resetCursor();
        }
    }

    /**
     * Gets the component part at the position of given index.
     *
     * @param pos the index to find
     * @return the component
     * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
     *                                   <p>
     *                                   for official javadoc
     *                                   {@link net.md_5.bungee.api.chat.ComponentBuilder#getComponent(int)}
     */
    public BaseComponent getComponent(int pos) throws IndexOutOfBoundsException {
        return parts.get(pos);
    }

    /**
     * Gets the component at the position of the cursor.
     *
     * @return the active component or null if builder is empty
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#getCurrentComponent()}
     */
    public BaseComponent getCurrentComponent() {
        return (cursor == -1) ? getDummy() : parts.get(cursor);
    }

    /**
     * Sets the color of the current part.
     *
     * @param color the new color
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#color(ChatColor)}
     */
    public PineappleComponentBuilder color(ChatColor color) {
        getCurrentComponent().setColor(color);
        return this;
    }

    /**
     * Sets the font of the current part.
     *
     * @param font the new font
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#font(String)}
     */
    public PineappleComponentBuilder font(String font) {
        getCurrentComponent().setFont(font);
        return this;
    }

    /**
     * Sets whether the current part is bold.
     *
     * @param bold whether this part is bold
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#bold(boolean)}
     */
    public PineappleComponentBuilder bold(boolean bold) {
        getCurrentComponent().setBold(bold);
        return this;
    }

    /**
     * Sets whether the current part is italic.
     *
     * @param italic whether this part is italic
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#italic(boolean)}
     */
    public PineappleComponentBuilder italic(boolean italic) {
        getCurrentComponent().setItalic(italic);
        return this;
    }

    /**
     * Sets whether the current part is underlined.
     *
     * @param underlined whether this part is underlined
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#underlined(boolean)}
     */
    public PineappleComponentBuilder underlined(boolean underlined) {
        getCurrentComponent().setUnderlined(underlined);
        return this;
    }

    /**
     * Sets whether the current part is strikethrough.
     *
     * @param strikethrough whether this part is strikethrough
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#strikethrough(boolean)}
     */
    public PineappleComponentBuilder strikethrough(boolean strikethrough) {
        getCurrentComponent().setStrikethrough(strikethrough);
        return this;
    }

    /**
     * Sets whether the current part is obfuscated.
     *
     * @param obfuscated whether this part is obfuscated
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#obfuscated(boolean)}
     */
    public PineappleComponentBuilder obfuscated(boolean obfuscated) {
        getCurrentComponent().setObfuscated(obfuscated);
        return this;
    }

    /**
     * Sets the insertion text for the current part.
     *
     * @param insertion the insertion text
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#insertion(String)}
     */
    public PineappleComponentBuilder insertion(String insertion) {
        getCurrentComponent().setInsertion(insertion);
        return this;
    }

    /**
     * Sets the click event for the current part.
     *
     * @param clickEvent the click event
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#event(ClickEvent)}
     */
    public PineappleComponentBuilder event(ClickEvent clickEvent) {
        getCurrentComponent().setClickEvent(clickEvent);
        return this;
    }

    /**
     * Sets the hover event for the current part.
     *
     * @param hoverEvent the hover event
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#event(HoverEvent)}
     */
    public PineappleComponentBuilder event(HoverEvent hoverEvent) {
        getCurrentComponent().setHoverEvent(hoverEvent);
        return this;
    }

    /**
     * Sets the current part back to normal settings. Only text is kept.
     *
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#reset()}
     */
    public PineappleComponentBuilder reset() {
        return retain(FormatRetention.NONE);
    }

    /**
     * Retains only the specified formatting. Text is not modified.
     *
     * @param retention the formatting to retain
     * @return this ComponentBuilder for chaining
     * <p>
     * for official javadoc
     * {@link
     * net.md_5.bungee.api.chat.ComponentBuilder#retain(net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention)}
     */
    public PineappleComponentBuilder retain(FormatRetention retention) {
        getCurrentComponent().retain(retention.toBungee());
        return this;
    }

    /**
     * Returns the component built by this builder. If this builder is empty, an empty text component will be returned.
     *
     * @return the component
     */
    public BaseComponent build() {
        TextComponent base = new TextComponent();
        if (!parts.isEmpty()) {
            List<BaseComponent> cloned = new ArrayList<>(parts);
            cloned.replaceAll(BaseComponent::duplicate);
            base.setExtra(cloned);
        }
        return base;
    }

    /**
     * Returns the components needed to display the message created by this builder.
     * <p>
     * <strong>NOTE:</strong> {@link #build()} is preferred as it will
     * consolidate all components into a single BaseComponent with extra contents as opposed to an array of components
     * which is non-standard and may result in unexpected behavior.
     *
     * @return the created components
     * <p>
     * for official javadoc {@link net.md_5.bungee.api.chat.ComponentBuilder#create()}
     * @deprecated do not use legacy create method prefer use of {@link PineappleComponentBuilder#build()}
     */
    @Deprecated(forRemoval = true)
    public BaseComponent[] create() {
        BaseComponent[] cloned = new BaseComponent[parts.size()];
        int i = 0;
        for (BaseComponent part : parts) {
            cloned[i++] = part.duplicate();
        }
        return cloned;
    }

    /**
     * Removes the array of BaseComponents and creates a single BaseComponent from it
     *
     * @param components the components to turn into a single component
     * @return the end BaseComponent
     */
    public static BaseComponent unarray(final BaseComponent[] components) {
        TextComponent base = new TextComponent();
        base.setExtra(List.of(components));
        return base;
    }

    /**
     * Turns a non array basecomponent into an array base component
     *
     * @param component the component to transition
     * @return the new array
     */
    public static BaseComponent[] array(final BaseComponent component) {
        return PineappleComponentBuilder.of(component).create();
    }

    /**
     * Creates a new Component from a ComponentBuilder
     *
     * @param builder the builder
     * @return the new component
     */
    public static PineappleComponentBuilder of(@NotNull final net.md_5.bungee.api.chat.ComponentBuilder builder) {
        return new PineappleComponentBuilder(builder);
    }

    /**
     * Creates a new component from another component
     *
     * @param original the original component
     * @return the new component
     */
    public static PineappleComponentBuilder of(@NotNull final PineappleComponentBuilder original) {
        return new PineappleComponentBuilder(original);
    }

    /**
     * Creates a component from a non array base component
     *
     * @param component the component to change
     * @return the new component
     */
    public static PineappleComponentBuilder of(@NotNull final BaseComponent component) {
        return new PineappleComponentBuilder(component);
    }

    /**
     * Creates a component from an array of components
     *
     * @param components the component to use
     * @return the new component
     */
    public static PineappleComponentBuilder of(@NotNull final BaseComponent[] components) {
        return new PineappleComponentBuilder(components);
    }

    /**
     * Creates a component from a string
     *
     * @param text the text
     * @return the new component
     */
    public static PineappleComponentBuilder of(@NotNull final String text) {
        return new PineappleComponentBuilder(text);
    }

    /**
     * Creates an empty component
     *
     * @return the new component
     */
    public static PineappleComponentBuilder empty() {
        return new PineappleComponentBuilder();
    }

    /**
     * The policy of how to retain format
     */
    public enum FormatRetention {

        /**
         * Specify that we do not want to retain anything from the previous component.
         */
        NONE,
        /**
         * Specify that we want the formatting retained from the previous component.
         */
        FORMATTING,
        /**
         * Specify that we want the events retained from the previous component.
         */
        EVENTS,
        /**
         * Specify that we want to retain everything from the previous component.
         */
        ALL;

        /**
         * Gets a bungee equivalent of the FormatRetention
         *
         * @return the bungee equivalent
         */
        public net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention toBungee() {
            return net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention.valueOf(name());
        }
    }

    /**
     * Functional interface to join additional components to a ComponentBuilder.
     */
    public interface Joiner {

        /**
         * Joins additional components to the provided {@link net.md_5.bungee.api.chat.ComponentBuilder} and then
         * returns it to fulfill a chain pattern.
         * <p>
         * Retention may be ignored and is to be understood as an optional recommendation to the Joiner and not as a
         * guarantee to have a previous component in builder unmodified.
         *
         * @param componentBuilder to which to append additional components
         * @param retention        the formatting to possibly retain
         * @return input componentBuilder for chaining
         */
        PineappleComponentBuilder join(PineappleComponentBuilder componentBuilder, FormatRetention retention);
    }
}
