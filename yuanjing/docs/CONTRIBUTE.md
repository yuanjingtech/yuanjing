# 贡献指南 (CONTRIBUTE.md)

## 技术栈概览

### 核心技术
- **UI框架**: Jetpack Compose Multiplatform
- **架构模式**: MVVM (Model-View-ViewModel)
- **依赖注入**: Koin 4.0.0 (多平台依赖注入框架)
- **状态管理**: Kotlin StateFlow + Compose State
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
│   ├── plugin/
│   │   └── builtin/         # 内置插件实现
│   ├── data/                # 数据模型和仓库
│   └── App.kt               # 应用入口
├── androidMain/             # Android平台特定代码
├── iosMain/                 # iOS平台特定代码
└── desktopMain/             # 桌面平台特定代码

shared/src/
├── commonMain/kotlin/com/yuanjingtech/shared/
│   ├── home/tab/           # 标签页插件系统核心 (新架构)
│   │   ├── TabPlugin.kt           # 插件接口定义
│   │   ├── TabPluginManager.kt    # 插件管理器
│   │   ├── PluginDiscovery.kt     # 插���������发现机制
│   │   └── PluginConfiguration.kt # 插件配置
│   └── ...                 # 其他共享代码

features/
├── jintianchishenme/src/   # 今天吃什么功能模块
│   └── commonMain/kotlin/com/yuanjing/jintianchishenme/
│       ├── ui/                    # 功能UI组件
│       ├── JintianchiShenmeTabPlugin.kt      # 插件实现
│       └─�� JintianchiShenmePluginRegistrar.kt # 插件注册器
└── ...                     # 其他功能模块
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
 * - 完全封装的业务���辑
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
1. **状态提升**: 将��态管理集中在Screen级别
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

### 2. 组件��成流��
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
# Android调试��本
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

### ��见问题
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
4. **推���** 分支 (`git push origin feature/new-screen`)
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

## 标签页插件系统开发指南

### 插件架构概述

我们的应用采用了创新的标签页插件架构，将核心插件系统移至 `shared` 模块，实现了：

- **模块化**: 每个功能作为独立插件
- **可扩展**: 轻松添加新标签页功能
- **解耦合**: 功能模块间完全独立
- **响应式**: 动态插件发现和注册

### 插件系统核心组件

#### 1. TabPlugin 接口 (`shared/home/tab/TabPlugin.kt`)
定义插件的基本规范：
```kotlin
interface TabPlugin {
    val id: String           // 唯一标识符
    val title: String        // 标签页显示名称
    val version: String      // 插件版本
    val priority: Int        // 显示优先级
    val isEnabled: Boolean   // 是否启用
    val description: String  // 描述信息
    
    @Composable
    fun Content(modifier: Modifier = Modifier)  // UI内容
    
    fun initialize() {}      // 初始化
    fun cleanup() {}         // 清理资源
}
```

#### 2. TabPluginManager (`shared/home/tab/TabPluginManager.kt`)
管理所有插件的生命周期：
- 插件注册/注销
- 状态响应式管理
- 优先级排序
- 线程安全操作

#### 3. PluginDiscovery (`shared/home/tab/PluginDiscovery.kt`)
提供插件发现机制：
- 注册器管理
- 自动插件发现
- 生命周期管理

#### 4. PluginConfiguration (`shared/home/tab/PluginConfiguration.kt`)
编译时插件配置：
- 避免运行时反射
- ���态插件注册
- 统计信息收集

### 创建新的标签页插件

#### 步骤 1: 创建功能模块
```bash
# ��� features/ 目录下创建新功能模块
mkdir features/yourfeature
cd features/yourfeature
```

#### 步骤 2: 实现 TabPlugin 接口
```kotlin
// features/yourfeature/src/commonMain/kotlin/.../YourFeatureTabPlugin.kt
class YourFeatureTabPlugin : TabPlugin {
    override val id = "your_feature_tab"
    override val title = "你的功能"
    override val version = "1.0.0"
    override val priority = 50
    
    @Composable
    override fun Content(modifier: Modifier) {
        YourFeatureScreen(modifier = modifier)
    }
    
    override fun initialize() {
        println("$title 插件初始化")
    }
    
    override fun cleanup() {
        println("$title 插件清理")
    }
}
```

#### 步骤 3: 创建插件注册器
```kotlin
// features/yourfeature/src/commonMain/kotlin/.../YourFeaturePluginRegistrar.kt
class YourFeaturePluginRegistrar : PluginRegistrar {
    private val plugin = YourFeatureTabPlugin()
    
    override fun registerPlugins() {
        TabPluginManager.registerPlugin(plugin)
    }
    
    override fun unregisterPlugins() {
        TabPluginManager.unregisterPlugin(plugin.id)
    }
}
```

#### 步骤 4: 更新插件配置
在 `shared/home/tab/PluginConfiguration.kt` 中添加：
```kotlin
// 在 getAllRegistrars() 方法中添加
try {
    registrars.add(createYourFeaturePluginRegistrar())
} catch (e: Exception) {
    println("YourFeature plugin not available: ${e.message}")
}

// 添加创建方法
private fun createYourFeaturePluginRegistrar(): PluginRegistrar {
    val className = "com.your.package.YourFeaturePluginRegistrar"
    return Class.forName(className).getDeclaredConstructor().newInstance() as PluginRegistrar
}
```

#### 步骤 5: 配置构建依赖
```kotlin
// features/yourfeature/build.gradle.kts
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.runtime)
                implementation(compose.material3)
                implementation(compose.ui)
            }
        }
    }
}

// composeApp/build.gradle.kts 中添加
dependencies {
    implementation(project(":features:yourfeature"))
}
```

### 插件最佳实践

#### 1. 性能优化
- 使用 `remember` 缓存昂贵计算
- 实现懒加载避免启动时间过长
- 及时清理资源防止内存泄漏

#### 2. 状态管理
- 在插件级别管理 ViewModel
- 使用 StateFlow 进行响应式状态更新
- 支持状态持久化

#### 3. 错误处理
- 实现优雅的错误降级
- 提供用户友好的错误信息
- 记录详细的错误日志

#### 4. 用户体验
- 遵循 Material Design 3 规范
- 支持响应式布局
- 提供加载状态指示

### 插件调试和测试

#### 1. 开发调试
```bash
# 检查插件注册状态
./gradlew :composeApp:run --info | grep "Plugin"

# 验证构建配置
./gradlew :features:yourfeature:compileKotlinMetadata
```

#### 2. 插件状态监控
```kotlin
// 获取插件统计信息
val stats = PluginConfiguration.getPluginStats()
println("已注册插件: ${stats.enabledPluginIds}")
```

#### 3. 单元测试
```kotlin
@Test
fun testPluginRegistration() {
    val plugin = YourFeatureTabPlugin()
    TabPluginManager.registerPlugin(plugin)
    
    assertTrue(TabPluginManager.isPluginRegistered(plugin.id))
    assertEquals("你的功能", plugin.title)
}
```

### 已实现的插件示例

#### 1. MainTabPlugin (内置)
- **功能**: 主要应用功能
- **位置**: `composeApp/plugin/builtin/MainTabPlugin.kt`
- **优先级**: 1 (最高)

#### 2. DemoTabPlugin (内置)  
- **功能**: 演示和调试信息
- **位置**: `composeApp/plugin/builtin/DemoTabPlugin.kt`
- **优先级**: 99 (最低)

#### 3. JintianchiShenmeTabPlugin (功能模块)
- **功能**: 今天吃什么推荐系统
- **位置**: `features/jintianchishenme/JintianchiShenmeTabPlugin.kt`
- **优先级**: 10 (高优先级)

### 插件系统架构优势

1. **模块化设计**: 每个功能独立开发和维护
2. **动态扩展**: 可在运行时添加/移除功能
3. **解耦架构**: 功能模块间无直接依赖
4. **响应式UI**: 插件变化自动更新界面
5. **编译时优化**: 避免运行时反射开销
6. **跨平台支持**: 统一的插件接口适用所有平台

## Koin 依赖注入开发指南

### Koin 架构概述

我们采用 Koin 4.0.0 作为多平台依赖注入框架，实现了：

- **多平台支持**: 统一的依赖注入在所有平台上工作
- **模块化配置**: 每个模块都有独立的依赖配置
- **类型安全**: 编译时类型检查，避免运行时错误
- **轻量级**: 相比其他 DI 框架更轻量，启动更快

### Koin 模块结构

#### 1. Shared 模块 (`shared/di/SharedModule.kt`)
包含跨模块共享的依赖，使用 `api` 暴露 Koin 依赖：
```kotlin
// shared/build.gradle.kts
kotlin {
    sourceSets {
        commonMain.dependencies {
            // 使用 api 暴露 Koin 给所有依赖模块
            api(libs.koin)
        }
    }
}

// shared/di/SharedModule.kt
val sharedModule = module {
    // 数据层依赖
    singleOf(::MealRepository)
    
    // ViewModels
    factory { MealSuggestionViewModel(get()) }
    factory { OrderViewModel() }
}
```

#### 2. 主应用模块 (`composeApp/di/AppModule.kt`)
依赖��� shared 模块，自动获得 Koin 支持：
```kotlin
// composeApp/build.gradle.kts
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared)
            // Koin 已通过 shared 模块的 api 依赖自动提供
        }
    }
}

// composeApp/di/AppModule.kt
val appModule = module {
    // ViewModels - 使用 factory 确保每次获取新实例
    factoryOf(::OrderViewModel)
    
    // 数据层
    single<MealRepository> { MealRepository() }
}
```

#### 3. 功能模块 (`features/*/di/*Module.kt`)
每个功能模块依赖 shared 模块，自动获得 Koin 支持：
```kotlin
// features/yourfeature/build.gradle.kts
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared"))
            // Koin 已通过 shared 模块的 api 依赖自动提供
        }
    }
}

// features/yourfeature/di/YourFeatureModule.kt
val yourFeatureModule = module {
    // 模块特定的 ViewModels
    factory { YourFeatureViewModel(get()) }
    
    // 模块特定的服务
    single<YourFeatureService> { YourFeatureServiceImpl() }
}
```

### Koin 最佳实践

#### 1. 依赖暴露策略 (重要)
- **shared 模块**: 使用 `api(libs.koin)` 暴露 Koin 给所有依赖模块
- **应用模块**: 使用 `implementation(projects.shared)` 自动获得 Koin 支持
- **功能模块**: 使用 `implementation(project(":shared"))` 自动获得 Koin 支持

```kotlin
// ✅ 正确做法 - shared 模块
kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.koin)  // 暴露给依赖模块
        }
    }
}

// ✅ 正确做法 - 其他模块
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared"))
            // Koin 自动可用，无需重复声明
        }
    }
}

// ❌ 避免重复声明
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared"))
            implementation(libs.koin)  // 不需要，已通过 shared 提供
        }
    }
}
```

#### 2. 依赖生命周期管理
- **single**: 单例，整个应用生命周期内只有一个实例
- **factory**: 工厂模式，每次获取都创建新实例
- **scoped**: 作用域，在特定范围内单例

```kotlin
val myModule = module {
    // 数据库、网络客户端等使用 single
    single<Database> { DatabaseImpl() }
    
    // ViewModels 通常使用 factory
    factory { MyViewModel(get()) }
    
    // 有状态的服务可以使用 scoped
    scoped<UserSession> { UserSessionImpl() }
}
```

#### 3. 接口和实现分离
```kotlin
val myModule = module {
    // 绑定接口到具体实现
    single<MyRepository> { MyRepositoryImpl(get()) }
    single<MyService> { MyServiceImpl() }
    
    // 或者使用 bind 语法
    singleOf(::MyRepositoryImpl) bind MyRepository::class
}
```

#### 4. 模块化依赖管理
```kotlin
// 每个功能模块都应该有独立的 Koin 模块
val featureModule = module {
    // 功能特定的依赖
    factory { FeatureViewModel(get()) }
    single<FeatureRepository> { FeatureRepositoryImpl() }
}

// 在主应用中��态加载功能模块
fun getAllModules() = buildList {
    add(sharedModule)
    add(appModule)
    
    // 动态加载功能模块
    try {
        addAll(loadFeatureModules())
    } catch (e: Exception) {
        println("Some feature modules not available: ${e.message}")
    }
}
```

#### 5. 条件依赖注册
```kotlin
val platformModule = module {
    // 平台特定的依赖
    single<PlatformService> {
        when (Platform.current) {
            Platform.Android -> AndroidPlatformService()
            Platform.iOS -> iOSPlatformService()
            else -> DefaultPlatformService()
        }
    }
}
```
