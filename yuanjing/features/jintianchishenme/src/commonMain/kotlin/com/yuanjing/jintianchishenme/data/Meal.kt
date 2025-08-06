package com.yuanjing.jintianchishenme.data


/**
 * Data class representing a meal/dish option
 * @param name Name of the dish (e.g., "红烧肉", "宫保鸡丁")
 * @param category Category of the meal (e.g., "中餐", "西餐", "日料")
 * @param tags Optional tags like "辣", "清淡", "素食"
 * @param calories Estimated calories for the dish
 */
data class Meal(
    val id: Int,
    val name: String,
    val category: String = "中餐",
    val tags: List<String> = emptyList(),
    val calories: Int = 0
)