package com.yuanjingtech.di

import com.yuanjingtech.shared.di.getSharedModules
import com.yuanjingtech.ui.demo.DemoTabPlugin
import com.yuanjingtech.ui.main.MainTabPlugin
import com.yuanjingtech.ui.order.OrderViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

/**
 * 主应用的 Koin 模块配置
 *
 * 包含应用特定的依赖，如 ViewModels 和服务
 */
val appModule = module {
    // ViewModels - 使用 factory 确保每次获取新实例
    factoryOf(::OrderViewModel)

}

/**
 * 插件系统模块
 * 用于插件相关的依赖注入
 */
val pluginModule = module {
    // TabPlugin实例 - 绑定到接口以便自动发现
    factory<com.yuanjingtech.shared.home.tab.TabPlugin> {
        MainTabPlugin()
    }
    factory<com.yuanjingtech.shared.home.tab.TabPlugin> {
        DemoTabPlugin()
    }
    
}

/**
 * 获取所有应用模块
 */
fun getAllModules() = buildList {
    addAll(getSharedModules())
    add(appModule)
    add(pluginModule)
}


/**
 * 初始化 Koin 依赖注入框架
 */
fun initializeKoin() {
    startKoin {
        // 打印 Koin 日志（开发环境）
        printLogger()

        // 加载所有模块
        modules(getAllModules())
    }
}
