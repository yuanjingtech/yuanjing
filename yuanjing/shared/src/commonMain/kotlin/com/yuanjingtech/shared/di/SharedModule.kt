package com.yuanjingtech.shared.di

import org.koin.dsl.module

/**
 * Shared 模块的 Koin 依赖注入配置
 *
 * 包含应用的核心依赖和共享服务
 * MealRepository 和相关 ViewModels 已移至各自的功能模块
 */
val sharedModule = module {
    // 这里可以添加真正共享的依赖，如网络客户端、数据库等
    // 目前为空，因为特定的依赖已移至各自模块
}

/**
 * 获取所有共享模块
 */
fun getSharedModules() = listOf(sharedModule)
