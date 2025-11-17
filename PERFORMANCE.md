# Performance Guide

This guide provides detailed information about optimizing and understanding LM Playground's performance.

## Table of Contents

- [Overview](#overview)
- [Performance Benchmarks](#performance-benchmarks)
- [Optimization Strategies](#optimization-strategies)
- [Profiling and Monitoring](#profiling-and-monitoring)
- [Device-Specific Considerations](#device-specific-considerations)
- [Model Performance Comparison](#model-performance-comparison)
- [Advanced Configuration](#advanced-configuration)

## Overview

### What Affects Performance?

LM Playground performance depends on several factors:

1. **Model Size**: Larger models (more parameters) = slower inference
2. **Device CPU**: Faster processors = quicker generation
3. **Available RAM**: More RAM = ability to run larger models
4. **Thermal State**: Hot devices throttle CPU, slowing inference
5. **Quantization**: Q4_K_M offers good balance of size and quality
6. **Context Length**: Longer conversations slightly slow generation

### Performance Metrics

Key metrics to understand:

- **Tokens/second**: How fast the model generates text
- **Time to First Token (TTFT)**: Delay before first word appears
- **Model Load Time**: Time to load model into memory
- **Memory Usage**: RAM consumed by loaded model
- **CPU Usage**: Processor utilization during inference

## Performance Benchmarks

### Reference Device Performance

Benchmarks on common device types (approximate values):

#### High-End Phone (2024)
*Example: Samsung Galaxy S24, Snapdragon 8 Gen 3, 12GB RAM*

| Model | Load Time | Tokens/sec | TTFT | RAM Usage |
|-------|-----------|------------|------|-----------|
| Qwen 3 0.6B | 10-15s | 15-25 | 0.5s | ~1.5GB |
| Qwen 3 1.7B | 15-20s | 10-15 | 0.8s | ~2.5GB |
| Qwen 3 4B | 25-35s | 5-8 | 1.5s | ~4.5GB |
| DeepSeek 7B | 45-60s | 2-4 | 2.5s | ~7GB |

#### Mid-Range Phone (2023)
*Example: Google Pixel 7a, Tensor G2, 8GB RAM*

| Model | Load Time | Tokens/sec | TTFT | RAM Usage |
|-------|-----------|------------|------|-----------|
| Qwen 3 0.6B | 15-20s | 10-18 | 0.7s | ~1.5GB |
| Qwen 3 1.7B | 20-30s | 6-10 | 1.2s | ~2.5GB |
| Qwen 3 4B | 35-50s | 3-5 | 2s | ~4.5GB |
| DeepSeek 7B | 70-90s | 1-2 | 4s | ~7GB |

#### Entry-Level Phone (2022)
*Example: Samsung Galaxy A33, MediaTek Dimensity 1080, 6GB RAM*

| Model | Load Time | Tokens/sec | TTFT | RAM Usage |
|-------|-----------|------------|------|-----------|
| Qwen 3 0.6B | 20-30s | 6-12 | 1s | ~1.5GB |
| Qwen 3 1.7B | 35-45s | 4-7 | 1.8s | ~2.5GB |
| Llama 3.2 3B | 50-70s | 2-4 | 3s | ~3.5GB |
| Qwen 3 4B | May struggle due to RAM |

#### Tablet (High-End)
*Example: Samsung Galaxy Tab S9+, Snapdragon 8 Gen 2, 12GB RAM*

| Model | Load Time | Tokens/sec | TTFT | RAM Usage |
|-------|-----------|------------|------|-----------|
| Qwen 3 0.6B | 8-12s | 20-30 | 0.4s | ~1.5GB |
| Qwen 3 4B | 20-30s | 7-12 | 1.2s | ~4.5GB |
| DeepSeek 7B | 40-55s | 3-6 | 2s | ~7GB |

**Note**: Actual performance varies based on device temperature, background apps, and Android version.

### Performance by Architecture

Different processor architectures show different characteristics:

**Qualcomm Snapdragon (ARM)**:
- ✅ Good general performance
- ✅ Power efficient
- ✅ Wide device support

**Google Tensor (ARM)**:
- ✅ Optimized for ML workloads
- ✅ Good sustained performance
- ⚠️ Can throttle under extended load

**MediaTek Dimensity (ARM)**:
- ⚠️ Variable performance across models
- ✅ Good value in mid-range
- ⚠️ May throttle aggressively

**Samsung Exynos (ARM)**:
- ⚠️ Mixed performance
- ⚠️ Thermal management varies by model
- ✅ Improving in recent generations

**x86_64 (Emulators/Tablets)**:
- ✅ Good performance on high-end hardware
- ⚠️ Less common in mobile devices
- ✅ Useful for development/testing

## Optimization Strategies

### For End Users

#### 1. Choose the Right Model

**For Speed**:
```
Qwen 3 0.6B > Gemma 3 1B > Llama 3.2 1B > Qwen 3 1.7B
```

**For Quality**:
```
DeepSeek 7B > Qwen 3 4B > Gemma 3 4B > Llama 3.2 3B
```

**Balanced (Recommended)**:
```
Qwen 3 1.7B or Llama 3.2 3B
```

#### 2. Manage Device Resources

**Before Using**:
- Close unnecessary apps
- Clear recent apps list
- Restart device if it's been running for days
- Ensure device is cool (not from previous use)

**During Use**:
- Keep app in foreground
- Don't multitask heavily
- Avoid charging if device is warm
- Remove phone case for better cooling

**After Use**:
- Close app to free RAM for other apps
- Don't leave model loaded indefinitely

#### 3. Environmental Factors

**Temperature Management**:
- Use in air-conditioned environment if possible
- Don't use in direct sunlight
- Take breaks if device gets hot
- Let device cool between long sessions

**Power Management**:
- Plug in for consistent performance
- But unplug if device gets too warm
- Disable battery saver during use
- Enable airplane mode if offline use

#### 4. Conversation Optimization

**Keep Conversations Moderate Length**:
- Very long conversations slightly slow inference
- Start fresh chat after 20-30 exchanges if you notice slowdown
- Shorter messages generate faster than long messages

**Be Efficient**:
- Ask clear, specific questions
- Avoid unnecessary follow-ups
- Use stop generation if response is sufficient

### For Developers

#### 1. Build Optimizations

**Gradle Configuration**:
```kotlin
// Already optimized in build.gradle.kts
android {
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(...)
        }
    }
    
    packagingOptions {
        // Reduce APK size
        resources.excludes.add("META-INF/*.kotlin_module")
    }
}
```

**Native Optimization**:
```cmake
# CMakeLists.txt optimization flags
set(CMAKE_CXX_FLAGS_RELEASE "-O3 -DNDEBUG")
set(CMAKE_C_FLAGS_RELEASE "-O3 -DNDEBUG")
```

#### 2. Memory Management

**Model Loading**:
```kotlin
// Load on background thread
viewModelScope.launch(Dispatchers.IO) {
    val model = LlamaModel.load(path, nGpuLayers = 0, callback)
    // Use model
}
```

**Proper Cleanup**:
```kotlin
override fun onCleared() {
    super.onCleared()
    session?.release()
    model?.release()
}
```

**Avoid Memory Leaks**:
```kotlin
// Use WeakReference for callbacks if needed
class WeakCallback(callback: LlamaGenerationCallback) : LlamaGenerationCallback {
    private val weakRef = WeakReference(callback)
    
    override fun onToken(token: String) {
        weakRef.get()?.onToken(token)
    }
}
```

#### 3. Threading Optimization

**Coroutine Usage**:
```kotlin
// IO for model operations
viewModelScope.launch(Dispatchers.IO) {
    session.generate(prompt, callback)
}

// Main for UI updates
withContext(Dispatchers.Main) {
    _messages.value = updatedMessages
}
```

**Avoid Blocking Main Thread**:
```kotlin
// ❌ Don't do this
LlamaModel.load(path)  // Blocks UI

// ✅ Do this
viewModelScope.launch(Dispatchers.IO) {
    LlamaModel.load(path)
}
```

#### 4. UI Performance

**Efficient Recomposition**:
```kotlin
@Composable
fun MessageList(messages: List<Message>) {
    // Use key to avoid unnecessary recomposition
    LazyColumn {
        items(messages, key = { it.id }) { message ->
            MessageChatBubble(message)
        }
    }
}
```

**Debounce Updates**:
```kotlin
// For streaming tokens, batch updates
private val tokenBuffer = mutableListOf<String>()
private var lastUpdateTime = 0L

fun onToken(token: String) {
    tokenBuffer.add(token)
    val now = System.currentTimeMillis()
    if (now - lastUpdateTime > 50) { // Update every 50ms
        flushTokens()
        lastUpdateTime = now
    }
}
```

#### 5. Native Layer Optimization

**Efficient JNI Calls**:
```cpp
// Cache method IDs
static jmethodID onTokenMethod = nullptr;
if (onTokenMethod == nullptr) {
    onTokenMethod = env->GetMethodID(callbackClass, "onToken", "(Ljava/lang/String;)V");
}
```

**Minimize JNI Crossing**:
```cpp
// ❌ Slow: Call Java for every token
for (token : tokens) {
    callJavaMethod(token);
}

// ✅ Faster: Batch tokens
std::string batch;
for (token : tokens) {
    batch += token;
    if (batch.size() > BATCH_SIZE) {
        callJavaMethod(batch);
        batch.clear();
    }
}
```

## Profiling and Monitoring

### Using Android Profiler

1. **CPU Profiler**:
   - Record trace during inference
   - Identify hot paths in native code
   - Check for unexpected allocations

2. **Memory Profiler**:
   - Track native heap usage
   - Monitor for memory leaks
   - Verify model cleanup

3. **Energy Profiler**:
   - Measure battery impact
   - Compare models
   - Optimize power usage

### Logcat Performance Logging

```kotlin
// Add timing logs
val startTime = System.currentTimeMillis()
model.load(path)
val loadTime = System.currentTimeMillis() - startTime
Log.d("Performance", "Model loaded in ${loadTime}ms")

val genStart = System.currentTimeMillis()
session.generate(prompt, callback)
val genTime = System.currentTimeMillis() - genStart
Log.d("Performance", "Generation took ${genTime}ms")
```

### Custom Metrics

```kotlin
class PerformanceMetrics {
    var tokensGenerated = 0
    var generationStartTime = 0L
    
    fun startGeneration() {
        tokensGenerated = 0
        generationStartTime = System.currentTimeMillis()
    }
    
    fun onToken() {
        tokensGenerated++
    }
    
    fun endGeneration() {
        val duration = System.currentTimeMillis() - generationStartTime
        val tokensPerSecond = tokensGenerated / (duration / 1000.0)
        Log.i("Performance", "Tokens/sec: $tokensPerSecond")
    }
}
```

## Device-Specific Considerations

### Samsung Devices

**Observations**:
- Game Optimizing Service may interfere
- Game Launcher can boost performance
- Thermal management varies by model

**Optimization**:
- Disable battery optimization for app
- Add to Game Launcher (if available) for performance mode
- Monitor temperature closely on older models

### Google Pixel Devices

**Observations**:
- Tensor chip optimized for ML
- Good sustained performance
- Adaptive Battery may limit background usage

**Optimization**:
- Generally work well out-of-box
- Ensure app isn't restricted in battery settings
- Pixel 6+ recommended for best experience

### OnePlus/Oppo Devices

**Observations**:
- Aggressive RAM management
- May kill background processes
- Game Space available for performance

**Optimization**:
- Lock app in recent apps
- Disable auto-optimize in settings
- Grant all battery permissions
- Use Game Space if available

### Xiaomi/Redmi Devices

**Observations**:
- MIUI has aggressive power saving
- May restrict background app activity
- Security app may interfere

**Optimization**:
- Disable battery optimization
- Allow autostart permission
- Lock in recent apps
- Disable "Clean cached data" for app

## Model Performance Comparison

### Speed Rankings (Fastest to Slowest)

1. **Qwen 3 0.6B** - 15-25 tokens/sec (high-end device)
2. **Gemma 3 1B** - 12-20 tokens/sec
3. **Llama 3.2 1B** - 10-18 tokens/sec
4. **Qwen 3 1.7B** - 8-15 tokens/sec
5. **DeepSeek 1.5B** - 7-12 tokens/sec
6. **Llama 3.2 3B** - 5-10 tokens/sec
7. **Phi-4 Mini** - 4-8 tokens/sec
8. **Qwen 3 4B** - 4-8 tokens/sec
9. **Gemma 3 4B** - 3-7 tokens/sec
10. **DeepSeek 7B** - 2-4 tokens/sec

### Memory Efficiency Rankings

Best memory footprint for capability:

1. **Qwen 3 1.7B** - Good quality at 2.5GB RAM
2. **Llama 3.2 3B** - Great balance at 3.5GB RAM
3. **DeepSeek 1.5B** - Excellent reasoning at 2.5GB RAM
4. **Gemma 3 1B** - Solid performance at 2GB RAM

### Quality vs Performance Matrix

```
Quality ↑
│
│  DeepSeek 7B
│             Qwen 3 4B
│             Gemma 3 4B
│       Llama 3.2 3B
│       Qwen 3 1.7B
│  Gemma 3 1B
│  Qwen 3 0.6B
│
└────────────────────→ Speed
```

## Advanced Configuration

### Future Enhancements

Currently, the app uses default inference parameters. Future versions may expose:

**Temperature** (Controls randomness):
- 0.0 = Deterministic, repetitive
- 0.7 = Balanced (default)
- 1.0+ = Creative, unpredictable

**Top-K** (Vocabulary limiting):
- Lower = More focused
- Higher = More diverse
- Default: 40

**Top-P** (Nucleus sampling):
- 0.9 = Conservative (default)
- 0.95 = More variety
- 1.0 = All tokens considered

**Repeat Penalty**:
- 1.0 = No penalty (default)
- 1.1 = Slight penalty
- 1.3 = Strong penalty

**Context Length**:
- Current: Model default (2048-4096 tokens)
- Longer = Better context, more memory, slower

### GPU Acceleration

Currently, `nGpuLayers = 0` (CPU only). Future work:

**Potential GPU Support**:
- Vulkan backend for mobile GPUs
- Could 2-5x speed with proper implementation
- Requires careful power/thermal management
- Not all Android GPUs support necessary features

**Current Status**:
- CPU-only is most compatible
- Works on all supported devices
- Predictable performance characteristics

## Performance Troubleshooting

### Slower Than Expected

**Check**:
1. Device temperature (thermal throttling?)
2. Background apps (closing them helps?)
3. Battery level (low battery reduces performance?)
4. App version (using latest?)
5. Model file integrity (correct size?)

### High Memory Usage

**Normal**:
- 0.6B model: ~1.5GB
- 1.7B model: ~2.5GB
- 4B model: ~4.5GB
- 7B model: ~7GB

**Too High**:
- Memory leak? Restart app
- Other apps? Close them
- Corrupted model? Re-download

### Inconsistent Performance

**Causes**:
- Thermal throttling (device heating/cooling)
- Background activity (varies)
- Power mode changes (plugged/unplugged)
- Android memory management (freeing RAM)

**Solutions**:
- Warm up device before benchmarking
- Close all other apps
- Use consistent power state
- Take multiple measurements

## Best Practices Summary

### For Users
- ✅ Choose model matching your device capabilities
- ✅ Close background apps before using
- ✅ Keep device cool
- ✅ Use WiFi for downloads
- ✅ Restart app periodically

### For Developers
- ✅ Profile before optimizing
- ✅ Use background threads for model operations
- ✅ Properly manage native resources
- ✅ Batch UI updates for streaming
- ✅ Monitor memory usage
- ✅ Test on range of devices

### For Contributors
- ✅ Benchmark changes
- ✅ Consider thermal impact
- ✅ Test on mid-range devices
- ✅ Profile memory usage
- ✅ Document performance implications

## Resources

- **llama.cpp Performance**: https://github.com/ggerganov/llama.cpp/discussions
- **Android Performance**: https://developer.android.com/topic/performance
- **Profiling Guide**: https://developer.android.com/studio/profile

---

**Last Updated**: November 2025  
**Version**: 0.1.0

For performance issues not covered here, see [TROUBLESHOOTING.md](TROUBLESHOOTING.md) or [open an issue](https://github.com/logic-arts-official/LMPlayground/issues).
