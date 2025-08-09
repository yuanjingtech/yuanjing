package com.yuanjingtech.shared.developer

/**
 * 调试模块导出文件
 *
 * 提供调试功能的统一访问入口，包括：
 * - DebugModeRepository: 核心数据存储
 * - DebugModeManager: 业务逻辑管理
 * - DebugModeProvider: 便捷访问接口
 * - DebugState: 状态数据类
 */

// 重新导出主要类和接口，方便其他模块导入
typealias SharedDebugModeRepository = DebugModeRepository
typealias SharedDebugModeManager = DebugModeManager
typealias SharedDebugModeProvider = DebugModeProvider
typealias SharedDebugState = DebugState
