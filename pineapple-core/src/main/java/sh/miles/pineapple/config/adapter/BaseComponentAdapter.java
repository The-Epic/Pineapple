package sh.miles.pineapple.config.adapter;

import net.md_5.bungee.api.chat.BaseComponent;
import sh.miles.pineapple.PineappleChat;

public class BaseComponentAdapter implements StringAdapter<BaseComponent> {

    @Override
    public String toString(final BaseComponent value) {
        return PineappleChat.serialize(value);
    }

    @Override
    public BaseComponent fromString(final String value) {
        return PineappleChat.parse(value);
    }
}
