package com.yuanjingtech

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import com.yuanjingtech.data.Meal
import com.yuanjingtech.data.OrderUiState
import com.yuanjingtech.ui.MealSuggestionViewModel
import com.yuanjingtech.ui.OrderViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import yuanjing.composeapp.generated.resources.Res
import yuanjing.composeapp.generated.resources.compose_multiplatform
import yuanjing.composeapp.generated.resources.str_arr
import yuanjing.composeapp.generated.resources.title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    orderViewModel: OrderViewModel = viewModel { OrderViewModel() },
    mealViewModel: MealSuggestionViewModel = viewModel { MealSuggestionViewModel() },
) {
    val orderState: OrderUiState by orderViewModel.uiState.collectAsState(
        initial = OrderUiState(
            pickupOptions = listOf(
                "xxxxx"
            )
        )
    )
    val currentMeal by mealViewModel.currentMeal.collectAsState()
    val suggestionMessage by mealViewModel.suggestionMessage.collectAsState()
    val mealCount by mealViewModel.mealCount.collectAsState()
    
    // Determines whether the top app bar should be displayed
    val showTopAppBar = windowSizeClass.windowHeightSizeClass != WindowHeightSizeClass.COMPACT
    
    AppEnvironment {
        MaterialTheme {
            var showContent by remember { mutableStateOf(true) }
            Column(
                modifier = Modifier
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (showTopAppBar) {
                    Text(stringResource(Res.string.title))
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text("语言和区域: ")
                    Text(LocalAppLocale.current)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text("主题模式: ")
                    Text(if (LocalAppTheme.current) "深色模式" else "浅色模式")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text("密度: ")
                    Text("${LocalAppDensity.current.density}")
                }
                
                // Meal Suggestion System
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "🍽️ 今天吃什么？",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                suggestionMessage,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            
                            currentMeal?.let { meal ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    colors = androidx.compose.material3.CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            meal.name,
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                        if (meal.tags.isNotEmpty()) {
                                            Text(
                                                "特色：${meal.tags.joinToString("、")}",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                        Text(
                                            "类别：${meal.category}",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        if (meal.calories > 0) {
                                            Text(
                                                "热量：${meal.calories} kcal",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                            
                            Button(
                                onClick = { mealViewModel.generateMealSuggestion() },
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Text("随机推荐一个")
                            }
                        }
                    }
                    
                    // Quick category suggestions
                    FlowRow(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("中餐", "西餐", "川菜", "面食").forEach { category ->
                            Button(
                                onClick = { mealViewModel.suggestByCategory(category) },
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                Text(category)
                            }
                        }
                    }
                    
                    Text(
                        "共收录了 $mealCount 道菜",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                
                FlowRow {
                    Text("欢迎使用Jetpack Compose Multiplatform!")
                    Text("${windowSizeClass.windowHeightSizeClass}")
                    Text(
                        "Compose Multiplatform!",
                        modifier = Modifier.background(Color.Red)
                            .padding(10.dp)
                            .background(Color.Blue)
                            .padding(5.dp)
                            .background(Color.Yellow)
                    )
                }
                
                Text(stringArrayResource(Res.array.str_arr)[0])
                Text(stringArrayResource(Res.array.str_arr)[1])
                Text(stringArrayResource(Res.array.str_arr)[2])
                
                Button(onClick = { showContent = !showContent }) {
                    Text("点击!")
                }
                
                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text("平台: $greeting")
                    }
                }
                
                // Show original order data
                if (orderState.pickupOptions.isNotEmpty()) {
                    Text("价格: ${orderState.price}")
                    Text("取货选项:")
                    orderState.pickupOptions.forEach { option ->
                        Text(option)
                    }
                } else {
                    Text("没有可用的取货选项")
                }
            }
        }
    }
}

@Composable
fun Row(content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}