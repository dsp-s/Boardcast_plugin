package org.broadcastplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BroadcastCommand implements CommandExecutor {
    private final BroadcastPlugin plugin;
    private int currentIndex;

    public BroadcastCommand(BroadcastPlugin plugin) {
        this.plugin = plugin;
        this.currentIndex = 0;
    }

    public void broadcastNextMessage() {
        FileConfiguration config = plugin.getConfig();
        List<String> messages = config.getStringList("messages");

        if (messages.isEmpty()) {
            plugin.getLogger().warning("没有配置广播消息!");
            return;
        }

        // 获取当前消息并递增索引
        String message = messages.get(currentIndex);
        currentIndex = (currentIndex + 1) % messages.size();  // 修正这里使用 size() 而不是 length

        // 广播消息
        String formattedMessage = config.getString("format", "&a[公告] &f%message%")
                .replace("%message%", message);
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', formattedMessage));
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

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        // 权限检查
        if (!sender.hasPermission(plugin.getConfig().getString("permissions.admin-permission"))) {
            sender.sendMessage(ChatColor.RED + "你没有权限使用此命令!");
            return true;
        }

        // 冷却检查
        if (sender instanceof Player && !sender.hasPermission("permissions.bypass-cooldown")) {
            long cooldown = plugin.getConfig().getLong("permissions.command-cooldown");
            // 实现冷却逻辑...
        }
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.getBroadcastManager().reload();
                sender.sendMessage(ChatColor.GREEN + "配置已重载!");
                return true;

            case "add":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "用法: /broadcast add <消息>");
                    return true;
                }
                String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                plugin.getBroadcastManager().addMessage(message);
                sender.sendMessage(ChatColor.GREEN + "已添加广播消息: " + message);
                return true;

            case "list":
                List<String> messages = plugin.getConfig().getStringList("messages");
                sender.sendMessage(ChatColor.GOLD + "=== 广播消息列表 ===");
                for (int i = 0; i < messages.size(); i++) {
                    sender.sendMessage(ChatColor.YELLOW + "" + (i + 1) + ". " + messages.get(i));
                }
                return true;

            default:
                sendHelp(sender);
                return true;
        }
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "=== 广播插件帮助 ===");
        sender.sendMessage(ChatColor.YELLOW + "/broadcast reload - 重载配置");
        sender.sendMessage(ChatColor.YELLOW + "/broadcast add <消息> - 添加广播消息");
        sender.sendMessage(ChatColor.YELLOW + "/broadcast list - 列出所有广播消息");
    }
}