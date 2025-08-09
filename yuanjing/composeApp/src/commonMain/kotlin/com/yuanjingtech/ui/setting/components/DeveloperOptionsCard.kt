package com.yuanjingtech.ui.setting.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * 开发者选项设置卡片
 */
@Composable
fun DeveloperOptionsCard(
    isDeveloperOptionsEnabled: Boolean,
    isDebugModeEnabled: Boolean,
    onToggleDeveloperOptions: () -> Unit,
    onToggleDebugMode: () -> Unit
) {
    Card(
        modifier = Modifier.Companion.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isDeveloperOptionsEnabled) {
                MaterialTheme.colorScheme.errorContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
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
                    text = "🔧",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "开发者选项",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Companion.SemiBold
                )
            }

            // 开发者选项开关
            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Column {
                    Text(
                        text = "启用开发者选项",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "开启后可使用调试功能和高级设置",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = isDeveloperOptionsEnabled,
                    onCheckedChange = { onToggleDeveloperOptions() }
                )
            }

            // 调试模式开关（仅在开发者选项启用时显示）
            if (isDeveloperOptionsEnabled) {
                HorizontalDivider()
                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "调试模式",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "显示详细日志和调试信息",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = isDebugModeEnabled,
                        onCheckedChange = { onToggleDebugMode() }
                    )
                }
            }
        }
    }
}