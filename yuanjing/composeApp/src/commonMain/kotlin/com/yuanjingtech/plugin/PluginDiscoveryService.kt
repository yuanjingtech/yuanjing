package com.yuanjingtech.plugin

import com.yuanjingtech.shared.home.tab.TabPlugin
import com.yuanjingtech.shared.plugin.TabPluginManager
import com.yuanjingtech.shared.plugin.DiscoveryResult

/**
 * 插件发现服务
 *
 * 管理所有已知的TabPlugin实例，并注册到TabPluginManager中。
 * 简化版本，直接创建已知的插件实例。
 */
object PluginDiscoveryService {

    /**
     * 发现并注册所有已知的TabPlugin实例
     */
    fun discoverAndRegisterPlugins(): DiscoveryResult {
        return try {
            val plugins = mutableListOf<TabPlugin>()
            
            // 添加内置插件
            try {
                val mainPlugin = com.yuanjingtech.plugin.builtin.MainTabPlugin()
                plugins.add(mainPlugin)
                println("✅ Loaded MainTabPlugin")
            } catch (e: Exception) {
                println("❌ Failed to load MainTabPlugin: ${e.message}")
            }
            
            try {
                val demoPlugin = com.yuanjingtech.plugin.builtin.DemoTabPlugin()
                plugins.add(demoPlugin)
                println("✅ Loaded DemoTabPlugin")
            } catch (e: Exception) {
                println("❌ Failed to load DemoTabPlugin: ${e.message}")
            }
            
            // 添加jintianchishenme插件
            try {
                val jintianchishenmePlugin = com.yuanjing.jintianchishenme.JintianchishenmeTabPlugin()
                plugins.add(jintianchishenmePlugin)
                println("✅ Loaded JintianchishenmeTabPlugin")
            } catch (e: Exception) {
                println("❌ Failed to load JintianchishenmeTabPlugin: ${e.message}")
            }
            
            println("🔍 PluginDiscoveryService: Discovered ${plugins.size} TabPlugin(s)")
            
            // 注册到TabPluginManager
            val result = TabPluginManager.registerPlugins(plugins)
            
            println("✅ PluginDiscoveryService: Registration completed - ${result.enabledCount}/${result.totalDiscovered} plugins enabled")
            
            result
        } catch (e: Exception) {
            println("❌ PluginDiscoveryService: Failed to discover plugins - ${e.message}")
            DiscoveryResult(
                totalDiscovered = 0,
                enabledCount = 0,
                pluginIds = emptyList(),
                error = e.message
            )
        }
    }

    /**
     * 重新加载插件
     */
    fun refresh(): DiscoveryResult {
        TabPluginManager.cleanup()
        return discoverAndRegisterPlugins()
    }
}