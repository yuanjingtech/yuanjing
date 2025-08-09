package com.yuanjingtech.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.yuanjingtech.shared.developer.DebugModeRepository
import com.yuanjingtech.shared.developer.DebugModeManager

/**
 * 设置界面的状态数据类
 */
data class SettingsUiState(
    val isDeveloperOptionsEnabled: Boolean = false,
    val isDebugModeEnabled: Boolean = false,
    val theme: AppTheme = AppTheme.SYSTEM,
    val language: AppLanguage = AppLanguage.SYSTEM
)

/**
 * 应用主题枚举
 */
enum class AppTheme(val displayName: String) {
    LIGHT("浅色主题"),
    DARK("深色主题"),
    SYSTEM("跟随系统")
}

/**
 * 应用语言枚举
 */
enum class AppLanguage(val displayName: String) {
    CHINESE("中文"),
    ENGLISH("English"),
    SYSTEM("跟随系统")
}

/**
 * 设置 ViewModel
 *
 * 管理应用程序设置状态，现在与共享的调试模式数据同步：
 * - 开发者选项开关（同步到共享模块）
 * - 调试模式开关（同步到共享模块）
 * - 主题设置
 * - 语言设置
 */
class SettingsViewModel : ViewModel() {

    // 调试模式管理器
    private val debugModeManager = DebugModeManager(viewModelScope)

    // 本地UI状态（主题和语言）
    private val _localUiState = MutableStateFlow(
        SettingsUiState().copy(
            theme = AppTheme.SYSTEM,
            language = AppLanguage.SYSTEM
        )
    )

    // 合并本地状态和共享调试状态
    val uiState: StateFlow<SettingsUiState> = combine(
        _localUiState,
        DebugModeRepository.isDeveloperOptionsEnabled,
        DebugModeRepository.isDebugModeEnabled
    ) { localState, developerOptions, debugMode ->
        localState.copy(
            isDeveloperOptionsEnabled = developerOptions,
            isDebugModeEnabled = debugMode
        )
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    /**
     * 切换开发者选项开关
     * 现在同步到共享模块
     */
    fun toggleDeveloperOptions() {
        viewModelScope.launch {
            debugModeManager.toggleDeveloperOptions()
        }
    }

    /**
     * 切换调试模式开关
     * 现在同步到共享模块
     */
    fun toggleDebugMode() {
        viewModelScope.launch {
            debugModeManager.toggleDebugMode()
        }
    }

    /**
     * 设置应用主题
     */
    fun setTheme(theme: AppTheme) {
        _localUiState.value = _localUiState.value.copy(theme = theme)
        println("🎨 主题已设置为: ${theme.displayName}")
    }

    /**
     * 设置应用语言
     */
    fun setLanguage(language: AppLanguage) {
        _localUiState.value = _localUiState.value.copy(language = language)
        println("🌐 语言已设置为: ${language.displayName}")
    }

    /**
     * 重置所有设置到默认值
     * 现在包含共享调试状态的重置
     */
    fun resetToDefaults() {
        viewModelScope.launch {
            // 重置本地设置
            _localUiState.value = SettingsUiState().copy(
                theme = AppTheme.SYSTEM,
                language = AppLanguage.SYSTEM
            )

            // 重置共享调试设置
            debugModeManager.resetAll()

            println("🔄 所有设置已重置为默认值")
        }
    }
}
