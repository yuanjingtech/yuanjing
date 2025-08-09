package com.yuanjingtech.shared.appearance

import kotlinx.coroutines.flow.StateFlow

/**
 * 外观设置提供者
 *
 * 为应用程序的其他模块提供外观设置的便捷访问接口。
 * 这是一个单例对象，确保整个应用程序中外观设置的一致性。
 *
 * 使用示例：
 * ```kotlin
 * // 检查当前主题
 * val currentTheme = AppearanceProvider.getCurrentTheme()
 *
 * // 监听主题变化
 * AppearanceProvider.themeFlow.collect { theme ->
 *     // 处理主题变化
 * }
 *
 * // 获取字体缩放因子
 * val scaleFactor = AppearanceProvider.getFontScaleFactor()
 * ```
 */
object AppearanceProvider {

    /**
     * 主题状态流
     */
    val themeFlow: StateFlow<AppTheme> = AppearanceRepository.currentTheme

    /**
     * 语言状态流
     */
    val languageFlow: StateFlow<AppLanguage> = AppearanceRepository.currentLanguage

    /**
     * 字体大小状态流
     */
    val fontSizeFlow: StateFlow<FontSize> = AppearanceRepository.fontSize

    /**
     * 动画效果状态流
     */
    val animationsFlow: StateFlow<Boolean> = AppearanceRepository.animationsEnabled

    /**
     * 动态颜色状态流
     */
    val dynamicColorsFlow: StateFlow<Boolean> = AppearanceRepository.dynamicColorsEnabled

    /**
     * 获取当前主题
     */
    fun getCurrentTheme(): AppTheme {
        return AppearanceRepository.currentTheme.value
    }

    /**
     * 获取当前语言
     */
    fun getCurrentLanguage(): AppLanguage {
        return AppearanceRepository.currentLanguage.value
    }

    /**
     * 获取当前字体大小
     */
    fun getCurrentFontSize(): FontSize {
        return AppearanceRepository.fontSize.value
    }

    /**
     * 获取字体缩放因子
     */
    fun getFontScaleFactor(): Float {
        return AppearanceRepository.fontSize.value.scaleFactor
    }

    /**
     * 检查动画是否启用
     */
    fun areAnimationsEnabled(): Boolean {
        return AppearanceRepository.animationsEnabled.value
    }

    /**
     * 检查动态颜色是否启用
     */
    fun areDynamicColorsEnabled(): Boolean {
        return AppearanceRepository.dynamicColorsEnabled.value
    }

    /**
     * 条件执行动画代码
     * 只有在动画启用时才执行提供的代码块
     */
    inline fun withAnimations(block: () -> Unit) {
        if (areAnimationsEnabled()) {
            block()
        }
    }

    /**
     * 根据当前主题执行代码
     */
    inline fun withTheme(
        onLight: () -> Unit = {},
        onDark: () -> Unit = {},
        onSystem: () -> Unit = {}
    ) {
        when (getCurrentTheme()) {
            AppTheme.LIGHT -> onLight()
            AppTheme.DARK -> onDark()
            AppTheme.SYSTEM -> onSystem()
        }
    }

    /**
     * 根据当前语言执行代码
     */
    inline fun withLanguage(
        onChinese: () -> Unit = {},
        onEnglish: () -> Unit = {},
        onSystem: () -> Unit = {}
    ) {
        when (getCurrentLanguage()) {
            AppLanguage.CHINESE -> onChinese()
            AppLanguage.ENGLISH -> onEnglish()
            AppLanguage.SYSTEM -> onSystem()
        }
    }

    /**
     * 获取本地化显示名称
     */
    fun getLocalizedThemeName(theme: AppTheme, locale: String = "zh"): String {
        return theme.getDisplayName(locale)
    }

    /**
     * 获取本地化语言名称
     */
    fun getLocalizedLanguageName(language: AppLanguage, locale: String = "zh"): String {
        return language.getDisplayName(locale)
    }

    /**
     * 获取本地化字体大小名称
     */
    fun getLocalizedFontSizeName(fontSize: FontSize, locale: String = "zh"): String {
        return fontSize.getDisplayName(locale)
    }

    /**
     * 获取当前设置的完整快照
     */
    fun getCurrentSnapshot(): AppearanceSnapshot {
        return AppearanceRepository.getCurrentSnapshot()
    }
}
