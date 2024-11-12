package org.night.nightcore.minimessage;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.night.nightcore.Main;

public class Minimessage {
    private Main plugin;
    public Minimessage(Main plugin) {this.plugin = plugin;}
    public String MessageCheange(String string) {
        MiniMessage mm = MiniMessage.miniMessage();
        Component parsed = mm.deserialize(string);
        String lastString = LegacyComponentSerializer.legacySection().serialize(parsed);
        return lastString;
    }

}
