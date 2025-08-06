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
import com.yuanjingtech.ui.demo.DemoScreen
import com.yuanjingtech.ui.screens.MainScreen
import com.yuanjingtech.ui.jintianchishenme.MealSuggestionScreen
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
    // 判断是否显示顶部应用栏
    val showTopAppBar = windowSizeClass.windowHeightSizeClass != WindowHeightSizeClass.COMPACT

    val tabTitles = listOf("主页", "今天吃什么", "演示")
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })
    val scope = rememberCoroutineScope()

    AppEnvironment {
        MaterialTheme {
            Column(
                modifier = Modifier.safeContentPadding().fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (showTopAppBar) {
                    Text(
                        stringResource(Res.string.title),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                // 标签页内容
                HorizontalPager(
                    state = pagerState, modifier = Modifier.fillMaxWidth().weight(1f)
                ) { page ->
                    when (page) {
                        0 -> MainScreen()
                        1 -> MealSuggestionScreen()
                        2 -> DemoScreen()
                    }
                }
                // 主标签页导航
                TabRow(
                    selectedTabIndex = pagerState.currentPage, modifier = Modifier.fillMaxWidth()
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(selected = pagerState.currentPage == index, onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }, text = {
                            Text(
                                text = title, style = MaterialTheme.typography.labelLarge
                            )
                        })
                    }
                }
            }
        }
    }
}
