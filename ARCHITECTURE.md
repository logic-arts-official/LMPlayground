# Architecture Documentation

This document provides an overview of the LM Playground architecture, explaining how different components work together to enable on-device LLM inference on Android.

## Table of Contents

- [High-Level Architecture](#high-level-architecture)
- [Layer Architecture](#layer-architecture)
- [Core Components](#core-components)
- [Data Flow](#data-flow)
- [Native Integration](#native-integration)
- [Model Management](#model-management)
- [UI Architecture](#ui-architecture)
- [Performance Considerations](#performance-considerations)

## High-Level Architecture

LM Playground follows a layered architecture pattern:

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│    (Jetpack Compose UI Components)     │
└─────────────────────────────────────────┘
                    │
┌─────────────────────────────────────────┐
│          Business Logic Layer           │
│         (ViewModels, State)             │
└─────────────────────────────────────────┘
                    │
┌─────────────────────────────────────────┐
│         Native Wrapper Layer            │
│        (Kotlin JNI Wrappers)            │
└─────────────────────────────────────────┘
                    │
┌─────────────────────────────────────────┐
│          Native Layer (C++)             │
│        (llama.cpp integration)          │
└─────────────────────────────────────────┘
```

## Layer Architecture

### 1. Presentation Layer

**Location**: `app/src/main/java/com/druk/lmplayground/`

**Components**:
- `MainActivity.kt`: Single activity hosting Compose UI
- Compose UI components in various packages
- Theme definitions (`theme/`)

**Responsibilities**:
- Render UI using Jetpack Compose
- Handle user interactions
- Display application state
- Apply Material 3 theming

**Key Files**:
- `MainActivity.kt`: Entry point, hosts Compose content
- `App.kt`: Application class, initializes native library
- `conversation/MessageChatBubble.kt`: Chat message UI
- `models/SelectModelDialog.kt`: Model selection UI
- `AppBar.kt`: Top app bar component

### 2. Business Logic Layer

**Location**: `app/src/main/java/com/druk/lmplayground/conversation/`

**Components**:
- `ConversationViewModel.kt`: Main business logic controller
- `ConversationUiState.kt`: UI state definitions
- `Message.kt`: Message data models

**Responsibilities**:
- Manage application state
- Coordinate model loading and inference
- Handle model downloads via DownloadManager
- Manage conversation history
- Process user input and model responses

**State Management**:
- Uses `LiveData` for observable state
- ViewModels survive configuration changes
- Coroutines for asynchronous operations

### 3. Native Wrapper Layer

**Location**: `app/src/main/java/com/druk/llamacpp/`

**Components**:
- `LlamaCpp.kt`: Main native library interface
- `LlamaModel.kt`: Model loading and management
- `LlamaGenerationSession.kt`: Text generation session
- `LlamaGenerationCallback.kt`: Callback interface for generation events
- `LlamaProgressCallback.kt`: Callback interface for loading progress

**Responsibilities**:
- Provide Kotlin-friendly interface to native code
- Manage native resource lifecycle
- Handle JNI interactions
- Abstract native implementation details

**Key Interfaces**:

```kotlin
// Initialize native library
LlamaCpp.loadLibrary()

// Load a model
val model = LlamaModel.load(
    path = modelPath,
    nGpuLayers = 0,
    callback = progressCallback
)

// Create generation session
val session = model.createSession(
    inputPrefix = "",
    inputSuffix = "",
    antiPrompt = emptyArray()
)

// Generate text
session.generate(
    prompt = userInput,
    callback = generationCallback
)
```

### 4. Native Layer

**Location**: `app/src/main/cpp/`

**Components**:
- `native-lib.cpp`: JNI entry points
- `LlamaModel.cpp`: C++ model wrapper
- `LlamaGenerationSession.cpp`: C++ generation session
- `LlamaCpp.h`: Native header definitions
- `llama.cpp/`: Submodule containing llama.cpp library

**Responsibilities**:
- Execute LLM inference using llama.cpp
- Manage model memory and resources
- Perform tokenization and detokenization
- Handle low-level tensor operations
- Provide callbacks to Kotlin layer

**Build Configuration**:
- CMake build system (`CMakeLists.txt`)
- Supports ARM64 (arm64-v8a) and x86_64 architectures
- Links against llama.cpp static library
- Optimized for mobile performance

## Core Components

### ConversationViewModel

The heart of the application's business logic.

**Key Responsibilities**:
1. **Model Management**:
   - Maintains list of available models
   - Handles model loading/unloading
   - Tracks model loading progress
   - Manages model downloads

2. **Conversation Handling**:
   - Maintains conversation history
   - Processes user messages
   - Generates AI responses
   - Handles message state (pending, complete, error)

3. **Resource Management**:
   - Ensures proper cleanup of native resources
   - Manages ViewModelScope for coroutines
   - Handles lifecycle events

**State Properties**:
- `_isGenerating`: Whether text generation is in progress
- `_isModelReady`: Whether a model is loaded and ready
- `_modelLoadingProgress`: Progress percentage (0.0 - 1.0)
- `_loadedModel`: Currently loaded model info
- `_models`: List of available models
- `_messages`: Conversation history

### ModelInfoProvider

Central registry for supported models.

**Features**:
- Defines available LLM models
- Maps model files to metadata
- Provides download URLs
- Specifies model-specific prompting formats
- Marks obsolete models
- Sorts models by name

**Model Properties**:
- `name`: Display name
- `file`: Local file (if downloaded)
- `remoteUri`: Download URL (Hugging Face)
- `downloadId`: Active download ID
- `inputPrefix`/`inputSuffix`: Prompt formatting
- `antiPrompt`: Stop sequences
- `obsolete`: Whether model is deprecated
- `description`: User-facing description with size and parameters

### LlamaModel

Native model wrapper providing Kotlin interface.

**Lifecycle**:
1. Load: `LlamaModel.load(path, nGpuLayers, callback)`
2. Use: Create sessions, generate text
3. Release: `release()` to free native memory

**Features**:
- Progress callbacks during loading
- Thread-safe operations
- Automatic resource cleanup via finalization

### LlamaGenerationSession

Text generation session manager.

**Features**:
- Stateful generation (maintains context)
- Streaming token generation
- Stop sequence detection
- Temperature and other sampling parameters

**Generation Flow**:
1. Create session with prompt formatting
2. Call `generate()` with user prompt
3. Receive tokens via callback
4. Optionally stop generation early
5. Session maintains conversation context

## Data Flow

### Model Loading Flow

```
User selects model
       │
       ▼
ConversationViewModel.loadModel()
       │
       ├──► Check if file exists
       │    └──► If not, initiate download
       │         └──► DownloadManager handles download
       │              └──► BroadcastReceiver notifies completion
       │
       ▼
Release previous model (if any)
       │
       ▼
LlamaModel.load() [Native call]
       │
       ├──► Load model file
       ├──► Parse GGUF format
       ├──► Allocate tensors
       ├──► Report progress via callback
       │
       ▼
Create LlamaGenerationSession
       │
       ▼
Update UI state (model ready)
```

### Message Generation Flow

```
User types message and sends
       │
       ▼
ConversationViewModel.sendMessage()
       │
       ├──► Add user message to history
       ├──► Create assistant message (pending)
       │
       ▼
session.generate()
       │
       ├──► Tokenize prompt
       ├──► Run inference (llama.cpp)
       │    └──► Token-by-token generation
       │         └──► Callback for each token
       │              │
       │              ▼
       │         Update message content in UI
       │         (streaming effect)
       │
       ▼
Generation completes
       │
       ├──► Mark message as complete
       └──► Ready for next message
```

## Native Integration

### JNI Architecture

The app uses JNI (Java Native Interface) to bridge Kotlin and C++ code.

**JNI Layers**:
1. **Kotlin Interface**: Public API in `com.druk.llamacpp` package
2. **JNI Bridge**: Native methods in `native-lib.cpp`
3. **C++ Implementation**: Model and session wrappers
4. **llama.cpp Library**: Core inference engine

**Key JNI Patterns**:

1. **Native Method Declaration** (Kotlin):
```kotlin
private external fun nativeLoad(
    path: String,
    nGpuLayers: Int
): Long  // Returns native pointer
```

2. **JNI Implementation** (C++):
```cpp
JNIEXPORT jlong JNICALL
Java_com_druk_llamacpp_LlamaModel_nativeLoad(
    JNIEnv* env,
    jobject thiz,
    jstring path,
    jint nGpuLayers
) {
    // Implementation
}
```

3. **Callback from Native to Kotlin**:
```cpp
// C++ calls back to Kotlin
jclass callbackClass = env->GetObjectClass(callback);
jmethodID methodId = env->GetMethodID(
    callbackClass, 
    "onProgress", 
    "(F)V"
);
env->CallVoidMethod(callback, methodId, progress);
```

### Memory Management

**Native Resource Lifecycle**:
- Native objects allocated via `new` in JNI methods
- Pointers stored as `Long` in Kotlin objects
- Explicit `release()` methods free native memory
- Finalizers provide backup cleanup

**Best Practices**:
- Always call `release()` when done
- Use `try-finally` or structured concurrency for cleanup
- Monitor native heap usage

## Model Management

### Model Storage

- **Location**: External storage Downloads directory
- **Format**: GGUF (GPT-Generated Unified Format)
- **Quantization**: Q4_K_M (4-bit quantization, medium quality)
- **Typical Size**: 500MB - 5GB per model

### Model Download Process

1. User selects model without local file
2. App constructs download request
3. Android DownloadManager handles download
4. BroadcastReceiver notifies app on completion
5. Model becomes available for loading

### Model Updates

To add a new model:
1. Add entry to `ModelInfoProvider.buildModelList()`
2. Specify Hugging Face download URL
3. Set prompt formatting (prefix/suffix/antiPrompt)
4. Provide description with size and parameters
5. Test with actual model file

## UI Architecture

### Compose Structure

**Component Hierarchy**:
```
MainActivity
  └─ ContentMainBinding (ViewBinding + Compose)
       └─ Conversation UI
            ├─ AppBar
            ├─ MessageList
            │    └─ MessageChatBubble (for each message)
            ├─ InputField
            └─ SelectModelDialog (when opened)
```

**State Management**:
- ViewModels expose `LiveData` / `StateFlow`
- Composables observe state via `observeAsState()`
- UI recomposes when state changes
- Unidirectional data flow (UDF pattern)

**Key Composables**:
- `MessageChatBubble`: Individual chat message
- `SelectModelDialog`: Model selection popup
- `AppBar`: Top navigation bar

### Theming

**Location**: `app/src/main/java/com/druk/lmplayground/theme/`

**Components**:
- `Color.kt`: Color palette definitions
- `Typography.kt`: Text styles
- `Themes.kt`: Light/dark theme configurations

**Approach**:
- Material 3 design system
- Dynamic colors where supported
- Consistent spacing and elevation

## Performance Considerations

### Optimization Strategies

1. **Model Loading**:
   - Load models on background thread
   - Show progress to user
   - Cache loaded model (don't reload unnecessarily)

2. **Text Generation**:
   - Generate on background thread
   - Stream tokens to UI for responsive feel
   - Allow user to stop generation

3. **Memory Management**:
   - Release models when switching
   - Monitor memory usage
   - Use appropriate model sizes for device

4. **UI Performance**:
   - Efficient Compose recomposition
   - Lazy loading for long conversations
   - Debounce user input if needed

### Device Requirements

**Minimum**:
- Android API 30 (Android 11)
- 4GB RAM
- ARM64 architecture

**Recommended**:
- Android API 35 (latest)
- 8GB+ RAM
- High-end ARM64 processor
- Ample storage for models

### Model Selection Guidelines

| Model Size | Parameters | RAM Usage | Recommended Device |
|------------|------------|-----------|-------------------|
| 0.5-1B     | 500M-1B    | ~2GB      | Mid-range phones  |
| 1-3B       | 1-3B       | ~3-4GB    | Modern phones     |
| 4-7B       | 4-7B       | ~5-8GB    | High-end phones   |
| 9B+*       | 9B+        | ~9GB+     | Tablets/flagship  |

*Note: Currently no actively supported models exceed 7B parameters. This row applies to obsolete models like Gemma2 9B or future large models.

## Build System

### Gradle Configuration

**Root `build.gradle.kts`**:
- Plugin versions (Android, Kotlin, Compose)
- Repository configuration

**App `build.gradle.kts`**:
- Android SDK versions (min 30, target 35, compile 35)
- Build variants (debug, release)
- Compose configuration
- Native build configuration (CMake)
- Dependencies

**Version Catalog**: `gradle/libs.versions.toml`
- Centralized dependency management
- Version coordination

### Native Build (CMake)

**`app/src/main/cpp/CMakeLists.txt`**:
- Links llama.cpp submodule
- Configures build flags
- Sets up include paths
- Defines shared library

**Build Process**:
1. Gradle invokes CMake
2. CMake builds llama.cpp
3. CMake builds JNI wrappers
4. Shared library packaged in APK

## Testing Strategy

### Unit Tests

**Location**: `app/src/test/java/`

**Current Tests**:
- `ModelInfoProviderTest`: Verifies model list integrity

**Test Tools**:
- JUnit 4
- Mockito (if needed)
- Kotlin test utilities

### Instrumented Tests

**Location**: `app/src/androidTest/java/`

**Approach**:
- Run on real device or emulator
- Test Android-specific functionality
- UI testing with Compose test framework

**CI/CD**:
- GitHub Actions runs tests on PR
- Managed Virtual Device for consistent environment

## Future Enhancements

Potential architectural improvements:

1. **Repository Pattern**: Introduce repository layer for model data
2. **Dependency Injection**: Consider Hilt for DI
3. **Modularization**: Split into feature modules
4. **State Management**: Consider Kotlin Flow / StateFlow more extensively
5. **Database**: Room DB for conversation persistence
6. **Multi-Model Support**: Load multiple models simultaneously (memory permitting)
7. **Streaming API**: WebSocket support for remote LLM fallback

## References

- [Android Architecture Guide](https://developer.android.com/topic/architecture)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [llama.cpp GitHub](https://github.com/ggerganov/llama.cpp)
- [GGUF Format](https://github.com/ggerganov/ggml/blob/master/docs/gguf.md)
- [JNI Specification](https://docs.oracle.com/javase/8/docs/technotes/guides/jni/)
