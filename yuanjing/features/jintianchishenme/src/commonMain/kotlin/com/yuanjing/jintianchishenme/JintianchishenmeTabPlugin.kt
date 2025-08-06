package com.yuanjing.jintianchishenme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yuanjing.jintianchishenme.ui.JintianchishenmeScreen
import com.yuanjingtech.shared.home.tab.TabPlugin

/**
 * 今天吃什么标签页插件
 *
 * 将今天吃什么功能模块封装为标签页插件，可以动态加载到主应用中。
 * 这是一个独立的功能模块，展示了插件化架构的强大扩展能力。
 */
@Suppress("unused")
class JintianchishenmeTabPlugin : TabPlugin {
    override val id: String = "jintianchishenme_tab"
    override val title: String = "今天吃什么"
    override val version: String = "2.0.0"
    override val priority: Int = 10 // 高优先级，显示在前面
    override val description: String = "专业的餐食推荐功能模块，提供智能化的用餐建议"

    @Composable
    override fun Content(modifier: Modifier) {
        JintianchishenmeScreen(modifier = modifier)
    }

    override fun initialize() {
        super.initialize()
        println("今天吃什么插件初始化完成 - 版本: $version")
    }

    override fun cleanup() {
        super.cleanup()
        println("今天吃什么插件已清理")
    }
}
