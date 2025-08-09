package com.yuanjingtech.ui.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yuanjingtech.shared.home.tab.TabPlugin

/**
 * 演示标签页插件
 *
 * 将原有的DemoScreen功能封装为插件形式，包含平台信息和调试内容。
 */
@Suppress("unused")
class DemoTabPlugin : TabPlugin {
    override val id: String = "demo_tab"
    override val title: String = "演示"
    override val version: String = "1.0.0"
    override val priority: Int = 99
    override val description: String = "演示和调试页面，显示平台信息"

    override fun createContent(): @Composable() ((modifier:Modifier) -> Unit) {
        return { modifier ->
            DemoScreen()
        }
    }

    override fun initialize() {
        println("Demo Tab Plugin initialized")
    }

    override fun cleanup() {
        println("Demo Tab Plugin cleaned up")
    }
}