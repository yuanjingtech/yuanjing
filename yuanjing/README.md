# 今天吃什么 - 智能餐食推荐应用

这是一个基于 Kotlin Multiplatform 的跨平台餐食推荐应用，支持 Android、iOS、Web、Desktop 和 Server。

## 🍽️ 产品功能

### 核心功能
- **智能推荐**: 基于算法的随机餐食推荐系统
- **分类推荐**: 支持中餐、西餐、川菜、面食等分类快速推荐
- **推荐历史**: 自动记录和管理推荐历史，支持历史查看
- **数据统计**: 实时显示收录菜品数量和统计信息
- **响应式设计**: 适配不同屏幕尺寸和设备类型

### 用户界面
- **标签页导航**: 推荐、分类、历史三个独立标签页
- **Material Design 3**: 现代化的设计语言和视觉体验
- **卡片式布局**: 清晰的信息层次和视觉分组
- **动画效果**: 流畅的页面切换和交互动画

### 技术特性
- **跨平台支持**: 一套代码，多平台部署
- **MVVM架构**: 清晰的代码结构和责任分离
- **依赖注入**: 基于 Koin 4.0.0 的现代化依赖管理
- **状态管理**: 基于 StateFlow 的响应式状态管理
- **模块化设计**: Screen组件化架构，开箱即用
- **插件系统**: 动态标签页插件机制，支持功��模块热插拔

## 🏗️ 项目架构

### 目录结构
- `/composeApp` - 共享的 Compose Multiplatform 应用代码
  - `commonMain` - 所有平台共享的代码
    - `ui/screens/` - Screen后缀组件 (完整封装的屏幕组件)
    - `ui/components/` - 可复用的UI组件
    - `ui/` - ViewModels和UI逻辑
    - `data/` - 数据模型和仓库层
  - 平台特定文件夹 - 各平台特有的实现代码

- `/iosApp` - iOS应用入口点和SwiftUI代码
- `/server` - Ktor服务器应用 
- `/shared` - 跨项目共享的代码库

### Screen组件架构
本项目采用Screen后缀组件架构，提供完全封装的屏幕级组件：

- `MainScreen` - 应用主界面，集成所有核心功能
- `MealSuggestionScreen` - 专门的餐食推荐界面

每个Screen组件都：
- 完全封装ViewModel和业务逻辑
- 支持依赖注入用于测试
- 开箱即用，无需额外配置
- 遵循Material Design 3规范

## 🚀 快速开始

### 构建要求
- JDK 11+
- Android Studio 或 IntelliJ IDEA
- Kotlin Multiplatform Mobile plugin

### 运行应用

#### Android
```bash
./gradlew :composeApp:assembleDebug
./gradlew :composeApp:installDebug
```

#### iOS
```bash
./gradlew :composeApp:iosArm64MainBinaries
# 然后在 Xcode 中打开 iosApp/iosApp.xcodeproj
```

#### 桌面版
```bash
./gradlew :composeApp:desktopRun
```

#### Web版
```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

#### 服务器
```bash
./gradlew :server:run
```

## 🛠️ 开发指南

### 技术栈
- **UI框架**: Jetpack Compose Multiplatform
- **架构模式**: MVVM + Kotlin StateFlow
- **后端**: Ktor 3.1.3 + Netty Engine
- **语言**: Kotlin (通用) + Swift (iOS特定)

### 贡献指南
详细的开发规范和贡献指南请参考 [CONTRIBUTE.md](./CONTRIBUTE.md)，包含：
- Screen组件开发规范
- 代码审查清单
- 性能优化指南
- 国际化支持

### 添加新功能
1. 创建Screen组件 (带Screen后缀)
2. 实现对应的ViewModel
3. 添加必要的数据模型
4. 编写测试和预览
5. 更新文档

## 📱 产品截图

*待添加应用截图*

## 🌍 国际化
- 支持中文简体
- 支持英文
- 易于扩展其他语言

## 📄 许可证

*待添加许可证信息*

## 🤝 贡献

欢迎贡献代码！请先阅读 [贡献指南](./CONTRIBUTE.md)。

## 📞 联系方式

如有问题或建议，请通过以下方式联系：
- 提交 Issue
- 发起 Pull Request

---

了解更多关于：
- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform)
- [Kotlin/Wasm](https://kotl.in/wasm/)
