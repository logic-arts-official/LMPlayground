# Supported Models

This document provides detailed information about the Large Language Models supported by LM Playground.

## Table of Contents

- [Overview](#overview)
- [Current Models](#current-models)
- [Obsolete Models](#obsolete-models)
- [Model Specifications](#model-specifications)
- [Choosing a Model](#choosing-a-model)
- [Technical Details](#technical-details)
- [Adding New Models](#adding-new-models)

## Overview

LM Playground supports a variety of Large Language Models (LLMs) from leading AI research organizations. All models are:

- **Format**: GGUF (GPT-Generated Unified Format)
- **Quantization**: Q4_K_M (4-bit quantization, medium quality)
- **Source**: Hugging Face community conversions
- **Optimized**: For on-device inference on Android

### What is GGUF?

GGUF is a file format for storing models for inference with llama.cpp and other compatible engines. It's designed for efficient loading and execution.

### What is Q4_K_M Quantization?

Q4_K_M is a 4-bit quantization method that:
- Reduces model size by ~75% compared to full precision
- Maintains good quality (medium tier quantization)
- Enables larger models to run on mobile devices
- Balances performance and accuracy

## Current Models

These are the actively supported and recommended models:

### Qwen 3 0.6B

- **Organization**: Alibaba Cloud
- **Parameters**: 0.6 billion
- **File Size**: 484 MB
- **Download**: [Hugging Face](https://huggingface.co/lmstudio-community/Qwen3-0.6B-GGUF)
- **File Name**: `Qwen3-0.6B-Q4_K_M.gguf`

**Characteristics**:
- Smallest model in the collection
- Very fast inference
- Low memory footprint (~1-2GB RAM)
- Suitable for simple tasks and older devices
- Good for testing and experimentation

**Best For**:
- Quick responses
- Simple conversations
- Devices with limited RAM
- Battery conservation

---

### Qwen 3 1.7B

- **Organization**: Alibaba Cloud
- **Parameters**: 1.7 billion
- **File Size**: 1.28 GB
- **Download**: [Hugging Face](https://huggingface.co/lmstudio-community/Qwen3-1.7B-GGUF)
- **File Name**: `Qwen3-1.7B-Q4_K_M.gguf`

**Characteristics**:
- Balanced size and capability
- Good general-purpose performance
- Moderate memory usage (~2-3GB RAM)
- Faster than larger models
- Better reasoning than 0.6B variant

**Best For**:
- General conversations
- Code assistance (simple)
- Mid-range devices
- Good balance of speed and quality

---

### Qwen 3 4B

- **Organization**: Alibaba Cloud
- **Parameters**: 4 billion
- **File Size**: 2.5 GB
- **Download**: [Hugging Face](https://huggingface.co/lmstudio-community/Qwen3-4B-GGUF)
- **File Name**: `Qwen3-4B-Q4_K_M.gguf`

**Characteristics**:
- Strong reasoning capabilities
- Better context understanding
- Higher memory requirement (~4-5GB RAM)
- Slower than smaller models
- More coherent long-form content

**Best For**:
- Complex conversations
- Code generation
- Creative writing
- High-end devices

---

### Gemma 3 1B

- **Organization**: Google
- **Parameters**: 1 billion
- **File Size**: 806 MB
- **Download**: [Hugging Face](https://huggingface.co/lmstudio-community/gemma-3-1b-it-GGUF)
- **File Name**: `gemma-3-1b-it-Q4_K_M.gguf`

**Characteristics**:
- Instruction-tuned (IT) variant
- Fast inference
- Efficient memory usage (~2GB RAM)
- Good at following instructions
- Latest Gemma generation

**Best For**:
- Instruction following
- Quick tasks
- Efficient operation
- Devices with moderate specs

---

### Gemma 3 4B

- **Organization**: Google
- **Parameters**: 4 billion
- **File Size**: 2.49 GB
- **Download**: [Hugging Face](https://huggingface.co/lmstudio-community/gemma-3-4b-it-GGUF)
- **File Name**: `gemma-3-4b-it-Q4_K_M.gguf`

**Characteristics**:
- Advanced instruction following
- Strong reasoning
- Memory requirement (~4-5GB RAM)
- High-quality responses
- Versatile capabilities

**Best For**:
- Complex instructions
- Multi-step reasoning
- Quality-focused use cases
- Devices with ample RAM

---

### Llama 3.2 1B

- **Organization**: Meta
- **Parameters**: 1 billion
- **File Size**: 808 MB
- **Download**: [Hugging Face](https://huggingface.co/lmstudio-community/Llama-3.2-1B-Instruct-GGUF)
- **File Name**: `Llama-3.2-1B-Instruct-Q4_K_M.gguf`

**Characteristics**:
- Latest Llama generation
- Instruction-optimized
- Fast and efficient
- Memory usage (~2GB RAM)
- Good language understanding

**Best For**:
- General purpose tasks
- Fast responses needed
- Llama ecosystem preference
- Moderate devices

---

### Llama 3.2 3B

- **Organization**: Meta
- **Parameters**: 3 billion
- **File Size**: 2.02 GB
- **Download**: [Hugging Face](https://huggingface.co/lmstudio-community/Llama-3.2-3B-Instruct-GGUF)
- **File Name**: `Llama-3.2-3B-Instruct-Q4_K_M.gguf`

**Characteristics**:
- Enhanced Llama 3.2 model
- Better reasoning than 1B
- Memory requirement (~3-4GB RAM)
- Good instruction following
- Balanced performance

**Best For**:
- Advanced conversations
- Better reasoning tasks
- Llama preference with more capability
- Modern smartphones

---

### Phi-4 Mini

- **Organization**: Microsoft
- **Parameters**: 3.8 billion
- **File Size**: 2.49 GB
- **Download**: [Hugging Face](https://huggingface.co/lmstudio-community/Phi-4-mini-instruct-GGUF)
- **File Name**: `Phi-4-mini-instruct-Q4_K_M.gguf`

**Characteristics**:
- Specialized small language model
- Efficient architecture
- Memory usage (~4GB RAM)
- Strong for its size
- Instruction-tuned

**Best For**:
- Code understanding
- Technical conversations
- Efficient high-quality inference
- Mid to high-end devices

---

### DeepSeek R1 Distill 1.5B

- **Organization**: DeepSeek AI
- **Parameters**: 1.5 billion (distilled from larger model)
- **File Size**: 1.12 GB
- **Download**: [Hugging Face](https://huggingface.co/lmstudio-community/DeepSeek-R1-Distill-Qwen-1.5B-GGUF)
- **File Name**: `DeepSeek-R1-Distill-Qwen-1.5B-Q4_K_M.gguf`

**Characteristics**:
- Distilled from larger DeepSeek model
- Knowledge from Qwen architecture
- Memory usage (~2-3GB RAM)
- Reasoning-focused
- Compact but capable

**Best For**:
- Reasoning tasks
- Efficient operation
- Devices with moderate specs
- Users wanting DeepSeek capabilities in smaller size

---

### DeepSeek R1 Distill 7B

- **Organization**: DeepSeek AI
- **Parameters**: 7 billion (distilled from larger model)
- **File Size**: 4.68 GB
- **Download**: [Hugging Face](https://huggingface.co/lmstudio-community/DeepSeek-R1-Distill-Qwen-7B-GGUF)
- **File Name**: `DeepSeek-R1-Distill-Qwen-7B-Q4_K_M.gguf`

**Characteristics**:
- Largest actively supported model
- Strong reasoning capabilities
- High memory requirement (~6-8GB RAM)
- Slower inference
- Best quality in collection

**Best For**:
- Complex reasoning tasks
- Code generation and analysis
- High-quality long-form content
- High-end devices and tablets only

## Obsolete Models

These models are from older generations and are hidden from new users. They remain available for users who already downloaded them.

**Note on naming**: Older models use different naming conventions (e.g., "Qwen2.5" without space) reflecting their original version numbers, while newer models use updated naming (e.g., "Qwen 3" with space) to match current upstream releases.

- **Qwen2.5 0.5B** - Superseded by Qwen 3 0.6B
- **Qwen2.5 1.5B** - Superseded by Qwen 3 1.7B
- **Llama3.2 1B** (GGML format, obsolete) – Superseded by **Llama 3.2 1B** (GGUF format, see Current Models)
- **Llama3.2 3B** (GGML format, obsolete) – Superseded by **Llama 3.2 3B** (GGUF format, see Current Models)
- **Phi3.5 Mini** - Superseded by Phi-4 Mini
- **Mistral 7B** - v0.3, no longer updated
- **Llama3.1 7B** - Superseded by later versions
- **Gemma2 9B** - Earlier generation

## Model Specifications

### Performance Comparison

| Model | Size | Params | Speed | Quality | RAM | Best Use Case |
|-------|------|--------|-------|---------|-----|---------------|
| Qwen 3 0.6B | 484MB | 0.6B | ⭐⭐⭐⭐⭐ | ⭐⭐ | ~2GB | Quick tasks |
| Gemma 3 1B | 806MB | 1B | ⭐⭐⭐⭐ | ⭐⭐⭐ | ~2GB | Instructions |
| Qwen 3 1.7B | 1.28GB | 1.7B | ⭐⭐⭐⭐ | ⭐⭐⭐ | ~3GB | General use |
| Llama 3.2 1B | 808MB | 1B | ⭐⭐⭐⭐ | ⭐⭐⭐ | ~2GB | General use |
| Llama 3.2 3B | 2.02GB | 3B | ⭐⭐⭐ | ⭐⭐⭐⭐ | ~4GB | Advanced tasks |
| Qwen 3 4B | 2.5GB | 4B | ⭐⭐⭐ | ⭐⭐⭐⭐ | ~5GB | Complex work |
| Gemma 3 4B | 2.49GB | 4B | ⭐⭐⭐ | ⭐⭐⭐⭐ | ~5GB | Complex work |
| Phi-4 Mini | 2.49GB | 3.8B | ⭐⭐⭐ | ⭐⭐⭐⭐ | ~4GB | Code & tech |
| DeepSeek 1.5B | 1.12GB | 1.5B | ⭐⭐⭐⭐ | ⭐⭐⭐ | ~3GB | Reasoning |
| DeepSeek 7B | 4.68GB | 7B | ⭐⭐ | ⭐⭐⭐⭐⭐ | ~8GB | Best quality |

### Device Recommendations

**Entry-Level Devices** (4GB RAM):
- Qwen 3 0.6B
- Gemma 3 1B
- Llama 3.2 1B

**Mid-Range Devices** (6-8GB RAM):
- Qwen 3 1.7B
- Llama 3.2 3B
- DeepSeek 1.5B
- Phi-4 Mini

**High-End Devices** (8GB+ RAM):
- Qwen 3 4B
- Gemma 3 4B
- DeepSeek 7B (12GB+ recommended)

**Tablets and Flagships** (12GB+ RAM):
- Any model
- Multiple models simultaneously (if needed)

## Choosing a Model

### Factors to Consider

1. **Device Specifications**:
   - Available RAM
   - Processor speed
   - Storage space
   - Battery capacity

2. **Use Case**:
   - Simple Q&A → Smaller models (0.6B-1.7B)
   - Code assistance → Phi-4 Mini, DeepSeek models
   - Creative writing → Larger models (4B+)
   - Long conversations → Models with good context handling

3. **Performance Preferences**:
   - Speed over quality → Smaller models
   - Quality over speed → Larger models
   - Balanced → 1.7B-3B range

4. **Organization Preference**:
   - Alibaba ecosystem → Qwen models
   - Google ecosystem → Gemma models
   - Meta ecosystem → Llama models
   - Microsoft → Phi models
   - DeepSeek AI → DeepSeek models

### Recommendations by Task

**Quick Questions & Simple Chat**:
- Qwen 3 0.6B, Gemma 3 1B, Llama 3.2 1B

**General Purpose Conversation**:
- Qwen 3 1.7B, Llama 3.2 3B

**Code & Technical Discussions**:
- Phi-4 Mini, DeepSeek 1.5B, DeepSeek 7B

**Creative Writing**:
- Qwen 3 4B, Gemma 3 4B, DeepSeek 7B

**Complex Reasoning**:
- DeepSeek 7B, Qwen 3 4B, Gemma 3 4B

## Technical Details

### Prompt Formatting

Most current models use automatic prompt formatting (empty prefix/suffix). Legacy models may use specific formats:

**Example - Qwen2.5 (obsolete)**:
```
<|im_start|>user
{user_message}<|im_end|>
<|im_start|>assistant
{assistant_response}<|im_end|>
```

**Example - Llama 3.1 (obsolete)**:
```
<|start_header_id|>user<|end_header_id|>

{user_message}<|eot_id|><|start_header_id|>assistant<|end_header_id|>

{assistant_response}<|eot_id|>
```

### Model Architecture

All models follow transformer architecture with variations:

- **Qwen**: Optimized for multilingual support
- **Gemma**: Google's efficient architecture
- **Llama**: Meta's decoder-only transformer
- **Phi**: Compact high-performance design
- **DeepSeek**: Reasoning-optimized architecture

### Inference Parameters

Models can be tuned with parameters:
- **Temperature**: Controls randomness (0.0-1.0)
- **Top-K**: Limits vocabulary per step
- **Top-P**: Nucleus sampling threshold
- **Repeat Penalty**: Prevents repetition
- **Context Length**: Maximum conversation history

*Note: Current app version uses default parameters.*

## Adding New Models

To add a new model to LM Playground:

1. **Find Model**: Locate GGUF Q4_K_M version on Hugging Face
2. **Verify Compatibility**: Test with llama.cpp
3. **Update Code**: Add entry to `ModelInfoProvider.kt`
4. **Test**: Download and verify functionality
5. **Document**: Update this file and README.md

**Required Information**:
- Model name and organization
- Parameter count
- File size
- Download URL
- Prompt format (if non-standard)
- Description

## Resources

- [Hugging Face](https://huggingface.co/) - Model repository
- [llama.cpp](https://github.com/ggerganov/llama.cpp) - Inference engine
- [GGUF Format Spec](https://github.com/ggerganov/ggml/blob/master/docs/gguf.md)
- [Quantization Guide](https://huggingface.co/docs/hub/gguf)

## Notes

- Model availability depends on internet connection for initial download
- Models are cached locally after first download
- Larger models provide better quality but slower inference
- Always check available RAM before selecting a model
- Model performance varies by device hardware
- Regular updates may introduce new models and retire old ones

For questions about specific models, please open an issue on GitHub.
