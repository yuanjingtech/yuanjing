package com.yuanjingtech.ui.setting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yuanjingtech.ui.setting.components.AppearanceSettingsCard
import com.yuanjingtech.ui.setting.components.DeveloperOptionsCard
import com.yuanjingtech.ui.setting.components.OtherSettingsCard

/**
 * 设置界面主屏幕
 *
 * 提供完整的应用程序设置功能，包括：
 * - 开发者选项开关
 * - 主题和语言设置
 * - 调试功能控制
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel { SettingsViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 页面标题
            Text(
                text = "设置",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // 开发者选项卡片
            DeveloperOptionsCard(
                isDeveloperOptionsEnabled = uiState.isDeveloperOptionsEnabled,
                isDebugModeEnabled = uiState.isDebugModeEnabled,
                onToggleDeveloperOptions = viewModel::toggleDeveloperOptions,
                onToggleDebugMode = viewModel::toggleDebugMode
            )

            // 外观设置卡片
            AppearanceSettingsCard(
                currentTheme = uiState.theme,
                currentLanguage = uiState.language,
                onThemeSelected = viewModel::setTheme,
                onLanguageSelected = viewModel::setLanguage
            )

            // 其他设置卡片
            OtherSettingsCard(
                onResetSettings = viewModel::resetToDefaults
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

