# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Kotlin Multiplatform project using Compose Multiplatform with support for Android, iOS, Web (WasmJS), and Desktop (JVM). The project follows a modular architecture with
shared code and platform-specific implementations.

## Architecture

### Modules Structure
- **composeApp/**: Main application module with Compose UI targeting all platforms
- **server/**: Ktor-based backend server application
- **shared/**: Shared business logic and utilities across all targets
- **iosApp/**: iOS-specific Swift/SwiftUI code and configuration

### Technology Stack
- **UI Framework**: Compose Multiplatform 1.8.0
- **Architecture**: MVVM pattern with Kotlin Flows
- **Backend**: Ktor 3.1.3 server
- **Backend Engine**: Netty
- **Languages**: Kotlin (common), Swift (iOS-specific)

### Key Components
- `composeApp/src/commonMain/` - Shared Compose UI components
- `composeApp/src/[platform]Main/` - Platform-specific implementations
- `data/` - Domain models (e.g., `OrderUiState`)
- `ui/` - ViewModels (e.g., `OrderViewModel`)
- `server/src/main/kotlin/` - Backend server implementation

## Build Commands

### Android
  ```bash
  ./gradlew :composeApp:assembleDebug  # Build debug APK
  ./gradlew :composeApp:installDebug   # Install on connected device

  iOS

  ./gradlew :composeApp:iosArm64MainBinaries
  # Then open iosApp/iosApp.xcodeproj in Xcode for iOS-specific development

  Desktop

  ./gradlew :composeApp:desktopRun      # Run desktop app
  ./gradlew :composeApp:packageDmg     # Package for macOS
  ./gradlew :composeApp:packageMsi     # Package for Windows
  ./gradlew :composeApp:packageDeb     # Package for Linux

  Web (WasmJS)

  ./gradlew :composeApp:wasmJsBrowserDevelopmentRun  # Run web app locally
  ./gradlew :composeApp:wasmJsBrowserDistribution    # Build for production

  Server

  ./gradlew :server:run                  # Run Ktor server
  ./gradlew :server:test                 # Run server tests

  All Platforms

  ./gradlew build                        # Build all targets
  ./gradlew test                         # Run all tests

  Development Setup

  Requirements

  - Java 21 (configured via mise.toml)
  - Android SDK via Android Studio
  - Xcode for iOS development
  - Git

  Environment Configuration

  - local.properties: Android SDK path (auto-generated)
  - gradle.properties: Memory and performance settings

  Important File Locations

  - Main Application Entry: composeApp/src/commonMain/kotlin/com/yuanjingtech/App.kt:42
  - UI State Model: composeApp/src/commonMain/kotlin/com y/m/y/data/OrderUiState.kt:7
  - ViewModel: composeApp/src/commonMain/kotlin/com/y/m/y/ui/OrderViewModel.kt:5
  - Server Entry: server/src/main/kotlin/com/yuanjingtech/Application.kt

  Running Development Server

  The project includes hot-reload capabilities configured via:
  - composeHotReload plugin for development
  - Ktor development mode enabled by default

# 产品代理 (Product Agent)

This file defines the product agent configuration for the 今天吃什么 (What to eat today) Kotlin Multiplatform project.

## 产品代理概述 (Product Agent Overview)

### 核心职责 (Core Responsibilities)
- **产品需求分析**: 分析用户需求，制定产品功能规划
- **功能模块管理**: 管理插件系统和功能模块的生命周期
- **用户体验优化**: 确保跨平台一致性和最佳用户体验
- **产品质量保证**: 确保产品符合技术规范和用户期望
- **国际化支持**: 优先简体中文，其次英文的国际化策略

### 技术架构支持 (Technical Architecture Support)
- **MVVM架构**: 遵循项目的MVVM模式和最佳实践
- **插件系统**: 支持动态标签页插件机制和模块化开发
- **跨平台支持**: Android、iOS、Web (WasmJS)、Desktop (JVM)
- **依赖注入**: 基于Koin 4.0.0的依赖管理
- **状态管理**: StateFlow + Compose State响应式编程

## 产品功能管理 (Product Feature Management)

### 现有功能模块 (Existing Feature Modules)

#### 1. 核心推荐功能 (Core Recommendation Features)
- **智能推荐**: 基于算法的随机餐食推荐系统
- **分类推荐**: 中餐、西餐、川菜、面食等分类推荐
- **推荐历史**: 历史记录管理和查看功能
- **数据统计**: 实时菜品数量和统计信息

#### 2. 用户界面功能 (User Interface Features)
- **标签页导航**: 推荐、分类、历史三个核心标签页
- **Material Design 3**: 现代化设计语言
- **响应式设计**: 适配不同屏幕尺寸
- **动画效果**: 流畅的交互体验

### 功能扩展策略 (Feature Extension Strategy)

#### 插件开发规范 (Plugin Development Standards)
```kotlin
// 新功能插件实现模板
interface ProductFeaturePlugin : TabPlugin {
    // 产品功能特定接口
    val featureCategory: FeatureCategory
    val userSegment: UserSegment
    val priority: Int
}

enum class FeatureCategory {
    CORE_RECOMMENDATION,    // 核心推荐
    USER_EXPERIENCE,        // 用户体验
    DATA_ANALYSIS,          // 数据分析
    SOCIAL_FEATURES,        // 社交功能
    PERSONALIZATION        // 个性化
}
```

#### Screen组件规范 (Screen Component Standards)
- 所有新功能必须实现为Screen后缀组件
- 完全封装ViewModel和业务逻辑
- 支持依赖注入用于测试
- 遵循国际化优先级: 简体中文 > 英文

## 产品质量保证 (Product Quality Assurance)

### 代码质量标准 (Code Quality Standards)
- **重构优先**: 所有修改都使用重构方式
- **版本控制**: 使用git命令和语义化commit message
- **文件移动**: 使用git命令保持历史记录
- **持续优化**: 保持最佳实践更新同步

### 文档同步策略 (Documentation Sync Strategy)
- **README同步**: 保持产品功能描述与实际实现同步
- **CONTRIBUTE同步**: 保持技术栈信息与项目配置同步
- **metadata规范**: 遵循文档元数据中的specs规范

### 国际化管理 (Internationalization Management)
```kotlin
// 国际化优先级配置
object ProductI18nConfig {
    val primaryLanguage = "zh-CN"     // 简体中文 (优先)
    val secondaryLanguage = "en"      // 英文 (其次)
    
    // 产品功能文案管理
    val featureStrings = mapOf(
        "meal_recommendation" to mapOf(
            "zh-CN" to "餐食推荐",
            "en" to "Meal Recommendation"
        ),
        "category_filter" to mapOf(
            "zh-CN" to "分类筛选", 
            "en" to "Category Filter"
        )
        // 更多功能文案...
    )
}
```

## 产品路线图 (Product Roadmap)

### 短期目标 (Short-term Goals)
1. **功能完善**: 优化现有推荐算法和用户体验
2. **性能优化**: 提升跨平台性能和响应速度
3. **UI/UX改进**: 完善Material Design 3实现

### 中期目标 (Medium-term Goals)
1. **个性化推荐**: 基于用户偏好的智能推荐
2. **社交功能**: 分享推荐结果和用户互动
3. **数据分析**: 用户行为分析和推荐效果统计

### 长期目标 (Long-term Goals)
1. **AI集成**: 集成机器学习推荐算法
2. **生态扩展**: 与外部餐饮服务集成
3. **企业级功能**: 团队餐食推荐和管理功能

## 开发指导原则 (Development Guidelines)

### 产品决策原则 (Product Decision Principles)
1. **用户体验优先**: 所有功能以用户体验为核心
2. **简洁易用**: 保持界面简洁，操作直观
3. **跨平台一致性**: 确保所有平台功能和体验一致
4. **性能第一**: 优化加载速度和响应时间
5. **可扩展性**: 支持未来功能扩展和插件集成

### 技术实现原则 (Technical Implementation Principles)
1. **模块化设计**: 功能模块独立，便于维护和测试
2. **依赖注入**: 使用Koin进行依赖管理
3. **响应式编程**: 使用StateFlow进行状态管理
4. **测试驱动**: 编写单元测试和集成测试
5. **文档同步**: 保持代码和文档的同步更新

### 产品迭代流程 (Product Iteration Process)
1. **需求分析** → 2. **技术设计** → 3. **开发实现** → 4. **测试验证** → 5. **文档更新** → 6. **发布部署**

---

## 联系方式 (Contact Information)
- **项目仓库**: [项目链接]
- **产品反馈**: [反馈渠道]
- **技术支持**: [支持渠道]

---

*最后更新: 2025-08-28*
*遵循语义化版本控制和最佳实践*
