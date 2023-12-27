package sh.miles.pineapple.chat.bungee;

import net.md_5.bungee.api.chat.BaseComponent;

class DummyBaseComponent extends BaseComponent {

    @Override
    public BaseComponent duplicate() {
        return this;
    }
}
