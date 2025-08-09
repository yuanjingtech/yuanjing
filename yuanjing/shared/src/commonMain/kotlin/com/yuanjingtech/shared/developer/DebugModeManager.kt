package com.yuanjingtech.shared.developer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * 调试模式管理器
 *
 * 提供调试模式的高级管理功能，包括：
 * - 业务逻辑处理
 * - 状态协调
 * - 日志管理
 * - 开发者工具集成
 */
class DebugModeManager(
    private val scope: CoroutineScope
) {

    /**
     * 调试模式状态流
     */
    val debugModeState: StateFlow<Boolean> = DebugModeRepository.isDebugModeEnabled

    /**
     * 开发者选项状态流
     */
    val developerOptionsState: StateFlow<Boolean> = DebugModeRepository.isDeveloperOptionsEnabled

    /**
     * 综合调试状态流 - 结合调试模式和开发者选项状态
     */
    val combinedDebugState: StateFlow<DebugState> = combine(
        debugModeState,
        developerOptionsState
    ) { debugMode, developerOptions ->
        DebugState(
            isDebugModeEnabled = debugMode,
            isDeveloperOptionsEnabled = developerOptions,
            canEnableDebugMode = developerOptions
        )
    }.stateIn(
        scope = scope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = DebugState()
    )

    /**
     * 启用调试模式
     */
    fun enableDebugMode() {
        scope.launch {
            if (DebugModeRepository.canEnableDebugMode()) {
                DebugModeRepository.setDebugModeEnabled(true)
                onDebugModeEnabled()
            } else {
                println("⚠️ [DebugModeManager] 无法启用调试模式：需要先启用开发者选项")
            }
        }
    }

    /**
     * 禁用调试模式
     */
    fun disableDebugMode() {
        scope.launch {
            DebugModeRepository.setDebugModeEnabled(false)
            onDebugModeDisabled()
        }
    }

    /**
     * 启用开发者选项
     */
    fun enableDeveloperOptions() {
        scope.launch {
            DebugModeRepository.setDeveloperOptionsEnabled(true)
            onDeveloperOptionsEnabled()
        }
    }

    /**
     * 禁用开发者选项
     */
    fun disableDeveloperOptions() {
        scope.launch {
            DebugModeRepository.setDeveloperOptionsEnabled(false)
            onDeveloperOptionsDisabled()
        }
    }

    /**
     * 切换调试模式
     */
    fun toggleDebugMode() {
        scope.launch {
            val newState = DebugModeRepository.toggleDebugMode()
            if (newState) {
                onDebugModeEnabled()
            } else {
                onDebugModeDisabled()
            }
        }
    }

    /**
     * 切换开发者选项
     */
    fun toggleDeveloperOptions() {
        scope.launch {
            val newState = DebugModeRepository.toggleDeveloperOptions()
            if (newState) {
                onDeveloperOptionsEnabled()
            } else {
                onDeveloperOptionsDisabled()
            }
        }
    }

    /**
     * 调试模式启用时的回调
     */
    private fun onDebugModeEnabled() {
        println("🐛 [DebugModeManager] 调试模式已启用 - 开始详细日志记录")
        // 可以在这里添加更多调试模式启用时的逻辑
        // 例如：启用详细日志、开启性能监控等
    }

    /**
     * 调试模式禁用时的回调
     */
    private fun onDebugModeDisabled() {
        println("🐛 [DebugModeManager] 调试模式已禁用 - 停止详细日志记录")
        // 可以在这里添加更多调试模式禁用时的逻辑
        // 例如：关闭详细日志、停止性能监控等
    }

    /**
     * 开发者选项启用时的回调
     */
    private fun onDeveloperOptionsEnabled() {
        println("🔧 [DebugModeManager] 开发者选项已启用 - 开发者工具可用")
        // 可以在这里添加更多开发者选项启用时的逻辑
    }

    /**
     * 开发者选项禁用时的回调
     */
    private fun onDeveloperOptionsDisabled() {
        println("🔒 [DebugModeManager] 开发者选项已禁用 - 开发者工具不可用")
        // 可以在这里添加更多开发者选项禁用时的逻辑
    }

    /**
     * 重置所有调试设置
     */
    fun resetAll() {
        scope.launch {
            DebugModeRepository.resetToDefaults()
            println("🔄 [DebugModeManager] 所有调试设置已重置")
        }
    }
}

/**
 * 调试状态数据类
 */
data class DebugState(
    val isDebugModeEnabled: Boolean = false,
    val isDeveloperOptionsEnabled: Boolean = false,
    val canEnableDebugMode: Boolean = false
)
