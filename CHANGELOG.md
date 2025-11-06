# Changelog

All notable changes to LM Playground will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Documentation
- Added comprehensive documentation including CONTRIBUTING.md, ARCHITECTURE.md, MODELS.md
- Updated README.md with accurate information about current project state
- Added CHANGELOG.md for version tracking
- Added SECURITY.md for security policy

## [0.1.0] - 2024

### Added
- Initial release of LM Playground
- Support for multiple LLM models:
  - Qwen 3 (0.6B, 1.7B, 4B)
  - Gemma 3 (1B, 4B)
  - Llama 3.2 (1B, 3B)
  - Phi-4 Mini (3.8B)
  - DeepSeek R1 Distill (1.5B, 7B)
- On-device inference using llama.cpp
- Interactive chat interface with streaming responses
- Model download management via Android DownloadManager
- Modern UI built with Jetpack Compose
- Material 3 design implementation
- Support for ARM64 and x86_64 architectures
- Progress indicators for model loading and download
- Native C++ layer for efficient LLM inference
- JNI bridge for Kotlin-C++ communication

### Legacy Models (Marked Obsolete)
- Qwen2.5 (0.5B, 1.5B)
- Llama3.2 (1B, 3B) - older format
- Phi3.5 mini
- Mistral 7B
- Llama3.1 8B
- Gemma2 9B

### Technical Stack
- Kotlin for Android app
- Jetpack Compose for UI
- C++ for native layer
- llama.cpp for LLM inference
- GGUF model format
- Q4_K_M quantization
- Gradle with Kotlin DSL
- CMake for native builds
- Android NDK 27.2.12479018

### Infrastructure
- GitHub Actions CI for automated testing
- Managed Virtual Device testing on API 35
- Gradle version catalog for dependency management
- Dependabot for dependency updates

---

## Version History Notes

### Future Releases

For upcoming features and planned changes, see the [Roadmap](README.md#roadmap) section in the README.

### Release Naming

- **Major versions (X.0.0)**: Significant architectural changes or major feature additions
- **Minor versions (0.X.0)**: New features, model additions, notable improvements
- **Patch versions (0.0.X)**: Bug fixes, documentation updates, minor improvements

### How to Report Issues

If you encounter bugs or have feature requests, please:
1. Check existing [GitHub Issues](https://github.com/logic-arts-official/LMPlayground/issues)
2. Open a new issue with detailed information
3. Include device specs, Android version, and steps to reproduce

---

[Unreleased]: https://github.com/logic-arts-official/LMPlayground/compare/v0.1.0...HEAD
[0.1.0]: https://github.com/logic-arts-official/LMPlayground/releases/tag/v0.1.0
