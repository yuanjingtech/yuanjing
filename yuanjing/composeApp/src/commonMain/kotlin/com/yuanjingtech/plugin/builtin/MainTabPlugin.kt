package com.yuanjingtech.plugin.builtin

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yuanjingtech.shared.home.tab.TabPlugin
import com.yuanjingtech.ui.screens.MainScreen
import com.yuanjingtech.ui.order.OrderViewModel

/**
 * 主页标签页插件
 *
 * 使用 Koin 依赖注入来获取 ViewModels，实现更好的依赖管理。
 */
@Suppress("unused")
class MainTabPlugin : TabPlugin {
    override val id: String = "main_tab"
    override val title: String = "主页"
    override val version: String = "1.0.0"
    override val priority: Int = 1
    override val description: String = "主要功能页面，包含餐食推荐系统，使用 Koin 依赖注入"

    @Composable
    override fun Content(modifier: Modifier) {
        // 直接创建 ViewModel 实例（简化版本）
        val orderViewModel = OrderViewModel()

        MainScreen(
            orderViewModel = orderViewModel,
            modifier = modifier
        )
    }

    override fun initialize() {
        super.initialize()
        println("Main Tab Plugin initialized with Koin DI")
    }

    override fun cleanup() {
        super.cleanup()
        println("Main Tab Plugin cleaned up")
    }
}
