# API Reference

This document provides detailed API reference for developers working with LM Playground's codebase.

## Table of Contents

- [Native Layer API](#native-layer-api)
- [Kotlin Wrapper API](#kotlin-wrapper-api)
- [ViewModel API](#viewmodel-api)
- [Model Management API](#model-management-api)
- [Data Models](#data-models)
- [Callbacks](#callbacks)

## Native Layer API

### LlamaCpp

Entry point for native library initialization.

```kotlin
package com.druk.llamacpp

object LlamaCpp {
    /**
     * Loads the native library.
     * Must be called before using any other native functionality.
     * Typically called in Application.onCreate()
     */
    fun loadLibrary()
}
```

**Usage**:
```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        LlamaCpp.loadLibrary()
    }
}
```

---

### LlamaModel

Represents a loaded LLM model.

```kotlin
package com.druk.llamacpp

class LlamaModel private constructor() {
    
    companion object {
        /**
         * Loads a model from file.
         *
         * @param path Absolute path to GGUF model file
         * @param nGpuLayers Number of layers to offload to GPU (0 for CPU-only)
         * @param callback Optional callback for loading progress
         * @return Loaded LlamaModel instance
         * @throws RuntimeException if model fails to load
         */
        fun load(
            path: String,
            nGpuLayers: Int = 0,
            callback: LlamaProgressCallback? = null
        ): LlamaModel
    }
    
    /**
     * Creates a generation session with this model.
     *
     * @param inputPrefix Text to prepend to user input (for prompt formatting)
     * @param inputSuffix Text to append to user input (for prompt formatting)
     * @param antiPrompt Array of stop sequences
     * @return New LlamaGenerationSession instance
     */
    fun createSession(
        inputPrefix: String,
        inputSuffix: String,
        antiPrompt: Array<String>
    ): LlamaGenerationSession
    
    /**
     * Releases native resources.
     * Must be called when done with the model.
     * After calling release(), the model cannot be used.
     */
    fun release()
}
```

**Example**:
```kotlin
val model = LlamaModel.load(
    path = "/path/to/model.gguf",
    nGpuLayers = 0,
    callback = object : LlamaProgressCallback {
        override fun onProgress(progress: Float) {
            println("Loading: ${(progress * 100).toInt()}%")
        }
    }
)

try {
    // Use model
    val session = model.createSession(
        inputPrefix = "",
        inputSuffix = "",
        antiPrompt = emptyArray()
    )
    // ... use session ...
} finally {
    model.release()
}
```

---

### LlamaGenerationSession

Handles text generation with a loaded model.

```kotlin
package com.druk.llamacpp

class LlamaGenerationSession(
    private val modelPtr: Long,
    private val inputPrefix: String,
    private val inputSuffix: String,
    private val antiPrompt: Array<String>
) {
    
    /**
     * Generates text based on prompt.
     *
     * @param prompt User input text
     * @param callback Callback for receiving generated tokens
     */
    fun generate(
        prompt: String,
        callback: LlamaGenerationCallback
    )
    
    /**
     * Stops ongoing generation.
     * Can be called from any thread.
     */
    fun stop()
    
    /**
     * Releases session resources.
     */
    fun release()
}
```

**Example**:
```kotlin
val session = model.createSession(
    inputPrefix = "",
    inputSuffix = "",
    antiPrompt = emptyArray()
)

session.generate(
    prompt = "What is the capital of France?",
    callback = object : LlamaGenerationCallback {
        override fun onToken(token: String) {
            print(token)  // Print each token as generated
        }
        
        override fun onComplete() {
            println("\nGeneration complete")
        }
    }
)
```

---

## Kotlin Wrapper API

### Callbacks

#### LlamaProgressCallback

Callback for model loading progress.

```kotlin
package com.druk.llamacpp

interface LlamaProgressCallback {
    /**
     * Called periodically during model loading.
     *
     * @param progress Loading progress (0.0 to 1.0)
     */
    fun onProgress(progress: Float)
}
```

#### LlamaGenerationCallback

Callback for text generation events.

```kotlin
package com.druk.llamacpp

interface LlamaGenerationCallback {
    /**
     * Called for each generated token.
     *
     * @param token Generated text token (may be partial word)
     */
    fun onToken(token: String)
    
    /**
     * Called when generation completes.
     */
    fun onComplete()
}
```

---

## ViewModel API

### ConversationViewModel

Main ViewModel managing conversation state and model operations.

```kotlin
package com.druk.lmplayground.conversation

class ConversationViewModel(val app: Application) : AndroidViewModel(app) {
    
    // Observable State
    val isGenerating: LiveData<Boolean>
    val isModelReady: LiveData<Boolean>
    val modelLoadingProgress: LiveData<Float>
    val loadedModel: LiveData<ModelInfo?>
    val models: LiveData<List<ModelInfo>>
    val messages: LiveData<List<Message>>
    val downloadProgress: LiveData<Map<Long, Float>>
    
    /**
     * Loads a model and prepares it for use.
     *
     * @param modelInfo Model to load
     */
    fun loadModel(modelInfo: ModelInfo)
    
    /**
     * Sends a user message and generates AI response.
     *
     * @param text User message text
     */
    fun sendMessage(text: String)
    
    /**
     * Stops ongoing text generation.
     */
    fun stopGeneration()
    
    /**
     * Initiates model download.
     *
     * @param modelInfo Model to download
     */
    fun downloadModel(modelInfo: ModelInfo)
    
    /**
     * Cancels an ongoing download.
     *
     * @param downloadId Download ID to cancel
     */
    fun cancelDownload(downloadId: Long)
    
    /**
     * Refreshes the list of available models.
     */
    fun refreshModels()
}
```

**Usage in Composable**:
```kotlin
@Composable
fun ConversationScreen(viewModel: ConversationViewModel) {
    val messages by viewModel.messages.observeAsState(emptyList())
    val isGenerating by viewModel.isGenerating.observeAsState(false)
    
    Column {
        MessageList(messages)
        
        if (isGenerating) {
            LinearProgressIndicator()
        }
        
        MessageInput(
            onSend = { text -> viewModel.sendMessage(text) },
            enabled = !isGenerating
        )
    }
}
```

---

## Model Management API

### ModelInfoProvider

Provides list of available models.

```kotlin
package com.druk.lmplayground.models

object ModelInfoProvider {
    /**
     * Builds list of available models.
     *
     * @param downloadsDir Directory to check for downloaded model files
     * @return List of ModelInfo, sorted by name
     */
    fun buildModelList(
        downloadsDir: File = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS
        )
    ): List<ModelInfo>
}
```

**Usage**:
```kotlin
val models = ModelInfoProvider.buildModelList()
models.forEach { model ->
    println("${model.name}: ${if (model.file != null) "Downloaded" else "Not downloaded"}")
}
```

---

## Data Models

### ModelInfo

Represents information about an LLM model.

```kotlin
package com.druk.lmplayground.models

data class ModelInfo(
    /** Display name of the model */
    val name: String,
    
    /** Local file if model is downloaded, null otherwise */
    val file: File? = null,
    
    /** Hugging Face download URL */
    val remoteUri: Uri? = null,
    
    /** Active download ID (if downloading) */
    val downloadId: Long? = null,
    
    /** Text to prepend to user input for prompt formatting */
    val inputPrefix: String? = null,
    
    /** Text to append to user input for prompt formatting */
    val inputSuffix: String? = null,
    
    /** Stop sequences for generation */
    val antiPrompt: Array<String> = emptyArray(),
    
    /** Whether this model is obsolete (superseded by newer version) */
    val obsolete: Boolean = false,
    
    /** Human-readable description with size and parameters */
    val description: String
)
```

**Properties**:
- `name`: Used for display and identification
- `file`: Non-null when model is downloaded and available
- `remoteUri`: Where to download the model from
- `downloadId`: Tracked during active download
- `inputPrefix`/`inputSuffix`: Format user input for specific model requirements
- `antiPrompt`: Sequences that stop generation
- `obsolete`: Hidden from new users but available if already downloaded
- `description`: Shown to users with file size and parameter count

---

### Message

Represents a chat message.

```kotlin
package com.druk.lmplayground.conversation

data class Message(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: Long,
    val isComplete: Boolean = true
)
```

**Properties**:
- `id`: Unique identifier (typically UUID)
- `text`: Message content
- `isUser`: true for user messages, false for AI messages
- `timestamp`: Unix timestamp in milliseconds
- `isComplete`: false while AI is still generating, true when done

---

### ConversationUiState

UI state for conversation screen.

```kotlin
package com.druk.lmplayground.conversation

data class ConversationUiState(
    val messages: List<Message> = emptyList(),
    val isGenerating: Boolean = false,
    val isModelReady: Boolean = false,
    val modelLoadingProgress: Float = 0f,
    val error: String? = null
)
```

---

## Extension Functions

### File Extensions

```kotlin
/**
 * Checks if file exists and is readable.
 */
fun File.isAvailable(): Boolean = exists() && canRead()

/**
 * Gets human-readable file size.
 */
fun File.getReadableSize(): String {
    val sizeBytes = length()
    return when {
        sizeBytes < 1024 -> "$sizeBytes B"
        sizeBytes < 1024 * 1024 -> "${sizeBytes / 1024} KB"
        sizeBytes < 1024 * 1024 * 1024 -> "${sizeBytes / (1024 * 1024)} MB"
        else -> "${sizeBytes / (1024 * 1024 * 1024)} GB"
    }
}
```

---

## Compose Components

### MessageChatBubble

Displays a single chat message.

```kotlin
package com.druk.lmplayground.conversation

@Composable
fun MessageChatBubble(
    message: Message,
    modifier: Modifier = Modifier
)
```

**Usage**:
```kotlin
MessageChatBubble(
    message = Message(
        id = UUID.randomUUID().toString(),
        text = "Hello, world!",
        isUser = true,
        timestamp = System.currentTimeMillis()
    )
)
```

---

### SelectModelDialog

Dialog for selecting an LLM model.

```kotlin
package com.druk.lmplayground.models

@Composable
fun SelectModelDialog(
    models: List<ModelInfo>,
    currentModel: ModelInfo?,
    onModelSelected: (ModelInfo) -> Unit,
    onDownloadModel: (ModelInfo) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
)
```

**Parameters**:
- `models`: List of available models
- `currentModel`: Currently loaded model (if any)
- `onModelSelected`: Called when user selects a model
- `onDownloadModel`: Called when user wants to download a model
- `onDismiss`: Called when dialog is dismissed

---

## Threading Model

### Native Layer

- **Model Loading**: Blocks calling thread (call from background)
- **Text Generation**: Blocks calling thread (call from background)
- **Callbacks**: Called on the thread that initiated the operation

### ViewModel

- **UI Updates**: LiveData updates happen on main thread
- **Model Operations**: Run on `Dispatchers.IO` via coroutines
- **Generation**: Runs on background thread, callbacks posted to main thread

**Example Threading**:
```kotlin
// In ViewModel
viewModelScope.launch(Dispatchers.IO) {
    val model = LlamaModel.load(path, 0, progressCallback)
    
    withContext(Dispatchers.Main) {
        _isModelReady.value = true
    }
    
    session.generate(prompt, object : LlamaGenerationCallback {
        override fun onToken(token: String) {
            // Already on main thread
            updateMessage(token)
        }
        
        override fun onComplete() {
            // Already on main thread
            markComplete()
        }
    })
}
```

---

## Error Handling

### Native Errors

Native methods may throw `RuntimeException` with error details:

```kotlin
try {
    val model = LlamaModel.load(path)
} catch (e: RuntimeException) {
    Log.e("LM", "Failed to load model: ${e.message}")
    // Handle error (show to user, fallback, etc.)
}
```

### ViewModel Errors

Errors are exposed via LiveData:

```kotlin
val error: LiveData<String?> = _error

// In ViewModel
private fun handleError(message: String) {
    _error.value = message
}

// In UI
val error by viewModel.error.observeAsState()
error?.let { errorMessage ->
    ErrorDialog(errorMessage)
}
```

---

## Best Practices

### Memory Management

1. **Always release models**:
```kotlin
val model = LlamaModel.load(path)
try {
    // Use model
} finally {
    model.release()
}
```

2. **Release sessions**:
```kotlin
val session = model.createSession(...)
try {
    // Use session
} finally {
    session.release()
}
```

3. **Switch models properly**:
```kotlin
// Release old model before loading new one
currentModel?.release()
currentModel = LlamaModel.load(newModelPath)
```

### Coroutines

1. **Use viewModelScope** for ViewModel operations
2. **Use Dispatchers.IO** for blocking operations
3. **Use Dispatchers.Main** for UI updates

### State Management

1. **Expose immutable LiveData** to UI
2. **Use private MutableLiveData** internally
3. **Use StateFlow** for more complex state

---

## Platform-Specific Notes

### Android

- **Permissions**: INTERNET for downloads, storage for model files
- **Lifecycle**: ViewModels survive configuration changes
- **Background**: Use WorkManager for long-running downloads (future enhancement)

### JNI

- **Reference Management**: Native code must manage JNI references properly
- **Thread Safety**: JNI calls must be made on appropriate threads
- **Error Handling**: Native exceptions should be caught and reported to Kotlin

---

## Migration Guide

### From 0.0.x to 0.1.x

If coming from an earlier version:

1. Update model names (Qwen2.5 → Qwen 3, etc.)
2. Check for new models in ModelInfoProvider
3. Obsolete models are automatically filtered if not downloaded

---

## Testing

### Unit Testing Models

```kotlin
@Test
fun `test model list is sorted`() {
    val models = ModelInfoProvider.buildModelList(File("."))
    val sortedModels = models.sortedBy { it.name }
    assertEquals(sortedModels, models)
}
```

### Mocking Native Layer

For testing, use composition or interface-based approaches since LlamaModel has a private constructor:

```kotlin
// Create a test wrapper instead of extending LlamaModel
class TestLlamaModelWrapper(
    private val mockSession: LlamaGenerationSession
) {
    fun createSession(
        inputPrefix: String,
        inputSuffix: String,
        antiPrompt: Array<String>
    ): LlamaGenerationSession = mockSession
    
    fun release() {
        // Mock cleanup
    }
}

// Or use a mock generation session directly
class MockLlamaGenerationSession : LlamaGenerationSession(
    modelPtr = 0L,
    inputPrefix = "",
    inputSuffix = "",
    antiPrompt = emptyArray()
) {
    override fun generate(prompt: String, callback: LlamaGenerationCallback) {
        callback.onToken("Mocked response")
        callback.onComplete()
    }
}
```

---

## Further Reading

- [ARCHITECTURE.md](ARCHITECTURE.md) - Overall architecture
- [CONTRIBUTING.md](CONTRIBUTING.md) - How to contribute
- [MODELS.md](MODELS.md) - Model information
- [llama.cpp Documentation](https://github.com/ggerganov/llama.cpp)

---

For questions about the API, please open an issue on GitHub.
