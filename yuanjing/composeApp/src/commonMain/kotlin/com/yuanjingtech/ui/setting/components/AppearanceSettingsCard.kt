package com.yuanjingtech.ui.setting.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yuanjingtech.shared.appearance.AppTheme
import com.yuanjingtech.shared.appearance.AppLanguage
import com.yuanjingtech.shared.appearance.FontSize

/**
 * 外观设置卡片组件
 *
 * 提供完整的外观自定义功能，包括：
 * - 主题选择（浅色/深色/跟随系统）
 * - 语言设置（中文/英文/跟随系统）
 * - 字体大小调整（4个级别）
 * - 动画效果开关
 * - 动态颜色开关
 */
@Composable
fun AppearanceSettingsCard(
    currentTheme: AppTheme,
    currentLanguage: AppLanguage,
    currentFontSize: FontSize,
    animationsEnabled: Boolean,
    dynamicColorsEnabled: Boolean,
    onThemeSelected: (AppTheme) -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit,
    onFontSizeSelected: (FontSize) -> Unit,
    onAnimationsToggled: () -> Unit,
    onDynamicColorsToggled: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 卡片标题
            CardHeader(
                icon = "🎨",
                title = "外观设置"
            )

            // 主题设置区域
            ThemeSelectionSection(
                currentTheme = currentTheme,
                onThemeSelected = onThemeSelected
            )

            HorizontalDivider()

            // 语言设置区域
            LanguageSelectionSection(
                currentLanguage = currentLanguage,
                onLanguageSelected = onLanguageSelected
            )

            HorizontalDivider()

            // 字体大小设置区域
            FontSizeSelectionSection(
                currentFontSize = currentFontSize,
                onFontSizeSelected = onFontSizeSelected
            )

            HorizontalDivider()

            // 动画和动态颜色设置区域
            EffectsSection(
                animationsEnabled = animationsEnabled,
                dynamicColorsEnabled = dynamicColorsEnabled,
                onAnimationsToggled = onAnimationsToggled,
                onDynamicColorsToggled = onDynamicColorsToggled
            )
        }
    }
}

/**
 * 卡片标题组件
 */
@Composable
private fun CardHeader(
    icon: String,
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = icon,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * 主题选择区域
 */
@Composable
private fun ThemeSelectionSection(
    currentTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "主题",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )

        AppTheme.entries.forEach { theme ->
            SelectionRow(
                selected = currentTheme == theme,
                onClick = { onThemeSelected(theme) },
                title = theme.getDisplayName(),
                subtitle = if (theme == AppTheme.SYSTEM) "根据系统设置自动切换" else null
            )
        }
    }
}

/**
 * 语言选择区域
 */
@Composable
private fun LanguageSelectionSection(
    currentLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "语言",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )

        AppLanguage.entries.forEach { language ->
            SelectionRow(
                selected = currentLanguage == language,
                onClick = { onLanguageSelected(language) },
                title = language.getDisplayName(),
                subtitle = if (language == AppLanguage.SYSTEM) "根据系统语言自动设置" else null
            )
        }
    }
}

/**
 * 字体大小选择区域
 */
@Composable
private fun FontSizeSelectionSection(
    currentFontSize: FontSize,
    onFontSizeSelected: (FontSize) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "字体大小",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )

        FontSize.entries.forEach { fontSize ->
            SelectionRow(
                selected = currentFontSize == fontSize,
                onClick = { onFontSizeSelected(fontSize) },
                title = fontSize.getDisplayName(),
                subtitle = "缩放比例: ${(fontSize.scaleFactor * 100).toInt()}%"
            )
        }
    }
}

/**
 * 显示效果设置区域
 */
@Composable
private fun EffectsSection(
    animationsEnabled: Boolean,
    dynamicColorsEnabled: Boolean,
    onAnimationsToggled: () -> Unit,
    onDynamicColorsToggled: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "显示效果",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )

        // 动画效果开关
        SwitchRow(
            checked = animationsEnabled,
            onCheckedChange = onAnimationsToggled,
            title = "动画效果",
            subtitle = "启用界面切换和交互动画"
        )

        // 动态颜色开关
        SwitchRow(
            checked = dynamicColorsEnabled,
            onCheckedChange = onDynamicColorsToggled,
            title = "动态颜色",
            subtitle = "根据壁纸和系统主题调整应用色彩"
        )
    }
}

/**
 * 选择行组件（单选按钮）
 */
@Composable
private fun SelectionRow(
    selected: Boolean,
    onClick: () -> Unit,
    title: String,
    subtitle: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
            subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * 开关行组件
 */
@Composable
private fun SwitchRow(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = { onCheckedChange() }
        )
    }
}
