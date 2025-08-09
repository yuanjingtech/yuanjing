package com.yuanjingtech

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import com.yuanjingtech.di.initializeKoin
import com.yuanjingtech.shared.plugin.TabPluginManager
import com.yuanjingtech.shared.developer.DebugModeProvider
import com.yuanjingtech.plugin.PluginDiscoveryService
import com.yuanjingtech.ui.theme.AppThemeWrapper
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import yuanjing.composeapp.generated.resources.Res
import yuanjing.composeapp.generated.resources.title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
) {
    var initializationStatus by remember { mutableStateOf<String?>(null) }

    // 监听已启用的插件列表
    val enabledPlugins by TabPluginManager.enabledPlugins.collectAsState()

    // 初始化Koin和插件系统
    LaunchedEffect(Unit) {
        try {
            initializeKoin()
            val discoveryResult = PluginDiscoveryService.discoverAndRegisterPlugins()
            initializationStatus =
                "发现 ${discoveryResult.totalDiscovered} 个插件，启用 ${discoveryResult.enabledCount} 个"
            println(initializationStatus)
        } catch (e: Exception) {
            initializationStatus = "初始化失败: ${e.message}"
            println(initializationStatus)
        }
    }

    // 判断是否显示顶部应用栏
    val showTopAppBar = windowSizeClass.windowHeightSizeClass != WindowHeightSizeClass.COMPACT

    AppEnvironment {
        AppThemeWrapper {
            // 当没有插件可用时的处理
            if (enabledPlugins.isEmpty()) {
                Column(
                    modifier = Modifier
                        .safeContentPadding()
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("正在加载插件...")
                    if (initializationStatus != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = initializationStatus!!,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                return@AppThemeWrapper
            }

            // 设置分页状态
            val pagerState = rememberPagerState(pageCount = { enabledPlugins.size })
            val scope = rememberCoroutineScope()

            Column(
                modifier = Modifier
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // 顶部应用栏
                if (showTopAppBar) {
                    Text(
                        stringResource(Res.string.title),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // 动态标签页导航（基于插件）
                if (enabledPlugins.size > 1) {
                    TabRow(
                        selectedTabIndex = pagerState.currentPage,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        enabledPlugins.forEachIndexed { index, plugin ->
                            Tab(
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                text = {
                                    Text(
                                        text = plugin.title,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            )
                        }
                    }
                }

                // 动态标签页内容（基于插件）
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { page ->
                    if (page < enabledPlugins.size) {
                        val plugin = enabledPlugins[page]

                        // 监听调试模式状态
                        val isDeveloperOptionsEnabled by DebugModeProvider.developerOptionsFlow.collectAsState()
                        val isDebugModeEnabled by DebugModeProvider.debugModeFlow.collectAsState()
                        val shouldShowDebugInfo = isDeveloperOptionsEnabled && isDebugModeEnabled

                        // 显示插件内容
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.TopStart
                        ) {
                            plugin.createContent().invoke(Modifier.fillMaxSize())

                            // 调试信息（仅在开发者选项和调试模式都启用时显示）
                            if (shouldShowDebugInfo) {
                                Card(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .align(Alignment.TopEnd),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(
                                            alpha = 0.9f
                                        )
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(6.dp),
                                        verticalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Text(
                                            text = "🐛 DEBUG",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onErrorContainer
                                        )
                                        Text(
                                            text = "ID: ${plugin.id}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onErrorContainer
                                        )
                                        Text(
                                            text = "版本: ${plugin.version}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onErrorContainer
                                        )
                                        Text(
                                            text = "优先级: ${plugin.priority}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onErrorContainer
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}