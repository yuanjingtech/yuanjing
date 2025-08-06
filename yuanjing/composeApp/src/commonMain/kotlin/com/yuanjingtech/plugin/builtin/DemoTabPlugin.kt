package com.yuanjingtech.plugin.builtin

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yuanjingtech.shared.home.tab.TabPlugin
import com.yuanjingtech.ui.demo.DemoScreen

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

    @Composable
    override fun Content(modifier: Modifier) {
        DemoScreen()
    }

    override fun initialize() {
        super.initialize()
        println("Demo Tab Plugin initialized")
    }

    override fun cleanup() {
        super.cleanup()
        println("Demo Tab Plugin cleaned up")
    }
}
