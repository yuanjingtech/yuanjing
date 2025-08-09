package com.yuanjingtech.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.yuanjingtech.shared.developer.DebugModeRepository
import com.yuanjingtech.shared.developer.DebugModeManager
import com.yuanjingtech.shared.appearance.AppearanceRepository
import com.yuanjingtech.shared.appearance.AppearanceManager
import com.yuanjingtech.shared.appearance.AppTheme
import com.yuanjingtech.shared.appearance.AppLanguage
import com.yuanjingtech.shared.appearance.FontSize

/**
 * 设置界面的状态数据类
 */
data class SettingsUiState(
    val isDeveloperOptionsEnabled: Boolean = false,
    val isDebugModeEnabled: Boolean = false,
    val theme: AppTheme = AppTheme.SYSTEM,
    val language: AppLanguage = AppLanguage.SYSTEM,
    val fontSize: FontSize = FontSize.MEDIUM,
    val animationsEnabled: Boolean = true,
    val dynamicColorsEnabled: Boolean = true
)

/**
 * 设置 ViewModel
 *
 * 管理应用程序设置状态，现在包含完整的外观设置功能：
 * - 开发者选项开关（同步到共享模块）
 * - 调试模式开关（同步到共享模块）
 * - 主题设置（浅色/深色/跟随系统）
 * - 语言设置（中文/英文/跟随系统）
 * - 字体大小设置
 * - 动画效果开关
 * - 动态颜色开关
 */
class SettingsViewModel : ViewModel() {

    // 调试模式管理器
    private val debugModeManager = DebugModeManager(viewModelScope)

    // 外观管理器
    private val appearanceManager = AppearanceManager(viewModelScope)

    // 合并所有状态源
    val uiState: StateFlow<SettingsUiState> = combine(
        DebugModeRepository.isDeveloperOptionsEnabled,
        DebugModeRepository.isDebugModeEnabled,
        AppearanceRepository.currentTheme,
        AppearanceRepository.currentLanguage,
        AppearanceRepository.fontSize,
        AppearanceRepository.animationsEnabled,
        AppearanceRepository.dynamicColorsEnabled
    ) { flows ->
        val developerOptions = flows[0] as Boolean
        val debugMode = flows[1] as Boolean
        val theme = flows[2] as AppTheme
        val language = flows[3] as AppLanguage
        val fontSize = flows[4] as FontSize
        val animations = flows[5] as Boolean
        val dynamicColors = flows[6] as Boolean

        SettingsUiState(
            isDeveloperOptionsEnabled = developerOptions,
            isDebugModeEnabled = debugMode,
            theme = theme,
            language = language,
            fontSize = fontSize,
            animationsEnabled = animations,
            dynamicColorsEnabled = dynamicColors
        )
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    // Debug Mode Methods

    /**
     * 切换开发者选项开关
     */
    fun toggleDeveloperOptions() {
        viewModelScope.launch {
            debugModeManager.toggleDeveloperOptions()
        }
    }

    /**
     * 切换调试模式开关
     */
    fun toggleDebugMode() {
        viewModelScope.launch {
            debugModeManager.toggleDebugMode()
        }
    }

    // Appearance Methods

    /**
     * 设置应用主题
     */
    fun setTheme(theme: AppTheme) {
        appearanceManager.applyTheme(theme)
    }

    /**
     * 设置应用语言
     */
    fun setLanguage(language: AppLanguage) {
        appearanceManager.applyLanguage(language)
    }

    /**
     * 设置字体大小
     */
    fun setFontSize(fontSize: FontSize) {
        appearanceManager.applyFontSize(fontSize)
    }

    /**
     * 切换动画效果
     */
    fun toggleAnimations() {
        appearanceManager.toggleAnimations()
    }

    /**
     * 切换动态颜色
     */
    fun toggleDynamicColors() {
        appearanceManager.toggleDynamicColors()
    }

    /**
     * 重置所有设置到默认值
     */
    fun resetToDefaults() {
        viewModelScope.launch {
            // 重置调试设置
            debugModeManager.resetAll()

            // 重置外观设置
            appearanceManager.resetAll()

            println("🔄 所有设置已重置为默认值")
        }
    }
}
