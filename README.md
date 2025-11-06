
<h1 align="center">LM Playground</h1>

<p align="center">
<img src="art/logo.png"/>
</p>

LM Playground is designed as a universal platform for experimenting with various types of Large Language Models (LLMs) on Android devices. It allows users to download different LLMs, load them onto the application, and converse with these models directly on their device—no internet connection required after model download.

![preview](art/Preview.png)

## Features

- 🚀 **On-Device Inference**: Run LLMs locally on your Android device
- 📱 **Multiple Model Support**: Choose from 10+ state-of-the-art models
- 💾 **Efficient Operation**: Optimized GGUF format with Q4_K_M quantization
- 🔄 **Easy Model Management**: Download and switch between models seamlessly
- 💬 **Interactive Chat**: Streaming responses for natural conversation
- 🎨 **Modern UI**: Built with Jetpack Compose and Material 3 design
- 🔒 **Privacy-Focused**: All processing happens on-device

## Currently Supported Models

### Latest Models (Recommended)
* **Qwen 3** - 0.6B, 1.7B, 4B (Alibaba Cloud)
* **Gemma 3** - 1B, 4B (Google)
* **Llama 3.2** - 1B, 3B (Meta)
* **Phi-4 Mini** - 3.8B (Microsoft)
* **DeepSeek R1 Distill** - 1.5B, 7B (DeepSeek AI)

For detailed information about each model, see [MODELS.md](MODELS.md).

## Requirements

### Minimum
- Android 11 (API 30) or higher
- 4GB RAM
- 2GB free storage space (varies by model)
- ARM64 (arm64-v8a) or x86_64 architecture

### Recommended
- Android 14+ (API 34+)
- 8GB+ RAM for larger models
- High-performance ARM64 processor

## Installation

### For Users

*Coming soon: Google Play Store link will be added here*

### For Developers

If you're a developer wanting to build from source or contribute, see the build instructions below.

## Build Instructions

### Prerequisites

* **Android Studio**: [Latest stable version](https://developer.android.com/studio/releases)
* **NDK**: Version 27.2.12479018 or higher
* **JDK**: Version 21 (automatically managed by Android Studio)
* **Git**: For cloning the repository

### Setup Steps

1. **Clone the repository** (including submodules):
```bash
git clone --recursive https://github.com/logic-arts-official/LMPlayground.git
cd LMPlayground
```

**Important**: The `--recursive` flag is required to fetch the llama.cpp submodule.

2. **Configure Android SDK**:

Create `local.properties` in the project root (if not already present):
```properties
sdk.dir=/path/to/Android/sdk
```
Replace `/path/to/Android/sdk` with your actual Android SDK location.

3. **Install NDK**:

Use Android Studio SDK Manager or command line:
```bash
sdkmanager "ndk;27.2.12479018"
sdkmanager --licenses
```

4. **Open in Android Studio**:
   - Launch Android Studio
   - Select `File` > `Open`
   - Navigate to the cloned `LMPlayground` directory
   - Wait for Gradle sync to complete (this may take a few minutes on first run)

5. **Connect a device or start an emulator**:
   - **Physical Device** (recommended for best performance):
     - Enable Developer Options and USB Debugging
     - Connect via USB
   - **Emulator**:
     - Use AVD Manager to create an emulator
     - Recommend API 35 with Google Play
     - Allocate at least 4GB RAM to the emulator

6. **Build and run**:
   - Click the "Run" button (▶️) in Android Studio
   - Or select `Run` > `Run 'app'`
   - Choose your connected device/emulator
   - Wait for build to complete (first build may take 10-15 minutes due to native compilation)

### Running Tests

**Unit Tests**:
```bash
./gradlew test
```

**Instrumented Tests** (requires connected device/emulator):
```bash
./gradlew connectedAndroidTest
```

Or use the managed virtual device:
```bash
./gradlew app:mvdApi35Check
```

## Usage

1. **Launch the app** on your device
2. **Select a model** by tapping the model selector at the top
3. **Download** your chosen model (if not already downloaded)
4. **Wait for loading** - Progress bar shows model loading status
5. **Start chatting** - Type your message and press send
6. **View streaming responses** - AI responses appear token-by-token

### Tips

- Start with smaller models (0.6B-1.7B) for faster responses
- Larger models provide better quality but slower inference
- Ensure sufficient free RAM before loading large models
- Models are cached after download for offline use

## Project Structure

```
LMPlayground/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/druk/
│   │   │   │   ├── llamacpp/       # Native JNI wrappers
│   │   │   │   └── lmplayground/   # Android app code
│   │   │   │       ├── conversation/  # Chat UI and logic
│   │   │   │       ├── models/        # Model management
│   │   │   │       └── theme/         # UI theming
│   │   │   ├── cpp/                # C++ native code
│   │   │   │   ├── llama.cpp/      # llama.cpp submodule
│   │   │   │   └── *.cpp/*.h       # JNI bridge code
│   │   │   └── res/                # Android resources
│   │   ├── test/                   # Unit tests
│   │   └── androidTest/            # Instrumented tests
│   └── build.gradle.kts            # App build configuration
├── gradle/                         # Gradle wrapper and libs
├── ARCHITECTURE.md                 # Architecture documentation
├── CONTRIBUTING.md                 # Contribution guidelines
├── MODELS.md                       # Model information
└── README.md                       # This file
```

## Documentation

- **[ARCHITECTURE.md](ARCHITECTURE.md)**: Technical architecture and design
- **[MODELS.md](MODELS.md)**: Detailed model information and comparison
- **[CONTRIBUTING.md](CONTRIBUTING.md)**: Guidelines for contributors
- **[LICENSE](LICENSE)**: MIT License terms

## Contributing

We welcome contributions! Please read our [Contributing Guidelines](CONTRIBUTING.md) before submitting pull requests.

### Quick Start for Contributors

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Make your changes and test thoroughly
4. Commit with clear messages: `git commit -m "Add feature X"`
5. Push to your fork: `git push origin feature/your-feature`
6. Open a Pull Request

## Technology Stack

- **Language**: Kotlin (app), C++ (native)
- **UI**: Jetpack Compose, Material 3
- **Architecture**: MVVM, Clean Architecture principles
- **Build**: Gradle (Kotlin DSL), CMake (native)
- **LLM Engine**: [llama.cpp](https://github.com/ggerganov/llama.cpp)
- **Model Format**: GGUF with Q4_K_M quantization

## Acknowledgments

This project is built on the excellent [llama.cpp](https://github.com/ggerganov/llama.cpp) project by Georgi Gerganov and contributors. We are grateful to:

- The llama.cpp team for the high-performance inference engine
- [Hugging Face](https://huggingface.co/) for hosting the GGUF model conversions
- Model creators: Alibaba (Qwen), Google (Gemma), Meta (Llama), Microsoft (Phi), DeepSeek AI (DeepSeek)
- The open-source community for tools and libraries

## License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

Copyright (c) 2024 Andriy Druk

## Support

- **Issues**: [GitHub Issues](https://github.com/logic-arts-official/LMPlayground/issues)
- **Discussions**: Use GitHub Issues for questions and discussions
- **Security**: See [SECURITY.md](SECURITY.md) for security policy

## Roadmap

Potential future enhancements:

- [ ] Conversation history persistence
- [ ] Model parameter customization (temperature, top-k, etc.)
- [ ] Multi-turn conversation context management
- [ ] Export/import conversations
- [ ] Dark/light theme support
- [ ] Additional model architectures
- [ ] Performance optimizations
- [ ] Accessibility improvements

## FAQ

**Q: Why does the initial build take so long?**
A: The app compiles llama.cpp from source, which includes substantial C++ code. Subsequent builds are much faster.

**Q: Can I use this offline?**
A: Yes! After downloading a model, all inference happens on-device without internet.

**Q: Which model should I choose?**
A: Start with a 1B-2B parameter model for speed, or 4B+ for quality. See [MODELS.md](MODELS.md) for detailed guidance.

**Q: Why does the app use so much RAM?**
A: LLMs require significant memory. The RAM usage scales with model size. Ensure your device has sufficient free RAM.

**Q: Is my data sent to any servers?**
A: No. All processing is on-device. The only network activity is downloading models from Hugging Face.

**Q: How can I contribute?**
A: Check [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines, or look for "good first issue" labels in the issue tracker.

---

Made with ❤️ by the LM Playground team

