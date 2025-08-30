# 📢 Broadcast Plugin for Minecraft
**[简体中文](README_zh.md)**

A feature-rich broadcast plugin for Minecraft servers with customizable messages, sound effects, and world-specific broadcasting.

## ✨ Features

- ⏰ **Scheduled Broadcasting**: Configurable interval for automatic messages
- 🔊 **Sound Effects**: Custom sound notifications for broadcasts
- 🌍 **World Filtering**: Broadcast to specific worlds only
- 🔐 **Permission System**: Full permission control
- 🎨 **Custom Formatting**: Fully customizable message formats
- 🔄 **Random Order**: Optional random message sequencing
- 📋 **Easy Management**: In-game commands for message management

## 📦 Installation

1. Download the latest `BroadcastPlugin.jar`
2. Place it in your server's `plugins/` folder
3. Restart the server
4. Configure `plugins/BroadcastPlugin/config.yml`

## ⚙️ Configuration

The plugin auto-generates a config file with these main options:

```yaml
# Basic Settings
settings:
  broadcast-interval: 600    # Broadcast interval in seconds
  random-order: false        # Random message order
  enable-sound: true         # Enable sound effects

# Message Content
messages:
  - "Welcome to our server! Use /help for assistance"
  - "Join our Discord: discord.gg/example"
  - "Weekly events every Saturday at 8 PM"
```
