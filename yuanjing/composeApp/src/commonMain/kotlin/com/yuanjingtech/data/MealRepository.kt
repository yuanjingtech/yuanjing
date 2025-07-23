package com.yuanjingtech.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Repository managing meal data and suggestions
 */
class MealRepository {
    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> = _meals.asStateFlow()

    init {
        initializeSampleMeals()
    }

    private fun initializeSampleMeals() {
        _meals.value = listOf(
            Meal(1, "红烧肉", "中餐", listOf("家常菜", "荤菜"), 800),
            Meal(2, "宫保鸡丁", "中餐", listOf("家常菜", "荤菜", "微辣"), 600),
            Meal(3, "番茄炒蛋", "中餐", listOf("家常菜", "素食"), 300),
            Meal(4, "麻婆豆腐", "中餐", listOf("家常菜", "素菜", "辣"), 400),
            Meal(5, "水煮鱼", "川菜", listOf("川菜", "辣", "荤菜"), 700),
            Meal(6, "糖醋里脊", "中餐", listOf("家常菜", "酸甜"), 650),
            Meal(7, "鸡蛋面", "面食", listOf("面食", "简单"), 480),
            Meal(8, "意大利面", "西餐", listOf("西餐", "芝士"), 520),
            Meal(9, "日式拉面", "日料", listOf("日料", "汤面"), 550),
            Meal(10, "汉堡", "西餐", listOf("快餐", "西餐"), 600),
            Meal(11, "披萨", "西餐", listOf("西餐", "芝士"), 800),
            Meal(12, "寿司", "日料", listOf("日料", "生食"), 350),
            Meal(13, "蛋炒饭", "中餐", listOf("快手", "简单"), 420),
            Meal(14, "蒸蛋", "中餐", listOf("清淡", "素食"), 180),
            Meal(15, "清蒸鲈鱼", "中餐", listOf("海鲜", "清淡"), 380),
            Meal(16, "麻辣香锅", "川菜", listOf("川菜", "辣", "自选"), 600),
            Meal(17, "担担面", "川菜", listOf("面食", "川菜", "辣"), 500),
            Meal(18, "蛋花汤", "中餐", listOf("汤品", "清淡"), 80),
            Meal(19, "串串香", "川川", listOf("小食", "辣", "火锅"), 450),
            Meal(20, "韭菜盒子", "中餐", listOf("面食", "素馅"), 380)
        )
    }

    fun addMeal(meal: Meal) {
        val currentMeals = _meals.value.toMutableList()
        val maxId = currentMeals.maxOfOrNull { it.id } ?: 0
        val newMeal = meal.copy(id = maxId + 1)
        _meals.value = currentMeals + newMeal
    }

    fun removeMeal(mealId: Int) {
        _meals.value = _meals.value.filter { it.id != mealId }
    }

    fun getRandomMeal(): Meal? {
        val mealsList = _meals.value
        return if (mealsList.isNotEmpty()) {
            mealsList.random()
        } else {
            null
        }
    }

    fun getMealsByCategory(category: String): List<Meal> {
        return _meals.value.filter { it.category == category }
    }

    fun getMealsByTag(tag: String): List<Meal> {
        return _meals.value.filter { meal -> meal.tags.contains(tag) }
    }
}