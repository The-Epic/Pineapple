package sh.miles.pineapple.config.adapter;

import sh.miles.pineapple.chat.PineappleChat;
import sh.miles.pineapple.chat.PineappleComponent;

public class PineappleComponentAdapter implements GenericStringAdapter<PineappleComponent> {

    @Override
    public Class<PineappleComponent> getRuntimeType() {
        return (Class<PineappleComponent>) (Object) PineappleComponent.class;
    }

    @Override
    public PineappleComponent fromString(String value) {
        return PineappleChat.component(value);
    }

    @Override
    public String toString(PineappleComponent value) {
        return value.getSource();
    }
}
