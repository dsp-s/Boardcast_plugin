package org.broadcastplugin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class BroadcastPlugin extends JavaPlugin {
    private BukkitTask broadcastTask;
    private BroadcastManager broadcastManager;

    @Override
    public void onEnable() {
        // 保存默认配置
        saveDefaultConfig();

        // 初始化广播管理器
        broadcastManager = new BroadcastManager(this);

        // 注册命令
        getCommand("broadcast").setExecutor(new BroadcastCommand(this));

        // 启动定时广播
        startBroadcastTask();

        getLogger().info("BroadcastPlugin 已启用!");
    }

    @Override
    public void onDisable() {
        // 取消所有任务
        if (broadcastTask != null) {
            broadcastTask.cancel();
        }

        getLogger().info("BroadcastPlugin 已禁用!");
    }

    private void startBroadcastTask() {
        long interval = getConfig().getLong("interval", 600) * 20; // 默认10分钟(600秒)
        broadcastTask = getServer().getScheduler().runTaskTimer(
                this,
                broadcastManager::broadcastNextMessage,
                100L, // 5秒后开始(100 ticks)
                interval
        );
    }

    public BroadcastManager getBroadcastManager() {
        return broadcastManager;
    }
}