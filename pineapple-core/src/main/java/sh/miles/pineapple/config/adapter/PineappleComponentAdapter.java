package sh.miles.pineapple.config.adapter;

import sh.miles.pineapple.chat.PineappleChat;
import sh.miles.pineapple.chat.PineappleComponent;

public class PineappleComponentAdapter implements StringAdapter<PineappleComponent> {

    @Override
    public String toString(final PineappleComponent value) {
        return value.getSource();
    }

    @Override
    public PineappleComponent fromString(final String value) {
        return PineappleChat.component(value);
    }
}
