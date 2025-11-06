# Contributing to LM Playground

Thank you for your interest in contributing to LM Playground! This document provides guidelines and instructions for contributing to the project.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Development Workflow](#development-workflow)
- [Code Standards](#code-standards)
- [Testing](#testing)
- [Submitting Changes](#submitting-changes)
- [Reporting Issues](#reporting-issues)

## Code of Conduct

This project and everyone participating in it is expected to uphold professional standards. Please be respectful and constructive in all interactions.

## Getting Started

### Prerequisites

Before you begin, ensure you have:

- **Android Studio**: [Latest stable version](https://developer.android.com/studio/releases)
- **NDK**: Version 27.2.12479018 or higher
- **Java**: Version 21 (as specified in the project)
- **Git**: For version control
- **Basic knowledge of**:
  - Kotlin programming
  - Android development
  - Jetpack Compose
  - C++ (for native code modifications)

### Understanding the Project

LM Playground is an Android application that enables running Large Language Models locally on Android devices. Key technologies:

- **Language**: Kotlin (Android app) + C++ (native layer via JNI)
- **UI Framework**: Jetpack Compose
- **Build System**: Gradle with Kotlin DSL
- **LLM Backend**: llama.cpp (integrated as a git submodule)
- **Architecture**: MVVM pattern with Android ViewModels

## Development Setup

1. **Clone the repository**:
   ```bash
   git clone --recursive https://github.com/logic-arts-official/LMPlayground.git
   cd LMPlayground
   ```

   Note: The `--recursive` flag is important to clone the llama.cpp submodule.

2. **Configure Android SDK**:
   Create or edit `local.properties` in the project root:
   ```properties
   sdk.dir=/path/to/Android/sdk
   ```

3. **Install NDK**:
   ```bash
   sdkmanager "ndk;27.2.12479018"
   sdkmanager --licenses
   ```

4. **Open in Android Studio**:
   - Launch Android Studio
   - Select `File` > `Open`
   - Navigate to the cloned repository
   - Wait for Gradle sync to complete

5. **Connect a Device or Emulator**:
   - Physical device: Enable USB debugging in Developer Options
   - Emulator: Use AVD Manager to create an emulator with API 35 (target SDK)
   - **Note**: The app requires significant RAM and processing power. Prefer physical devices with ARM64 architecture for optimal performance.

## Development Workflow

### Branch Strategy

- `main` (or default branch): Stable, production-ready code
- Feature branches: `feature/description` or `fix/description`
- Create pull requests to merge changes into main

### Making Changes

1. **Create a new branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes**:
   - Follow the code standards (see below)
   - Write or update tests as needed
   - Update documentation if needed

3. **Test your changes**:
   ```bash
   ./gradlew test
   ./gradlew app:mvdApi35Check  # For instrumented tests
   ```

4. **Commit your changes**:
   ```bash
   git add .
   git commit -m "Brief description of changes"
   ```

## Code Standards

### Kotlin Code Style

- Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Keep functions small and focused on a single responsibility
- Use Kotlin idioms (data classes, sealed classes, extension functions, etc.)

### Code Organization

- **Package structure**:
  - `com.druk.lmplayground` - Main app components
  - `com.druk.lmplayground.conversation` - Chat/conversation UI and logic
  - `com.druk.lmplayground.models` - Model management and data
  - `com.druk.lmplayground.theme` - UI theming
  - `com.druk.llamacpp` - Native library wrapper

- **File naming**:
  - Activities: `*Activity.kt`
  - ViewModels: `*ViewModel.kt`
  - Composables: Descriptive names like `MessageChatBubble.kt`

### Compose Best Practices

- Keep composables small and reusable
- Use `remember` and `derivedStateOf` appropriately
- Prefer stateless composables when possible
- Use proper state hoisting

### Native Code (C++)

- Follow C++11 standards (as specified in CMake)
- Use RAII principles
- Ensure proper JNI reference management
- Document any complex algorithms

### Comments and Documentation

- Use KDoc for public APIs
- Add inline comments for complex logic
- Keep comments up-to-date with code changes

## Testing

### Unit Tests

Location: `app/src/test/java/`

Run unit tests:
```bash
./gradlew test
```

### Instrumented Tests

Location: `app/src/androidTest/java/`

Run on connected device/emulator:
```bash
./gradlew connectedAndroidTest
```

Or use the managed virtual device:
```bash
./gradlew app:mvdApi35Check
```

### Test Coverage

- Write tests for new features
- Maintain or improve existing test coverage
- Focus on business logic and critical paths

## Submitting Changes

### Pull Request Process

1. **Update your branch**:
   ```bash
   git checkout main
   git pull origin main
   git checkout your-branch
   git rebase main
   ```

2. **Push your branch**:
   ```bash
   git push origin your-branch
   ```

3. **Create a Pull Request**:
   - Go to the repository on GitHub
   - Click "New Pull Request"
   - Select your branch
   - Fill in the PR template with:
     - Description of changes
     - Related issue number (if applicable)
     - Testing performed
     - Screenshots (for UI changes)

4. **Code Review**:
   - Address reviewer feedback
   - Push additional commits if needed
   - Be responsive to questions

5. **Merge**:
   - Once approved, a maintainer will merge your PR
   - Delete your branch after merge

### Pull Request Guidelines

- Keep PRs focused on a single feature or fix
- Write clear commit messages
- Include tests for new functionality
- Update documentation as needed
- Ensure CI checks pass

## Reporting Issues

### Bug Reports

When reporting bugs, include:

- **Device information**: Model, Android version, RAM
- **App version**: From About screen or build.gradle.kts
- **Steps to reproduce**: Clear, numbered steps
- **Expected behavior**: What should happen
- **Actual behavior**: What actually happens
- **Logs**: Logcat output if available
- **Screenshots**: If UI-related

### Feature Requests

When requesting features:

- **Clear description**: What feature you want
- **Use case**: Why this feature is needed
- **Examples**: Similar features in other apps
- **Technical considerations**: If you have ideas on implementation

### Questions

For questions:

- Check existing documentation first
- Search closed issues for similar questions
- Provide context about what you're trying to accomplish

## Adding New Models

To add support for a new LLM model:

1. **Verify compatibility**: Ensure the model is in GGUF format with Q4_K_M quantization
2. **Update `ModelInfoProvider.kt`**:
   - Add a new `ModelInfo` entry
   - Include name, remote URI, size, and description
   - Set appropriate input prefix/suffix if needed
3. **Test thoroughly**:
   - Download the model
   - Test conversation generation
   - Verify memory usage is reasonable
4. **Update documentation**: Add the model to MODELS.md and README.md

## Resources

- [Android Development Documentation](https://developer.android.com/)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Kotlin Language Documentation](https://kotlinlang.org/docs/home.html)
- [llama.cpp Project](https://github.com/ggerganov/llama.cpp)
- [GGUF Format Information](https://huggingface.co/docs/hub/gguf)

## Questions?

If you have questions not covered here, feel free to:

- Open an issue on GitHub
- Reach out to the maintainers

Thank you for contributing to LM Playground!
