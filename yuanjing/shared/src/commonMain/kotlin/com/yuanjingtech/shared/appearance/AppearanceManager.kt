package com.yuanjingtech.shared.appearance

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * 外观管理器
 *
 * 提供外观设置的高级管理功能，包括：
 * - 主题切换逻辑
 * - 语言切换处理
 * - 字体缩放应用
 * - 动画效果控制
 * - 状态协调和验证
 */
class AppearanceManager(
    private val scope: CoroutineScope
) {

    /**
     * 综合外观状态流
     */
    val appearanceState: StateFlow<AppearanceState> = combine(
        AppearanceRepository.currentTheme,
        AppearanceRepository.currentLanguage,
        AppearanceRepository.fontSize,
        AppearanceRepository.animationsEnabled,
        AppearanceRepository.dynamicColorsEnabled
    ) { theme, language, fontSize, animations, dynamicColors ->
        AppearanceState(
            theme = theme,
            language = language,
            fontSize = fontSize,
            animationsEnabled = animations,
            dynamicColorsEnabled = dynamicColors,
            isSystemDarkMode = false // 这个值需要从平台获取
        )
    }.stateIn(
        scope = scope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = AppearanceState()
    )

    /**
     * 应用主题切换
     */
    fun applyTheme(theme: AppTheme) {
        scope.launch {
            AppearanceRepository.setTheme(theme)
            onThemeChanged(theme)
        }
    }

    /**
     * 应用语言切换
     */
    fun applyLanguage(language: AppLanguage) {
        scope.launch {
            AppearanceRepository.setLanguage(language)
            onLanguageChanged(language)
        }
    }

    /**
     * 应用字体大小
     */
    fun applyFontSize(fontSize: FontSize) {
        scope.launch {
            AppearanceRepository.setFontSize(fontSize)
            onFontSizeChanged(fontSize)
        }
    }

    /**
     * 切换动画效果
     */
    fun toggleAnimations() {
        scope.launch {
            val current = AppearanceRepository.animationsEnabled.value
            AppearanceRepository.setAnimationsEnabled(!current)
            onAnimationsToggled(!current)
        }
    }

    /**
     * 切换动态颜色
     */
    fun toggleDynamicColors() {
        scope.launch {
            val current = AppearanceRepository.dynamicColorsEnabled.value
            AppearanceRepository.setDynamicColorsEnabled(!current)
            onDynamicColorsToggled(!current)
        }
    }

    /**
     * 重置所有外观设置
     */
    fun resetAll() {
        scope.launch {
            AppearanceRepository.resetToDefaults()
            onSettingsReset()
        }
    }

    /**
     * 获取当前有效主题（考虑系统主题）
     */
    fun getEffectiveTheme(isSystemDarkMode: Boolean): AppTheme {
        return when (AppearanceRepository.currentTheme.value) {
            AppTheme.SYSTEM -> if (isSystemDarkMode) AppTheme.DARK else AppTheme.LIGHT
            else -> AppearanceRepository.currentTheme.value
        }
    }

    /**
     * 获取当前有效语言
     */
    fun getEffectiveLanguage(systemLocale: String): AppLanguage {
        return when (AppearanceRepository.currentLanguage.value) {
            AppLanguage.SYSTEM -> {
                when {
                    systemLocale.startsWith("zh") -> AppLanguage.CHINESE
                    systemLocale.startsWith("en") -> AppLanguage.ENGLISH
                    else -> AppLanguage.ENGLISH // 默认英文
                }
            }
            else -> AppearanceRepository.currentLanguage.value
        }
    }

    // 回调方法 - 可以被子类重写或通过依赖注入扩展

    private fun onThemeChanged(theme: AppTheme) {
        println("🎨 [AppearanceManager] 主题已切换到: ${theme.getDisplayName()}")
        // 这里可以添加主题切换的副作用，比如通知系统栏颜色变化
    }

    private fun onLanguageChanged(language: AppLanguage) {
        println("🌐 [AppearanceManager] 语言已切换到: ${language.getDisplayName()}")
        // 这里可以添加语言切换的副作用，比如重新加载本地化资源
    }

    private fun onFontSizeChanged(fontSize: FontSize) {
        println("📝 [AppearanceManager] 字体大小已切换到: ${fontSize.getDisplayName()}")
        // 这里可以添加字体大小变化的副作用
    }

    private fun onAnimationsToggled(enabled: Boolean) {
        println("✨ [AppearanceManager] 动画效果已${if (enabled) "启用" else "禁用"}")
        // 这里可以添加动画开关的副作用
    }

    private fun onDynamicColorsToggled(enabled: Boolean) {
        println("🌈 [AppearanceManager] 动态颜色已${if (enabled) "启用" else "禁用"}")
        // 这里可以添加动态颜色开关的副作用
    }

    private fun onSettingsReset() {
        println("🔄 [AppearanceManager] 外观设置已重置")
        // 这里可以添加设置重置的副作用
    }
}

/**
 * 外观状态数据类
 */
data class AppearanceState(
    val theme: AppTheme = AppTheme.SYSTEM,
    val language: AppLanguage = AppLanguage.SYSTEM,
    val fontSize: FontSize = FontSize.MEDIUM,
    val animationsEnabled: Boolean = true,
    val dynamicColorsEnabled: Boolean = true,
    val isSystemDarkMode: Boolean = false
) {
    /**
     * 获取有效主题（考虑系统设置）
     */
    fun getEffectiveTheme(): AppTheme {
        return when (theme) {
            AppTheme.SYSTEM -> if (isSystemDarkMode) AppTheme.DARK else AppTheme.LIGHT
            else -> theme
        }
    }

    /**
     * 是否应该使用深色主题
     */
    fun shouldUseDarkTheme(): Boolean {
        return getEffectiveTheme() == AppTheme.DARK
    }
}
