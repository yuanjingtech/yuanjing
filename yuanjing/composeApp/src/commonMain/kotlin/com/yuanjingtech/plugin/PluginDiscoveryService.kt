package com.yuanjingtech.plugin

import com.yuanjingtech.shared.home.tab.TabPlugin
import com.yuanjingtech.shared.plugin.TabPluginManager
import com.yuanjingtech.shared.plugin.DiscoveryResult
import com.yuanjingtech.ui.main.MainTabPlugin
import com.yuanjingtech.ui.demo.DemoTabPlugin
import com.yuanjing.jintianchishenme.JintianchishenmeTabPlugin

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
            val plugins = listOf<TabPlugin>(
                MainTabPlugin(),
                DemoTabPlugin(),
                JintianchishenmeTabPlugin()
            )

            println("🔍 PluginDiscoveryService: Discovered ${plugins.size} TabPlugin(s)")
            plugins.forEach { plugin ->
                println("  - ${plugin.title} (${plugin.id}) - priority: ${plugin.priority}")
            }
            
            // 注册到TabPluginManager
            val result = TabPluginManager.registerPlugins(plugins)
            
            println("✅ PluginDiscoveryService: Registration completed - ${result.enabledCount}/${result.totalDiscovered} plugins enabled")
            
            result
        } catch (e: Exception) {
            println("❌ PluginDiscoveryService: Failed to discover plugins - ${e.message}")
            e.printStackTrace()
            DiscoveryResult(
                totalDiscovered = 0,
                enabledCount = 0,
                pluginIds = emptyList(),
                error = e.message
            )
        }
    }

}