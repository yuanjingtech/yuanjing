package com.yuanjingtech.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuanjingtech.data.Meal
import com.yuanjingtech.data.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing meal suggestions and interactions
 */
class MealSuggestionViewModel : ViewModel() {
    private val repository = MealRepository()

    private val _currentMeal = MutableStateFlow<Meal?>(null)
    val currentMeal: StateFlow<Meal?> = _currentMeal.asStateFlow()

    private val _suggestionMessage = MutableStateFlow("今天你想吃点什么呢？")
    val suggestionMessage: StateFlow<String> = _suggestionMessage.asStateFlow()

    private val _mealCount = MutableStateFlow(0)
    val mealCount: StateFlow<Int> = _mealCount.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _mealCount.value = repository.meals.value.size
        }
    }

    fun generateMealSuggestion() {
        viewModelScope.launch {
            val randomMeal = repository.getRandomMeal()
            if (randomMeal != null) {
                _currentMeal.value = randomMeal
                _suggestionMessage.value = generateSuggestionMessage(randomMeal)
            } else {
                _currentMeal.value = null
                _suggestionMessage.value = "抱歉，暂时没有可推荐的菜品！"
            }
        }
    }

    private fun generateSuggestionMessage(meal: Meal): String {
        val tags = if (meal.tags.isNotEmpty()) meal.tags.joinToString("、") else ""
        return buildString {
            append("建议你今天吃：")
            append(meal.name)
            if (tags.isNotEmpty()) {
                append(" ($tags)")
            }
            if (meal.calories > 0) {
                append(" ${meal.calories}卡")
            }
        }
    }

    fun suggestByCategory(category: String) {
        viewModelScope.launch {
            val meals = repository.getMealsByCategory(category)
            if (meals.isNotEmpty()) {
                val randomMeal = meals.random()
                _currentMeal.value = randomMeal
                _suggestionMessage.value = "${category}推荐：${randomMeal.name}"
            } else {
                _currentMeal.value = null
                _suggestionMessage.value = "暂无${category}类菜品推荐"
            }
        }
    }

    fun suggestByTag(tag: String) {
        viewModelScope.launch {
            val meals = repository.getMealsByTag(tag)
            if (meals.isNotEmpty()) {
                val randomMeal = meals.random()
                _currentMeal.value = randomMeal
                _suggestionMessage.value = "${tag}推荐：${randomMeal.name}"
            } else {
                _currentMeal.value = null
                _suggestionMessage.value = "暂无${tag}类菜品推荐"
            }
        }
    }

    fun resetSuggestion() {
        _currentMeal.value = null
        _suggestionMessage.value = "今天你想吃点什么呢？"
    }
}