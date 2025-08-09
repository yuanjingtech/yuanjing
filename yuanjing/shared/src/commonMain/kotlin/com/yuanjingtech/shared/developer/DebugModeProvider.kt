package com.yuanjingtech.shared.developer

import kotlinx.coroutines.flow.StateFlow

/**
 * 调试模式提供者
 *
 * 为应用程序的其他模块提供调试模式状态的便捷访问接口。
 * 这是一个单例对象，确保整个应用程序中调试状态的一致性。
 *
 * 使用示例：
 * ```kotlin
 * // 检查调试模式是否启用
 * if (DebugModeProvider.isDebugModeEnabled()) {
 *     println("调试信息：...")
 * }
 *
 * // 监听调试模式状态变化
 * DebugModeProvider.debugModeFlow.collect { isEnabled ->
 *     // 处理状态变化
 * }
 * ```
 */
object DebugModeProvider {

    /**
     * 调试模式状态流
     * 其他模块可以通过监听此流来响应调试模式状态变化
     */
    val debugModeFlow: StateFlow<Boolean> = DebugModeRepository.isDebugModeEnabled

    /**
     * 开发者选项状态流
     * 其他模块可以通过监听此流来响应开发者选项状态变化
     */
    val developerOptionsFlow: StateFlow<Boolean> = DebugModeRepository.isDeveloperOptionsEnabled

    /**
     * 同步获取当前调试模式状态
     *
     * @return 当前调试模式是否启用
     */
    fun isDebugModeEnabled(): Boolean {
        return DebugModeRepository.getCurrentDebugModeState()
    }

    /**
     * 同步获取当前开发者选项状态
     *
     * @return 当前开发者选项是否启用
     */
    fun isDeveloperOptionsEnabled(): Boolean {
        return DebugModeRepository.getCurrentDeveloperOptionsState()
    }

    /**
     * 检查是否可以启用调试模式
     *
     * @return 是否可以启用调试模式（需要开发者选项已启用）
     */
    fun canEnableDebugMode(): Boolean {
        return DebugModeRepository.canEnableDebugMode()
    }

    /**
     * 条件执行调试代码
     * 只有在调试模式启用时才执行提供的代码块
     *
     * @param block 要在调试模式下执行的代码块
     */
    inline fun debugOnly(block: () -> Unit) {
        if (isDebugModeEnabled()) {
            block()
        }
    }

    /**
     * 条件执行开发者代码
     * 只有在开发者选项启用时才执行提供的代码块
     *
     * @param block 要在开发者模式下执行的代码块
     */
    inline fun developerOnly(block: () -> Unit) {
        if (isDeveloperOptionsEnabled()) {
            block()
        }
    }

    /**
     * 调试日志输出
     * 只有在调试模式启用时才输出日志
     *
     * @param tag 日志标签
     * @param message 日志消息
     */
    fun debugLog(tag: String, message: String) {
        debugOnly {
            println("🐛 [$tag] $message")
        }
    }

    /**
     * 开发者日志输出
     * 只有在开发者选项启用时才输出日志
     *
     * @param tag 日志标签
     * @param message 日志消息
     */
    fun developerLog(tag: String, message: String) {
        developerOnly {
            println("🔧 [$tag] $message")
        }
    }
}
