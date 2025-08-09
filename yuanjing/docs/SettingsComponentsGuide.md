# 设置组件使用指南

## 📦 组件架构概览

重构后的设置界面采用模块化组件架构，将原本的大型内联组件分离为独立、可复用的组件文件。

## 🗂️ 文件结构

```
/ui/setting/
├── SettingsScreen.kt              # 主设置界面容器
├── SettingsViewModel.kt           # 状态管理和业务逻辑
└── components/                    # 独立组件目录
    ├── DeveloperOptionsCard.kt    # 开发者选项卡片
    ├── AppearanceSettingsCard.kt  # 外观设置卡片
    └── OtherSettingsCard.kt       # 其他设置卡片
```

## 🧩 组件详解

### 1. DeveloperOptionsCard

**功能**: 开发者选项和调试模式控制

**参数**:
```kotlin
@Composable
fun DeveloperOptionsCard(
    isDeveloperOptionsEnabled: Boolean,
    isDebugModeEnabled: Boolean,
    onToggleDeveloperOptions: () -> Unit,
    onToggleDebugMode: () -> Unit,
    modifier: Modifier = Modifier
)
```

**特性**:
- 条件显示调试模式开关
- 视觉反馈（启用时显示错误容器样式）
- 完整的KDoc文档

### 2. AppearanceSettingsCard

**功能**: 完整的外观自定义功能

**参数**:
```kotlin
@Composable
fun AppearanceSettingsCard(
    currentTheme: AppTheme,
    currentLanguage: AppLanguage,
    currentFontSize: FontSize,
    animationsEnabled: Boolean,
    dynamicColorsEnabled: Boolean,
    onThemeSelected: (AppTheme) -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit,
    onFontSizeSelected: (FontSize) -> Unit,
    onAnimationsToggled: () -> Unit,
    onDynamicColorsToggled: () -> Unit,
    modifier: Modifier = Modifier
)
```

**内部子组件**:
- `CardHeader` - 可复用的卡片标题
- `ThemeSelectionSection` - 主题选择区域
- `LanguageSelectionSection` - 语言选择区域
- `FontSizeSelectionSection` - 字体大小选择
- `EffectsSection` - 显示效果设置
- `SelectionRow` - 通用单选按钮行
- `SwitchRow` - 通用开关行

### 3. OtherSettingsCard

**功能**: 其他设置和重置功能

**参数**:
```kotlin
@Composable
fun OtherSettingsCard(
    onResetSettings: () -> Unit,
    modifier: Modifier = Modifier
)
```

**特性**:
- 危险操作的错误色彩警示
- 清晰的操作提示信息

## 🔧 使用示例

### 在SettingsScreen中的使用

```kotlin
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel { SettingsViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column {
        // 开发者选项卡片
        DeveloperOptionsCard(
            isDeveloperOptionsEnabled = uiState.isDeveloperOptionsEnabled,
            isDebugModeEnabled = uiState.isDebugModeEnabled,
            onToggleDeveloperOptions = viewModel::toggleDeveloperOptions,
            onToggleDebugMode = viewModel::toggleDebugMode
        )
        
        // 外观设置卡片
        AppearanceSettingsCard(
            currentTheme = uiState.theme,
            currentLanguage = uiState.language,
            currentFontSize = uiState.fontSize,
            animationsEnabled = uiState.animationsEnabled,
            dynamicColorsEnabled = uiState.dynamicColorsEnabled,
            onThemeSelected = viewModel::setTheme,
            onLanguageSelected = viewModel::setLanguage,
            onFontSizeSelected = viewModel::setFontSize,
            onAnimationsToggled = viewModel::toggleAnimations,
            onDynamicColorsToggled = viewModel::toggleDynamicColors
        )
        
        // 其他设置卡片
        OtherSettingsCard(
            onResetSettings = viewModel::resetToDefaults
        )
    }
}
```

## 🎯 设计原则

### 1. 单一职责原则
每个组件专注于特定的功能领域：
- `DeveloperOptionsCard` → 开发者相关设置
- `AppearanceSettingsCard` → 外观相关设置
- `OtherSettingsCard` → 通用操作

### 2. 组件组合
通过组合多个小组件构建复杂功能：
- `AppearanceSettingsCard` 内部组合了多个私有子组件
- 每个子组件可以独立测试和维护

### 3. 可复用性
设计可复用的通用组件：
- `SelectionRow` - 适用于任何单选场景
- `SwitchRow` - 适用于任何开关场景
- `CardHeader` - 统一的卡片标题样式

## 🔄 状态管理

### 状态流向
```
ViewModel → UiState → Component → User Action → ViewModel
```

### 状态提升
- 所有状态由 `SettingsViewModel` 统一管理
- 组件接收状态和回调函数
- 无本地状态，确保数据一致性

## 🧪 测试策略

### 单元测试
每个组件都可以独立测试：
```kotlin
@Test
fun developerOptionsCard_showsDebugModeWhenEnabled() {
    // 测试开发者选项启用时显示调试模式开关
}

@Test
fun appearanceSettingsCard_updatesThemeSelection() {
    // 测试主题选择功能
}
```

### 集成测试
测试组件间的协作：
```kotlin
@Test
fun settingsScreen_integratesAllComponents() {
    // 测试主界面集成所有组件
}
```

## 🔧 扩展指南

### 添加新的设置卡片

1. **创建组件文件**:
   ```kotlin
   // NewSettingsCard.kt
   @Composable
   fun NewSettingsCard(
       // 参数定义
       modifier: Modifier = Modifier
   ) {
       // 组件实现
   }
   ```

2. **更新SettingsScreen**:
   ```kotlin
   // 在SettingsScreen中添加新组件
   NewSettingsCard(
       // 传递参数
   )
   ```

3. **更新ViewModel**:
   ```kotlin
   // 添加相关状态和方法
   ```

### 添加新的设置选项

1. **扩展现有组件**: 在相应的组件中添加新选项
2. **更新状态**: 在 `SettingsUiState` 中添加新状态
3. **更新业务逻辑**: 在 `SettingsViewModel` 中添加处理方法

## 📚 最佳实践

### 组件设计
- ✅ 保持组件的纯函数特性
- ✅ 使用 `@Composable` 注解
- ✅ 提供默认的 `modifier` 参数
- ✅ 添加完整的 KDoc 文档

### 状态管理
- ✅ 状态提升到合适的层级
- ✅ 使用不可变的数据类
- ✅ 避免组件内部状态
- ✅ 使用回调函数处理事件

### 代码组织
- ✅ 按功能组织组件文件
- ✅ 使用清晰的命名约定
- ✅ 保持文件大小适中
- ✅ 合理使用私有子组件

## 🎉 重构成果

### 改进前后对比

**重构前**:
- 单一大文件包含所有组件
- 内联组件定义难以复用
- 测试困难
- 维护成本高

**重构后**:
- 模块化的组件架构
- 可复用的独立组件
- 易于测试和维护
- 清晰的职责分离

### 量化指标
- **文件数量**: 1 → 4 (主界面 + 3个组件)
- **代码复用性**: 提升显著 (SelectionRow, SwitchRow等)
- **可测试性**: 大幅改善
- **维护性**: 显著提升

这套重构后的组件架构为设置界面提供了更好的代码组织、更高的可维护性和更强的扩展能力，是现代Compose应用开发的最佳实践体现。
