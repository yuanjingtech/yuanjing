package com.yuanjing.jintianchishenme.di

import com.yuanjing.jintianchishenme.data.MealRepository
import com.yuanjingtech.ui.jintianchishenme.MealSuggestionViewModel
import org.koin.dsl.module

/**
 * 今天吃什么功能模块的 Koin 依赖注入配置
 *
 * 包含该模块特有的依赖，如特定的 ViewModels 和服务
 */
val jintianchishenmeModule = module {
    // ViewModels - 使用 factory 确保每次获取新实例
    factory { MealSuggestionViewModel(get()) }

    // 数据层
    single<MealRepository> { MealRepository() }
    
    // TabPlugin实例
    factory<com.yuanjingtech.shared.home.tab.TabPlugin> { 
        com.yuanjing.jintianchishenme.JintianchishenmeTabPlugin() 
    }
}

/**
 * 获取今天吃什么模块的所有 Koin 模块
 */
fun getJintianchishenmeModules() = listOf(jintianchishenmeModule)
