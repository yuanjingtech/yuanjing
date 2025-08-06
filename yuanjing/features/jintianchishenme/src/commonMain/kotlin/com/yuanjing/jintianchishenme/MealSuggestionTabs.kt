package com.yuanjingtech.ui.jintianchishenme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yuanjing.jintianchishenme.data.Meal
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealSuggestionTabs(
    currentMeal: Meal?,
    suggestionMessage: String,
    mealCount: Int,
    mealViewModel: MealSuggestionViewModel,
    modifier: Modifier = Modifier
) {
    val tabTitles = listOf("推荐", "分类", "历史", "统计")
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 共享的头部
        MealSuggestionHeader(
            mealCount = mealCount,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Tab Row
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(title) }
                )
            }
        }

        // Tab Content
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            when (page) {
                0 -> MealSuggestionTab(
                    currentMeal = currentMeal,
                    suggestionMessage = suggestionMessage,
                    onGenerateSuggestion = { mealViewModel.generateMealSuggestion() }
                )
                1 -> MealCategoryTab(viewModel = mealViewModel)
                2 -> MealHistoryTab(viewModel = mealViewModel)
                3 -> MealStatisticsTab(viewModel = mealViewModel)
            }
        }
    }
}

@Composable
private fun MealSuggestionTab(
    currentMeal: Meal?,
    suggestionMessage: String,
    onGenerateSuggestion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MealSuggestionCard(
            currentMeal = currentMeal,
            suggestionMessage = suggestionMessage,
            onGenerateSuggestion = onGenerateSuggestion,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun MealCategoryTab(
    viewModel: MealSuggestionViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MealCategoryQuickSuggestions(
            viewModel = viewModel,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun MealHistoryTab(
    viewModel: MealSuggestionViewModel
) {
    val mealHistory by viewModel.mealHistory.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "历史推荐",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (mealHistory.isEmpty()) {
            Text(
                "还没有推荐历史",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            mealHistory.reversed().take(10).forEach { meal ->
                MealDisplayCard(
                    meal = meal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun MealStatisticsTab(
    viewModel: MealSuggestionViewModel
) {
    // 统计数据的实现
    Text("统计数据", style = MaterialTheme.typography.titleLarge)
}
