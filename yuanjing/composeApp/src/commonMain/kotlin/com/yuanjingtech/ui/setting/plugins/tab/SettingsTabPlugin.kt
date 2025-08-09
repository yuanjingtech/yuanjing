package com.yuanjingtech.ui.setting.plugins.tab

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yuanjingtech.shared.home.tab.TabPlugin
import com.yuanjingtech.ui.setting.SettingsScreen

/**
 * 设置标签页插件
 *
 * 提供应用程序设置功能，包括：
 * - 开发者选项开关
 * - 应用偏好设置
 * - 主题配置
 * - 语言设置
 */
@Suppress("unused")
class SettingsTabPlugin : TabPlugin {
    override val id: String = "settings_tab"
    override val title: String = "设置"
    override val version: String = "1.0.0"
    override val priority: Int = 900 // 较低优先级，显示在后面
    override val description: String = "应用程序设置和配置管理，包含开发者选项"

    override fun createContent(): @Composable() ((Modifier) -> Unit) {
        return { modifier ->
            SettingsScreen(modifier = modifier)
        }
    }

    override fun initialize() {
        println("Settings Tab Plugin initialized - 版本: $version")
    }

    override fun cleanup() {
        println("Settings Tab Plugin cleaned up")
    }
}