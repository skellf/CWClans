package me.skellf.cwclans.utils.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class MessageConfig {

    private final MiniMessage mm = MiniMessage.miniMessage();
    private final Map<String, String> messages;
    private final FileConfiguration config;
    private final String prefix;

    public MessageConfig(FileConfiguration config, Map<String, String> messages) {

        this.config = config;
        this.prefix = config.getString("messages.prefix");

        this.messages = messages;
    }

    public Component getMessage(String key) {
        return mm.deserialize(prefix + messages.getOrDefault(key, "<red>Неизвестная строка!</red>"));
    }

    public Map<String, String> loadMessages() {
        Map<String, String> newMessages = new HashMap<>();

        newMessages.put("only-players", config.getString("messages.general.only-players"));
        newMessages.put("no-permission", config.getString("messages.general.no-permission"));
        newMessages.put("usage", config.getString("messages.usage"));
        newMessages.put("clan-create.usage", config.getString("messages.create-clan.usage"));
        newMessages.put("clan-created", config.getString("messages.create-clan.created"));
        newMessages.put("error", config.getString("messages.general.error"));
        newMessages.put("insufficient-arguments", config.getString("messages.general.insufficient-arguments"));
        newMessages.put("reloaded", config.getString("messages.general.reloaded"));

        return newMessages;
    }

}
