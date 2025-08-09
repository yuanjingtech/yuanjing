package com.yuanjingtech.shared.appearance

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 外观设置数据仓库
 *
 * 管理应用程序外观相关设置的中央存储，包括：
 * - 主题模式（浅色/深色/跟随系统）
 * - 语言设置（中文/英文/跟随系统）
 * - 字体大小设置
 * - 动画效果开关
 * - 状态持久化
 *
 * 这是应用程序外观设置的单一数据源
 */
object AppearanceRepository {

    // 私有状态流
    private val _currentTheme = MutableStateFlow(AppTheme.SYSTEM)
    private val _currentLanguage = MutableStateFlow(AppLanguage.SYSTEM)
    private val _fontSize = MutableStateFlow(FontSize.MEDIUM)
    private val _animationsEnabled = MutableStateFlow(true)
    private val _dynamicColorsEnabled = MutableStateFlow(true)

    // 公开的只读状态流
    val currentTheme: StateFlow<AppTheme> = _currentTheme.asStateFlow()
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()
    val fontSize: StateFlow<FontSize> = _fontSize.asStateFlow()
    val animationsEnabled: StateFlow<Boolean> = _animationsEnabled.asStateFlow()
    val dynamicColorsEnabled: StateFlow<Boolean> = _dynamicColorsEnabled.asStateFlow()

    /**
     * 设置应用主题
     */
    fun setTheme(theme: AppTheme) {
        _currentTheme.value = theme
        println("🎨 [AppearanceRepository] 主题已设置为: ${theme.displayNameKey}")
    }

    /**
     * 设置应用语言
     */
    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
        println("🌐 [AppearanceRepository] 语言已设置为: ${language.displayNameKey}")
    }

    /**
     * 设置字体大小
     */
    fun setFontSize(size: FontSize) {
        _fontSize.value = size
        println("📝 [AppearanceRepository] 字体大小已设置为: ${size.displayNameKey}")
    }

    /**
     * 设置动画效果
     */
    fun setAnimationsEnabled(enabled: Boolean) {
        _animationsEnabled.value = enabled
        println("✨ [AppearanceRepository] 动画效果已${if (enabled) "启用" else "禁用"}")
    }

    /**
     * 设置动态颜色
     */
    fun setDynamicColorsEnabled(enabled: Boolean) {
        _dynamicColorsEnabled.value = enabled
        println("🌈 [AppearanceRepository] 动态颜色已${if (enabled) "启用" else "禁用"}")
    }

    /**
     * 重置所有外观设置到默认值
     */
    fun resetToDefaults() {
        _currentTheme.value = AppTheme.SYSTEM
        _currentLanguage.value = AppLanguage.SYSTEM
        _fontSize.value = FontSize.MEDIUM
        _animationsEnabled.value = true
        _dynamicColorsEnabled.value = true
        println("🔄 [AppearanceRepository] 外观设置已重置为默认值")
    }

    /**
     * 获取当前设置的同步快照
     */
    fun getCurrentSnapshot(): AppearanceSnapshot {
        return AppearanceSnapshot(
            theme = _currentTheme.value,
            language = _currentLanguage.value,
            fontSize = _fontSize.value,
            animationsEnabled = _animationsEnabled.value,
            dynamicColorsEnabled = _dynamicColorsEnabled.value
        )
    }
}

/**
 * 应用主题枚举
 */
enum class AppTheme(
    val displayNameKey: String,
    val localizedNames: Map<String, String>
) {
    LIGHT(
        displayNameKey = "theme.light",
        localizedNames = mapOf(
            "zh" to "浅色主题",
            "en" to "Light Theme"
        )
    ),
    DARK(
        displayNameKey = "theme.dark",
        localizedNames = mapOf(
            "zh" to "深色主题",
            "en" to "Dark Theme"
        )
    ),
    SYSTEM(
        displayNameKey = "theme.system",
        localizedNames = mapOf(
            "zh" to "跟随系统",
            "en" to "Follow System"
        )
    );

    fun getDisplayName(locale: String = "zh"): String {
        return localizedNames[locale] ?: localizedNames["en"] ?: displayNameKey
    }
}

/**
 * 应用语言枚举
 */
enum class AppLanguage(
    val displayNameKey: String,
    val localeCode: String,
    val localizedNames: Map<String, String>
) {
    CHINESE(
        displayNameKey = "language.chinese",
        localeCode = "zh",
        localizedNames = mapOf(
            "zh" to "中文",
            "en" to "Chinese"
        )
    ),
    ENGLISH(
        displayNameKey = "language.english",
        localeCode = "en",
        localizedNames = mapOf(
            "zh" to "英文",
            "en" to "English"
        )
    ),
    SYSTEM(
        displayNameKey = "language.system",
        localeCode = "auto",
        localizedNames = mapOf(
            "zh" to "跟随系统",
            "en" to "Follow System"
        )
    );

    fun getDisplayName(locale: String = "zh"): String {
        return localizedNames[locale] ?: localizedNames["en"] ?: displayNameKey
    }
}

/**
 * 字体大小枚举
 */
enum class FontSize(
    val displayNameKey: String,
    val scaleFactor: Float,
    val localizedNames: Map<String, String>
) {
    SMALL(
        displayNameKey = "font_size.small",
        scaleFactor = 0.85f,
        localizedNames = mapOf(
            "zh" to "小字体",
            "en" to "Small"
        )
    ),
    MEDIUM(
        displayNameKey = "font_size.medium",
        scaleFactor = 1.0f,
        localizedNames = mapOf(
            "zh" to "标准字体",
            "en" to "Medium"
        )
    ),
    LARGE(
        displayNameKey = "font_size.large",
        scaleFactor = 1.15f,
        localizedNames = mapOf(
            "zh" to "大字体",
            "en" to "Large"
        )
    ),
    EXTRA_LARGE(
        displayNameKey = "font_size.extra_large",
        scaleFactor = 1.3f,
        localizedNames = mapOf(
            "zh" to "超大字体",
            "en" to "Extra Large"
        )
    );

    fun getDisplayName(locale: String = "zh"): String {
        return localizedNames[locale] ?: localizedNames["en"] ?: displayNameKey
    }
}

/**
 * 外观设置快照数据类
 */
data class AppearanceSnapshot(
    val theme: AppTheme,
    val language: AppLanguage,
    val fontSize: FontSize,
    val animationsEnabled: Boolean,
    val dynamicColorsEnabled: Boolean
)
