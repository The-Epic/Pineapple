package sh.miles.pineapple.config.adapter;

import net.md_5.bungee.api.ChatColor;

import java.awt.Color;

public class ColorAdapter implements GenericStringAdapter<ChatColor> {

    @Override
    public Class<ChatColor> getRuntimeType() {
        return ChatColor.class;
    }

    @Override
    public String toString(ChatColor value) {
        return "#" + Integer.toHexString(value.getColor().getRGB());
    }

    @Override
    public ChatColor fromString(String value) {
        if (value.startsWith("#")) {
            value = value.substring(1);
        }

        return ChatColor.of(new Color(Integer.parseInt(value, 16)));
    }
}
