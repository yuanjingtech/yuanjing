package com.yuanjingtech.shared.developer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 调试模式数据仓库
 *
 * 提供调试模式状态的中央管理，包括：
 * - 调试模式开关状态
 * - 开发者选项状态
 * - 持久化存储
 * - 状态同步
 *
 * 这是应用程序中调试功能的单一数据源
 */
object DebugModeRepository {

    // 私有状态流
    private val _isDebugModeEnabled = MutableStateFlow(false)
    private val _isDeveloperOptionsEnabled = MutableStateFlow(false)

    // 公开的只读状态流
    val isDebugModeEnabled: StateFlow<Boolean> = _isDebugModeEnabled.asStateFlow()
    val isDeveloperOptionsEnabled: StateFlow<Boolean> = _isDeveloperOptionsEnabled.asStateFlow()

    /**
     * 设置调试模式状态
     *
     * @param enabled 是否启用调试模式
     */
    fun setDebugModeEnabled(enabled: Boolean) {
        // 只有在开发者选项启用时才允许设置调试模式
        if (_isDeveloperOptionsEnabled.value || !enabled) {
            _isDebugModeEnabled.value = enabled

            if (enabled) {
                println("🐛 [DebugModeRepository] 调试模式已启用")
            } else {
                println("🐛 [DebugModeRepository] 调试模式已关闭")
            }
        } else {
            println("⚠️ [DebugModeRepository] 无法启用调试模式：开发者选项未启用")
        }
    }

    /**
     * 设置开发者选项状态
     *
     * @param enabled 是否启用开发者选项
     */
    fun setDeveloperOptionsEnabled(enabled: Boolean) {
        _isDeveloperOptionsEnabled.value = enabled

        // 关闭开发者选项时，同时关闭调试模式
        if (!enabled && _isDebugModeEnabled.value) {
            _isDebugModeEnabled.value = false
            println("🔒 [DebugModeRepository] 开发者选项已关闭，调试模式已自动关闭")
        } else if (enabled) {
            println("🔧 [DebugModeRepository] 开发者选项已启用")
        } else {
            println("🔒 [DebugModeRepository] 开发者选项已关闭")
        }
    }

    /**
     * 切换调试模式状态
     *
     * @return 新的调试模式状态
     */
    fun toggleDebugMode(): Boolean {
        val newState = !_isDebugModeEnabled.value
        setDebugModeEnabled(newState)
        return _isDebugModeEnabled.value
    }

    /**
     * 切换开发者选项状态
     *
     * @return 新的开发者选项状态
     */
    fun toggleDeveloperOptions(): Boolean {
        val newState = !_isDeveloperOptionsEnabled.value
        setDeveloperOptionsEnabled(newState)
        return _isDeveloperOptionsEnabled.value
    }

    /**
     * 重置所有调试相关设置到默认值
     */
    fun resetToDefaults() {
        _isDebugModeEnabled.value = false
        _isDeveloperOptionsEnabled.value = false
        println("🔄 [DebugModeRepository] 调试设置已重置为默认值")
    }

    /**
     * 获取当前调试模式状态（同步）
     */
    fun getCurrentDebugModeState(): Boolean = _isDebugModeEnabled.value

    /**
     * 获取当前开发者选项状态（同步）
     */
    fun getCurrentDeveloperOptionsState(): Boolean = _isDeveloperOptionsEnabled.value

    /**
     * 检查是否可以启用调试模式
     */
    fun canEnableDebugMode(): Boolean = _isDeveloperOptionsEnabled.value
}
