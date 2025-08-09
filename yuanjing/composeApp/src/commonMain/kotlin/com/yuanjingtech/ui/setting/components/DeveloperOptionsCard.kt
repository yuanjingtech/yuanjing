package com.yuanjingtech.ui.setting.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * 开发者选项设置卡片
 *
 * 提供开发者选项和调试模式的开关功能，包括：
 * - 开发者选项主开关
 * - 调试模式开关（条件显示）
 * - 视觉反馈（启用时显示错误容器样式）
 */
@Composable
fun DeveloperOptionsCard(
    isDeveloperOptionsEnabled: Boolean,
    isDebugModeEnabled: Boolean,
    onToggleDeveloperOptions: () -> Unit,
    onToggleDebugMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isDeveloperOptionsEnabled) {
                MaterialTheme.colorScheme.errorContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 卡片标题
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "🔧",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "开发者选项",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // 开发者选项开关
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
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