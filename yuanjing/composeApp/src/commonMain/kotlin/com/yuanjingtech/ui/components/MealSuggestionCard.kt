package com.yuanjingtech.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yuanjingtech.data.Meal
import com.yuanjingtech.ui.MealSuggestionViewModel

@Composable
fun MealSuggestionCard(
    currentMeal: Meal?,
    suggestionMessage: String,
    onGenerateSuggestion: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
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
                MealDisplayCard(
                    meal = meal,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            Button(
                onClick = onGenerateSuggestion,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text("随机推荐一个")
            }
        }
    }
}

@Composable
fun MealCategoryQuickSuggestions(
    viewModel: MealSuggestionViewModel,
    modifier: Modifier = Modifier
) {
    val categories = listOf("中餐", "西餐", "川菜", "面食")
    
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "快速分类推荐",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        androidx.compose.foundation.layout.FlowRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                Button(
                    onClick = { viewModel.suggestByCategory(category) },
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(category)
                }
            }
        }
    }
}

@Composable
fun MealSuggestionHeader(
    mealCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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
        
        Text(
            "共收录了 $mealCount 道菜",
            style = MaterialTheme.typography.bodySmall,
            color = androidx.compose.ui.graphics.Color.Gray
        )
    }
}