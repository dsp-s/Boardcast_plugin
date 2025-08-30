# 服务器广播插件 | Broadcast Plugin

一个功能丰富的 Minecraft 服务器广播插件，支持定时广播、自定义消息、音效提示等功能。

## ✨ 功能特性

- 📢 **定时广播**：可配置间隔时间自动广播消息
- 🎵 **音效提示**：支持播放自定义音效增强提示效果
- 🌍 **世界过滤**：支持指定世界进行广播
- 🔧 **权限管理**：完善的权限控制系统
- 🎨 **格式自定义**：完全可自定义的消息格式和样式
- 🔄 **随机顺序**：支持随机顺序播放消息

## 📦 安装方法

1. 下载最新版本的 `BroadcastPlugin.jar`
2. 将文件放入服务器的 `plugins/` 文件夹
3. 重启服务器
4. 编辑 `plugins/BroadcastPlugin/config.yml` 进行配置

## ⚙️ 配置说明

插件会自动生成配置文件，主要配置项：

```yaml
# 基础设置
settings:
  broadcast-interval: 600  # 广播间隔(秒)
  random-order: false      # 随机顺序
  enable-sound: true       # 启用音效

# 消息内容
messages:
  - "欢迎加入我们的服务器！"
  - "服务器QQ群：123456789"
