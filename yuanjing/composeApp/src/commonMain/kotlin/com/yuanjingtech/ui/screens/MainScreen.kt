package com.yuanjingtech.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yuanjingtech.ui.order.OrderViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * 主屏幕组件 - 完整的应用主界面
 *
 * 这是一个完整封装的屏幕组件，包含：
 * - 餐食推荐系统
 * - 订单管理功能
 * - 应用设置和状态显示
 * - 响应式布局适配
 *
 * 特性：
 * - 开箱即用，无需额外配置
 * - 完全封装的业务逻辑
 * - 支持多平台适配
 * - 遵循Material Design 3规范
 *
 * @param modifier 修饰符
 * @param orderViewModel 订单管理ViewModel，可选注入
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    orderViewModel: OrderViewModel = viewModel { OrderViewModel() }
) {
    // 状态收集
    val orderState by orderViewModel.uiState.collectAsState()

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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            // 订单状态卡片（如果有订单数据）
            if (orderState.pickupOptions.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "订单信息",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "价格: ${orderState.price}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "取货选项:",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        orderState.pickupOptions.forEach { option ->
                            Text(
                                text = "• $option",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }


        }
    }
}

/**
 * 主屏幕预览组件
 */
@Composable
@Preview
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen()
    }
}
