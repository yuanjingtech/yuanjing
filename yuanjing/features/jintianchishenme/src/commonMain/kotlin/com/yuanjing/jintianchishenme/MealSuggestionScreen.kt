package com.yuanjingtech.ui.jintianchishenme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yuanjingtech.ui.jintianchishenme.MealSuggestionViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * 餐食推荐屏幕组件
 *
 * 这是一个完整的屏幕组件，封装了所有的ViewModel和业务逻辑，可以开箱即用。
 * 包含以下功能：
 * - 随机餐食推荐
 * - 按分类快速推荐
 * - 推荐历史记录
 * - 餐食数据统计
 *
 * 使用方式：
 * ```kotlin
 * @Composable
 * fun MyApp() {
 *     MealSuggestionScreen()
 * }
 * ```
 *
 * @param modifier 修饰符，用于自定义布局
 * @param viewModel 可选的ViewModel实例，通常用于测试或自定义配置
 */
@Composable
fun MealSuggestionScreen(
    modifier: Modifier = Modifier,
    viewModel: MealSuggestionViewModel = viewModel()
) {
    // 收集所有需要的状态
    val currentMeal by viewModel.currentMeal.collectAsState()
    val suggestionMessage by viewModel.suggestionMessage.collectAsState()
    val mealCount by viewModel.mealCount.collectAsState()

    // 主要UI布局
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 应用标题
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🍽️ 今天吃什么",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "智能餐食推荐助手",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
            // 使用封装好的标签页组件
            MealSuggestionTabs(
                currentMeal = currentMeal,
                suggestionMessage = suggestionMessage,
                mealCount = mealCount,
                mealViewModel = viewModel,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * 餐食推荐屏幕预览组件
 * 用于开发和设计阶段的预览
 */
@Composable
@Preview
fun MealSuggestionScreenPreview() {
    MaterialTheme {
        MealSuggestionScreen()
    }
}
