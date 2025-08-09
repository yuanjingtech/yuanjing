package com.yuanjingtech.ui.setting.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yuanjingtech.ui.setting.AppLanguage
import com.yuanjingtech.ui.setting.AppTheme

/**
 * 外观设置卡片
 */
@Composable
fun AppearanceSettingsCard(
    currentTheme: AppTheme,
    currentLanguage: AppLanguage,
    onThemeSelected: (AppTheme) -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit
) {
    Card(
        modifier = Modifier.Companion.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.Companion.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Companion.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "🎨",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "外观设置",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Companion.SemiBold
                )
            }

            // 主题设置
            Text(
                text = "主题",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Companion.Medium
            )

            AppTheme.entries.forEach { theme ->
                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    RadioButton(
                        selected = currentTheme == theme,
                        onClick = { onThemeSelected(theme) }
                    )
                    Text(
                        text = theme.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.Companion.padding(start = 8.dp)
                    )
                }
            }

            HorizontalDivider()

            // 语言设置
            Text(
                text = "语言",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Companion.Medium
            )

            AppLanguage.entries.forEach { language ->
                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    RadioButton(
                        selected = currentLanguage == language,
                        onClick = { onLanguageSelected(language) }
                    )
                    Text(
                        text = language.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.Companion.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

