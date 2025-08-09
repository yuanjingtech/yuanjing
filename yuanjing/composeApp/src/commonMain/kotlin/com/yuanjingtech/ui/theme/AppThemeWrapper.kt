package com.yuanjingtech.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.yuanjingtech.shared.appearance.AppearanceProvider
import com.yuanjingtech.shared.appearance.AppTheme

/**
 * 应用主题包装器
 *
 * 根据用户设置的外观偏好自动应用主题，包括：
 * - 浅色/深色主题切换
 * - 字体大小缩放
 * - 动态颜色支持
 * - 动画效果控制
 */
@Composable
fun AppThemeWrapper(
    content: @Composable () -> Unit
) {
    // 监听外观设置状态
    val currentTheme by AppearanceProvider.themeFlow.collectAsState()
    val currentFontSize by AppearanceProvider.fontSizeFlow.collectAsState()
    val animationsEnabled by AppearanceProvider.animationsFlow.collectAsState()
    val dynamicColorsEnabled by AppearanceProvider.dynamicColorsFlow.collectAsState()

    // 系统主题状态
    val systemInDarkTheme = isSystemInDarkTheme()

    // 确定有效主题
    val effectiveTheme = when (currentTheme) {
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
        AppTheme.SYSTEM -> systemInDarkTheme
    }

    // 应用字体缩放
    val fontScaleFactor = currentFontSize.scaleFactor
    val density = LocalDensity.current
    val scaledDensity = Density(
        density = density.density,
        fontScale = density.fontScale * fontScaleFactor
    )

    CompositionLocalProvider(
        LocalDensity provides scaledDensity,
        LocalAppearanceSettings provides AppearanceSettings(
            isDarkTheme = effectiveTheme,
            animationsEnabled = animationsEnabled,
            dynamicColorsEnabled = dynamicColorsEnabled,
            fontScaleFactor = fontScaleFactor
        )
    ) {
        MaterialTheme(
            colorScheme = if (dynamicColorsEnabled) {
                // 使用动态颜色（如果平台支持）
                getDynamicColorScheme(effectiveTheme)
            } else {
                // 使用静态颜色方案
                getStaticColorScheme(effectiveTheme)
            },
            content = content
        )
    }
}

/**
 * 外观设置数据类
 */
data class AppearanceSettings(
    val isDarkTheme: Boolean,
    val animationsEnabled: Boolean,
    val dynamicColorsEnabled: Boolean,
    val fontScaleFactor: Float
)

/**
 * 外观设置的 CompositionLocal
 */
val LocalAppearanceSettings = compositionLocalOf {
    AppearanceSettings(
        isDarkTheme = false,
        animationsEnabled = true,
        dynamicColorsEnabled = true,
        fontScaleFactor = 1.0f
    )
}

/**
 * 获取动态颜色方案
 */
@Composable
private fun getDynamicColorScheme(isDarkTheme: Boolean): ColorScheme {
    return if (isDarkTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }
}

/**
 * 获取静态颜色方案
 */
private fun getStaticColorScheme(isDarkTheme: Boolean): ColorScheme {
    return if (isDarkTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }
}

/**
 * 扩展函数：检查是否启用动画
 */
@Composable
fun isAnimationEnabled(): Boolean {
    return LocalAppearanceSettings.current.animationsEnabled
}

/**
 * 扩展函数：获取字体缩放因子
 */
@Composable
fun getFontScaleFactor(): Float {
    return LocalAppearanceSettings.current.fontScaleFactor
}
