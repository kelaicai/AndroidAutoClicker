# 安卓连点器

一个简单实用的安卓自动点击工具，支持自定义点击频率和持续时间。

## 功能特性

- ✅ 支持多种点击频率（1秒1次、1秒2次）
- ✅ 支持多种持续时间（5秒、10秒、15秒、20秒、25秒、30秒、60秒）
- ✅ 使用无障碍服务实现，安全可靠
- ✅ 简洁直观的用户界面
- ✅ 实时状态显示

## 技术实现

- **开发语言**: Java
- **最低SDK版本**: Android 7.0 (API 24)
- **目标SDK版本**: Android 13 (API 33)
- **核心技术**: AccessibilityService (无障碍服务)

## 项目结构

```
AndroidAutoClicker/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/autoclicker/
│   │       │   ├── MainActivity.java          # 主界面Activity
│   │       │   └── AutoClickService.java      # 无障碍服务
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   └── activity_main.xml      # 主界面布局
│   │       │   ├── values/
│   │       │   │   └── strings.xml            # 字符串资源
│   │       │   └── xml/
│   │       │       └── accessibility_service_config.xml  # 无障碍服务配置
│   │       └── AndroidManifest.xml            # 应用清单
│   └── build.gradle                           # 应用构建配置
├── build.gradle                               # 项目构建配置
├── settings.gradle                            # 项目设置
└── gradle.properties                          # Gradle属性配置
```

## 安装和使用

### 方法一：使用Android Studio（推荐）

1. 使用Android Studio打开项目
2. 等待Gradle同步完成
3. 连接安卓设备或启动模拟器
4. 点击"Run"按钮安装应用

### 方法二：使用在线构建平台

#### GitHub Actions（推荐）

1. **上传到GitHub**:
   ```bash
   git init
   git add .
   git commit -m "Initial commit"
   git remote add origin https://github.com/your-username/AndroidAutoClicker.git
   git push -u origin main
   ```

2. **自动构建**:
   - GitHub Actions会自动触发构建
   - 在仓库的"Actions"标签页查看构建进度
   - 构建完成后下载APK文件

3. **下载APK**:
   - 在Actions页面找到构建任务
   - 下载"app-debug"或"app-release"artifact
   - 安装到安卓设备

#### 其他在线平台

- **App Center**: https://appcenter.ms
- **Bitrise**: https://bitrise.io
- **CircleCI**: https://circleci.com
- **Codemagic**: https://codemagic.io

### 方法三：命令行构建

```bash
# 进入项目目录
cd AndroidAutoClicker

# 赋予执行权限（Linux/macOS）
chmod +x gradlew

# 构建Debug版本
./gradlew assembleDebug

# 构建Release版本
./gradlew assembleRelease

# APK文件位置
# Debug: app/build/outputs/apk/debug/app-debug.apk
# Release: app/build/outputs/apk/release/app-release.apk
```

### 启用无障碍服务

**重要**: 首次使用必须启用无障碍服务，否则无法实现自动点击功能。

1. 打开应用后，会自动提示启用无障碍服务
2. 在无障碍设置页面中，找到"安卓连点器"
3. 开启无障碍服务权限
4. 返回应用主界面

### 使用步骤

1. **选择点击频率**: 
   - "1秒1次": 每秒点击1次
   - "1秒2次": 每秒点击2次

2. **选择持续时间**:
   - 可选择5秒、10秒、15秒、20秒、25秒、30秒、60秒

3. **开始连点**:
   - 点击"开始连点"按钮
   - 应用将在屏幕中心位置自动点击
   - 状态栏会显示"连点器运行中..."

4. **停止连点**:
   - 点击"停止连点"按钮可随时停止
   - 连点器会在达到设定时间后自动停止

## 核心代码说明

### MainActivity.java

主界面Activity，负责：
- UI界面初始化和交互
- 无障碍服务状态检查
- 连点逻辑控制
- 定时任务管理

### AutoClickService.java

无障碍服务类，负责：
- 提供模拟点击功能
- 支持单次点击、长按、滑动等手势操作
- 通过GestureDescription API实现手势模拟

## 注意事项

1. **权限要求**:
   - 需要无障碍服务权限
   - Android 6.0及以上需要悬浮窗权限

2. **使用限制**:
   - 仅支持Android 7.0及以上版本
   - 点击位置固定在屏幕中心
   - 部分应用可能阻止模拟点击

3. **安全提示**:
   - 请勿用于违反应用使用条款的场景
   - 建议在个人设备上使用
   - 使用完毕后可关闭无障碍服务

## 扩展功能

当前版本实现了基本的连点功能，您可以根据需要扩展：

- 自定义点击位置
- 添加多点点击模式
- 实现点击脚本录制
- 添加循环点击功能
- 支持更多手势操作

## 许可证

本项目仅供学习和个人使用。

## 版本历史

- v1.0 (2024-04-28)
  - 初始版本
  - 实现基本连点功能
  - 支持1秒1次和1秒2次频率
  - 支持5-60秒持续时间选择