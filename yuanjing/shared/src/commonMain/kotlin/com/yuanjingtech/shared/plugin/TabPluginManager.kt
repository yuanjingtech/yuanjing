package com.yuanjingtech.shared.plugin

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.yuanjingtech.shared.home.tab.TabPlugin

/**
 * TabPlugin管理器
 *
 * 管理所有TabPlugin实例，提供响应式的插件列表。
 * 插件注册将通过外部调用进行，以避免在shared模块中引入Koin依赖。
 */
object TabPluginManager {

    // 使用StateFlow提供响应式的插件列表
    private val _availablePlugins = MutableStateFlow<List<TabPlugin>>(emptyList())
    val availablePlugins: StateFlow<List<TabPlugin>> = _availablePlugins.asStateFlow()

    private val _enabledPlugins = MutableStateFlow<List<TabPlugin>>(emptyList())
    val enabledPlugins: StateFlow<List<TabPlugin>> = _enabledPlugins.asStateFlow()

    private var isInitialized = false

    /**
     * 注册插件列表（由外部调用，传入从Koin获取的插件列表）
     */
    fun registerPlugins(plugins: List<TabPlugin>): DiscoveryResult {
        return try {
            // 按优先级排序 (数字越小优先级越高)
            val sortedPlugins = plugins.sortedBy { it.priority }
            
            _availablePlugins.value = sortedPlugins
            _enabledPlugins.value = sortedPlugins // 默认启用所有插件
            
            isInitialized = true
            
            println("🔍 TabPluginManager: Registered ${plugins.size} TabPlugin(s)")
            plugins.forEach { plugin ->
                println("  - ${plugin.title} (${plugin.id}) - priority: ${plugin.priority}")
            }
            
            DiscoveryResult(
                totalDiscovered = plugins.size,
                enabledCount = sortedPlugins.size,
                pluginIds = plugins.map { it.id }
            )
        } catch (e: Exception) {
            println("❌ Failed to register TabPlugins: ${e.message}")
            DiscoveryResult(
                totalDiscovered = 0,
                enabledCount = 0,
                pluginIds = emptyList(),
                error = e.message
            )
        }
    }

    /**
     * 启用指定插件
     */
    fun enablePlugin(pluginId: String): Boolean {
        val plugin = _availablePlugins.value.find { it.id == pluginId }
        return if (plugin != null && !_enabledPlugins.value.contains(plugin)) {
            val currentEnabled = _enabledPlugins.value.toMutableList()
            currentEnabled.add(plugin)
            _enabledPlugins.value = currentEnabled.sortedBy { it.priority }
            println("✅ Enabled plugin: ${plugin.title}")
            true
        } else {
            false
        }
    }

    /**
     * 禁用指定插件
     */
    fun disablePlugin(pluginId: String): Boolean {
        val plugin = _enabledPlugins.value.find { it.id == pluginId }
        return if (plugin != null) {
            _enabledPlugins.value = _enabledPlugins.value.filter { it.id != pluginId }
            println("❌ Disabled plugin: ${plugin.title}")
            true
        } else {
            false
        }
    }

    /**
     * 获取指定插件
     */
    fun getPlugin(pluginId: String): TabPlugin? {
        return _availablePlugins.value.find { it.id == pluginId }
    }

    /**
     * 清理所有插件
     */
    fun cleanup() {
        _availablePlugins.value = emptyList()
        _enabledPlugins.value = emptyList()
        isInitialized = false
        println("🧹 TabPluginManager: Cleaned up all plugins")
    }

    /**
     * 检查是否已初始化
     */
    fun isInitialized(): Boolean = isInitialized
}

/**
 * 插件发现结果
 */
data class DiscoveryResult(
    val totalDiscovered: Int,
    val enabledCount: Int,
    val pluginIds: List<String>,
    val error: String? = null
) {
    val isSuccessful: Boolean = error == null
}