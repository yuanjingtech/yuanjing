package com.yuanjingtech.shared.home.tab

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 标签页插件接口
 *
 * 定义了标签页插件的基本规范，任何想要作为标签页的功能模块都需要实现此接口。
 *
 * 特性：
 * - 支持动态注册和注销
 * - 提供标签页基本信息
 * - 封装完整的UI组件
 * - 支持优先级排序
 */
interface TabPlugin {
    /**
     * 插件唯一标识符
     */
    val id: String

    /**
     * 标签页显示名称
     */
    val title: String

    /**
     * 插件版本
     */
    val version: String

    /**
     * 标签页显示优先级（数值越小优先级越高）
     */
    val priority: Int get() = 100

    /**
     * 插件是否可用
     */
    val isEnabled: Boolean get() = true

    /**
     * 插件描述信息
     */
    val description: String get() = ""

    /**
     * 标签页内容组件构造器
     *
     * 返回一个构造 Composable 的函数，用于创建标签页的UI组件
     *
     * @return 返回一个接受 Modifier 参数的 Composable 函数
     */
    fun createContent(): @Composable() ((modifier: Modifier) -> Unit)

    /**
     * 插件初始化方法
     */
    fun initialize() {}

    /**
     * 插件清理方法
     */
    fun cleanup() {}
}