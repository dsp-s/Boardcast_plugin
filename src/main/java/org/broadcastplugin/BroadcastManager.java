package org.broadcastplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.*;

public class BroadcastManager {
    private final BroadcastPlugin plugin;
    private int currentIndex;

    public BroadcastManager(BroadcastPlugin plugin) {
        this.plugin = plugin;
        this.currentIndex = 0;
    }

    public void broadcastNextMessage() {
        FileConfiguration config = plugin.getConfig();
        List<String> messages = config.getStringList("messages");
        List<String> enabledWorlds = config.getStringList("enabled-worlds");

        if (messages.isEmpty()) {
            plugin.getLogger().warning("没有配置广播消息!");
            return;
        }

        // 获取消息
        String message = config.getBoolean("settings.random-order")
                ? messages.get(new Random().nextInt(messages.size()))
                : messages.get(currentIndex);

        currentIndex = (currentIndex + 1) % messages.size();

        // 构建格式化消息
        String formatted = config.getString("formats.prefix", "&a[公告]")
                + " " + message
                + " " + config.getString("formats.suffix", "");

        // 播放音效
        if (config.getBoolean("settings.enable-sound")) {
            Sound sound = Sound.valueOf(config.getString("settings.sound-effect"));
            float volume = (float) config.getDouble("settings.sound-volume");
            float pitch = (float) config.getDouble("settings.sound-pitch");

            Bukkit.getOnlinePlayers().forEach(p -> {
                if (enabledWorlds.isEmpty() || enabledWorlds.contains(p.getWorld().getName())) {
                    p.playSound(p.getLocation(), sound, volume, pitch);
                }
            });
        }

        // 发送广播
        String finalMessage = ChatColor.translateAlternateColorCodes('&', formatted);
        if (enabledWorlds.isEmpty()) {
            Bukkit.broadcastMessage(finalMessage);
        } else {
            Bukkit.getOnlinePlayers().stream()
                    .filter(p -> enabledWorlds.contains(p.getWorld().getName()))
                    .forEach(p -> p.sendMessage(finalMessage));
        }
    }
    public void reload() {
        plugin.reloadConfig();
        currentIndex = 0;
    }

    public void addMessage(String message) {
        List<String> messages = plugin.getConfig().getStringList("messages");
        messages.add(message);
        plugin.getConfig().set("messages", messages);
        plugin.saveConfig();
    }
}