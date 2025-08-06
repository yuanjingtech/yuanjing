# 贡献指南 (CONTRIBUTE.md)

## 技术栈概览

### 核心技术
- **UI框架**: Jetpack Compose Multiplatform
- **架构模式**: MVVM (Model-View-ViewModel)
- **状态管理**: Kotlin StateFlow + Compose State
- **依赖注入**: Compose ViewModel
- **后端服务**: Ktor 3.1.3 + Netty Engine
- **编程语言**: Kotlin (通用) + Swift (iOS特定)

### 项目结构
```
composeApp/src/
├── commonMain/kotlin/com/yuanjingtech/
│   ├── ui/
│   │   ├── screens/          # Screen后缀组件 (新架构)
│   │   ├── components/       # 可复用UI组件
│   │   └── *.kt             # ViewModels
│   ├── data/                # 数据模型和仓库
│   └── App.kt               # 应用入口
├── androidMain/             # Android平台特定代码
├── iosMain/                 # iOS平台特定代码
└── desktopMain/             # 桌面平台特定代码
```

## Screen组件开发规范

### 1. Screen组件命名和结构

#### 命名规范
- 所有屏幕级组件必须以 `Screen` 后缀结尾
- 使用 PascalCase 命名，例如：`MainScreen`、`MealSuggestionScreen`
- 文件路径：`ui/screens/XxxScreen.kt`

#### 组件结构模板
```kotlin
/**
 * 屏幕组件 - [功能描述]
 * 
 * 这是一个完整封装的屏幕组件，包含：
 * - [功能1]
 * - [功能2]
 * - [功能3]
 * 
 * 特性：
 * - 开箱即用，无需额外配置
 * - 完全封装的业务逻辑
 * - 支持多平台适配
 * - 遵循Material Design 3规范
 * 
 * @param modifier 修饰符
 * @param viewModel ViewModel，支持依赖注入
 */
@Composable
fun XxxScreen(
    modifier: Modifier = Modifier,
    viewModel: XxxViewModel = viewModel { XxxViewModel() }
) {
    // 状态收集
    val state1 by viewModel.state1.collectAsState()
    val state2 by viewModel.state2.collectAsState()

    // UI实现
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // 组件内容
    }
}

/**
 * 预览组件
 */
@Composable
@Preview
fun XxxScreenPreview() {
    MaterialTheme {
        XxxScreen()
    }
}
```

### 2. Screen组件设计原则

#### 封装原则
- **完全封装**: Screen组件必须封装所有相关的ViewModel和业务逻辑
- **开箱即用**: 调用者只需要引入组件，无需额外配置
- **依赖注入**: 支持可选的ViewModel注入，便于测试和自定义

#### 职责划分
- **Screen组件**: 负责屏幕级的布局和状态管理
- **Components组件**: 可复用的UI元素
- **ViewModels**: 业务逻辑和数据管理

#### 最佳实践
1. **状态提升**: 将状态管理集中在Screen级别
2. **组件复用**: 将可复用的UI元素抽取为独立Components
3. **响应式设计**: 支持不同屏幕尺寸和方向
4. **无障碍支持**: 添加适当的语义标签
5. **性能优化**: 合理使用remember和LaunchedEffect

### 3. 当前Screen组件

#### MainScreen
- **文件**: `ui/screens/MainScreen.kt`
- **功能**: 应用主界面，集成餐食推荐和订单管理
- **特性**: 响应式布局、卡片式设计、状态展示

#### MealSuggestionScreen
- **文件**: `ui/screens/MealSuggestionScreen.kt`
- **功能**: 专门的餐食推荐界面
- **特性**: 标签页导航、历史记录、分类推荐

## 开发工作流

### 1. 添加新Screen组件
```bash
# 1. 创建Screen组件文件
touch composeApp/src/commonMain/kotlin/com/yuanjingtech/ui/screens/NewScreen.kt

# 2. 创建对应的ViewModel (如果需要)
touch composeApp/src/commonMain/kotlin/com/yuanjingtech/ui/NewViewModel.kt

# 3. 添加必要的数据模型
touch composeApp/src/commonMain/kotlin/com/yuanjingtech/data/NewModel.kt
```

### 2. 组件集成流程
1. **设计**: 确定Screen组件的功能和界面
2. **实现**: 按照模板创建Screen组件
3. **测试**: 添加预览和单元测试
4. **集成**: 在App.kt或导航中引入
5. **文档**: 更新README和CONTRIBUTE

### 3. 代码审查清单
- [ ] 组件命名符合规范 (Screen后缀)
- [ ] 包含完整的KDoc注释
- [ ] 支持依赖注入
- [ ] 添加了Preview组件
- [ ] 遵循Material Design 3
- [ ] 支持响应式布局
- [ ] 性能优化 (remember、LaunchedEffect)
- [ ] 无障碍标签

## 构建和部署

### 开发环境
```bash
# Android调试版本
./gradlew :composeApp:assembleDebug

# iOS开发版本
./gradlew :composeApp:iosArm64MainBinaries

# 桌面版本
./gradlew :composeApp:desktopRun
```

### 测试
```bash
# 运行所有测试
./gradlew test

# 运行特定平台测试
./gradlew :composeApp:testDebugUnitTest
```

## 国际化支持

### 多语言资源
- 所有用户可见文本使用 `stringResource`
- 资源文件路径: `composeApp/src/commonMain/composeResources/`
- 支持语言: 中文(简体)、英文

### 添加新文本资源
1. 在 `strings.xml` 中添加键值对
2. 在组件中使用 `stringResource(Res.string.key)`
3. 为所有支持的语言添加翻译

## 性能优化指南

### Compose最佳实践
1. **合理使用remember**: 缓存昂贵的计算结果
2. **避免重复组合**: 使用stable类型和不可变数据
3. **惰性加载**: 对长列表使用LazyColumn/LazyRow
4. **状态提升**: 将状态管理提升到合适的层级

### 内存管理
- 及时清理ViewModel中的资源
- 合理使用LaunchedEffect的清理逻辑
- 避免内存泄漏

## 错误处理和调试

### 常见问题
1. **状态丢失**: 检查State的保存和恢复
2. **重组问题**: 验证数据类的稳定性
3. **导航问题**: 确保Screen组件的生命周期管理

### 调试工具
- Compose Inspector
- Layout Inspector
- 性能分析器

## 贡献流程

1. **Fork** 仓库
2. **创建** 功能分支 (`git checkout -b feature/new-screen`)
3. **提交** 更改 (`git commit -am 'Add new screen'`)
4. **推送** 分支 (`git push origin feature/new-screen`)
5. **创建** Pull Request

### 提交信息规范
```
[类型]: 简短描述

详细描述 (可选)

- 变更1
- 变更2
```

类型包括: feat, fix, docs, style, refactor, test, chore

---

最后更新: 2025年8月6日
维护者: Yuanjing Tech Team
