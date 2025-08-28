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