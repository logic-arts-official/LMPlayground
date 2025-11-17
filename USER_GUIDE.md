# LM Playground User Guide

Welcome to LM Playground! This guide will help you get started with running AI language models on your Android device.

## Table of Contents

- [Quick Start](#quick-start)
- [Understanding Models](#understanding-models)
- [First Time Setup](#first-time-setup)
- [Using the App](#using-the-app)
- [Tips and Best Practices](#tips-and-best-practices)
- [Understanding Performance](#understanding-performance)

## Quick Start

### What is LM Playground?

LM Playground is an Android app that lets you run AI language models directly on your phone or tablet - no internet required after downloading a model. Think of it as having ChatGPT running locally on your device with complete privacy.

### What You'll Need

- Android 11 or newer
- At least 4GB of RAM (8GB recommended)
- 2-5GB of free storage (depending on model size)
- Internet connection (only for initial model download)

### Your First 5 Minutes

1. **Install the app** on your Android device
2. **Open LM Playground** - you'll see an empty chat screen
3. **Tap the model selector** at the top (shows "No model selected")
4. **Choose a model** - we recommend starting with "Qwen 3 0.6B" (smallest, fastest)
5. **Tap Download** - the model will download (484MB for Qwen 3 0.6B)
6. **Wait for loading** - after download, the model loads automatically (progress bar shows status)
7. **Start chatting!** - type your first message and press send

## Understanding Models

### What is a Language Model?

A language model is an AI that can:
- Answer questions
- Have conversations
- Help with writing
- Assist with code
- Explain concepts
- And much more!

### Which Model Should I Choose?

Think of models like car engines - bigger is more powerful but uses more resources:

**For Beginners: Start Small**
- **Qwen 3 0.6B** (484MB) - Fastest, works on any device
- **Gemma 3 1B** (806MB) - Good balance of speed and quality
- **Llama 3.2 1B** (808MB) - Solid all-around choice

**For Better Quality: Medium Models**
- **Qwen 3 1.7B** (1.28GB) - Better responses, still quite fast
- **Llama 3.2 3B** (2.02GB) - Good for complex questions

**For Best Quality: Large Models** (Requires 8GB+ RAM)
- **Qwen 3 4B** (2.5GB) - High quality responses
- **Gemma 3 4B** (2.49GB) - Excellent instruction following
- **DeepSeek 7B** (4.68GB) - Best quality, slowest speed

### Model Comparison at a Glance

| If you want... | Choose this model |
|----------------|-------------------|
| Fastest responses | Qwen 3 0.6B or Gemma 3 1B |
| Best quality | DeepSeek R1 Distill 7B |
| Code help | Phi-4 Mini or DeepSeek models |
| Balanced performance | Qwen 3 1.7B or Llama 3.2 3B |
| For older phones (4GB RAM) | Qwen 3 0.6B, Gemma 3 1B, or Llama 3.2 1B |

## First Time Setup

### Step 1: Download Your First Model

1. Open the app
2. Tap the **model selector** at the top of the screen
3. Browse the list of available models
4. Tap your chosen model
5. Tap the **Download** button
6. Wait for the download to complete (shown in your notification bar)

**Tip**: Downloads happen in the background. You can close the app and the download continues.

### Step 2: Wait for Model Loading

After download completes:
- The app automatically starts loading the model
- You'll see a progress bar showing loading status
- First load takes 30 seconds to 2 minutes depending on model size
- The model stays loaded until you switch to a different model

**What's happening?**: The app is reading the model file and preparing it for use. This only happens once per session.

### Step 3: Start Chatting!

Once the model is loaded:
- The progress bar disappears
- The text input field becomes active
- You can type your first message
- Press the send button (or Enter on keyboard)
- Watch the AI response appear word-by-word

## Using the App

### The Main Screen

```
┌─────────────────────────────────┐
│  [Model Name]          [≡ Menu] │  ← Top Bar
├─────────────────────────────────┤
│                                 │
│  Chat messages appear here      │  ← Conversation Area
│                                 │
│  [Progress bar if loading]      │
│                                 │
├─────────────────────────────────┤
│  Type your message...  [Send]   │  ← Input Area
└─────────────────────────────────┘
```

### Selecting and Switching Models

**To change models:**
1. Tap the model name at the top
2. Select a different model from the list
3. If not downloaded, tap Download first
4. If downloaded, tap the model to load it
5. Wait for loading to complete

**Note**: Switching models clears your conversation history and requires reloading.

### Having a Conversation

**Sending Messages:**
- Type your message in the text box at the bottom
- Press the send button (paper airplane icon)
- Or press Enter on your keyboard

**Receiving Responses:**
- AI responses appear token-by-token (streaming)
- This creates a natural "typing" effect
- Wait for the full response before sending another message

**Stopping Generation:**
- If the response is too long, you can stop it
- Currently, stopping requires closing and reopening the app
- (Future versions will have a stop button)

### Understanding the Interface

**Top Bar Icons:**
- Model name: Tap to switch models
- Menu (≡): Future features

**Conversation Area:**
- Your messages appear on the right with blue background
- AI messages appear on the left with gray background
- Scroll up to see older messages

**Progress Indicators:**
- Circular progress during model loading
- Shows percentage complete
- Disappears when model is ready

### Downloading More Models

You can download multiple models:
1. Each model only needs to be downloaded once
2. Downloaded models are stored in your Downloads folder
3. You can switch between downloaded models anytime
4. To delete a model, remove it from your Downloads folder

## Tips and Best Practices

### Getting the Best Responses

**Be Specific**
- ❌ "Tell me about cars"
- ✅ "What are the main differences between electric and gas cars?"

**Provide Context**
- ❌ "How do I fix it?"
- ✅ "I'm trying to build a mobile app in Kotlin. How do I fix a null pointer exception?"

**Ask Follow-up Questions**
- Models remember the conversation context
- You can ask "Can you explain that in simpler terms?"
- Or "Give me an example of that"

### Performance Tips

**For Faster Responses:**
1. Use smaller models (0.6B - 1.7B)
2. Close other apps to free up RAM
3. Keep messages reasonably short
4. Plug in your device if battery is low

**For Better Quality:**
1. Use larger models (4B - 7B)
2. Be patient with slower generation
3. Provide more context in your questions
4. Ensure device has adequate cooling

**Battery Saving:**
- Smaller models use less power
- Don't leave model loaded when not using the app
- Consider using battery saver mode

### Storage Management

**Models are stored in Downloads:**
- Location: `/storage/emulated/0/Download/`
- Files are named like `Qwen3-0.6B-Q4_K_M.gguf`
- To free space, delete models you don't use
- You can re-download anytime

**Storage Usage:**
- Smallest model: 484MB (Qwen 3 0.6B)
- Typical model: 1-3GB
- Largest model: 4.68GB (DeepSeek 7B)
- Plan for 2-5GB if you want multiple models

### Privacy and Security

**Your Data Stays on Your Device:**
- All conversations happen locally
- No data is sent to servers (except model downloads)
- No account required
- No tracking or analytics

**Model Downloads:**
- Downloaded from Hugging Face (trusted source)
- Use WiFi for large downloads to save mobile data
- Downloads are standard GGUF format files

**Conversation History:**
- Currently not saved between sessions
- Closing the app clears conversation
- Future updates may add history saving

## Understanding Performance

### Why is it slow?

AI language models are computationally intensive:
- Larger models require more processing
- Mobile devices have limited resources compared to servers
- It's normal for responses to take 5-30 seconds
- Word-by-word generation is intentional for better UX

### Response Speed by Model

**Fast (1-3 seconds per sentence):**
- Qwen 3 0.6B
- Gemma 3 1B
- Llama 3.2 1B

**Medium (3-7 seconds per sentence):**
- Qwen 3 1.7B
- Llama 3.2 3B
- DeepSeek 1.5B

**Slower (7-15 seconds per sentence):**
- Qwen 3 4B
- Gemma 3 4B
- Phi-4 Mini

**Slowest (15+ seconds per sentence):**
- DeepSeek 7B

### What Affects Performance?

**Device Factors:**
- Processor speed (newer = faster)
- Available RAM (more = better)
- Thermal throttling (heat slows down processing)
- Background apps (close them for better performance)

**Model Factors:**
- Model size (larger = slower but better quality)
- Quantization (Q4_K_M is a good balance)
- Context length (longer conversations may slow down)

### Optimizing Your Experience

**For Daily Use:**
- Start with Qwen 3 1.7B or Llama 3.2 3B
- Good balance of speed and quality
- Works well on most modern phones

**For Quick Tasks:**
- Use Qwen 3 0.6B or Gemma 3 1B
- Fast responses for simple questions
- Great for testing and learning

**For Important Work:**
- Use Qwen 3 4B, Gemma 3 4B, or DeepSeek 7B
- Accept slower speed for better quality
- Best for code, writing, complex reasoning

## Frequently Asked Questions

### Can I use this offline?
Yes! After downloading a model, the app works completely offline. No internet required for conversations.

### How much data do downloads use?
Models range from 484MB to 4.68GB. Use WiFi to avoid mobile data charges.

### Can I have multiple models?
Yes! Download as many as you want. You can only use one at a time, but switching is easy.

### Does this send my conversations anywhere?
No. Everything runs on your device. Complete privacy.

### Can I use this on a tablet?
Yes! Tablets often have more RAM and can run larger models better than phones.

### Why does my phone get warm?
Running AI models is intensive work. This is normal. Take breaks if your device gets very hot.

### Can I delete conversations?
Currently, conversations clear when you close the app. Future versions may add history saving.

### Which model is best for coding?
Phi-4 Mini and DeepSeek models are optimized for code. For faster code help, try Qwen 3 4B.

### Can I use this for homework?
Yes, but remember:
- AI can make mistakes - always verify important information
- Use it as a learning tool, not a replacement for learning
- Some schools have policies about AI use - check with your teachers

### Is this really free?
Yes! The app and all models are free to use. Open source MIT license.

## Need More Help?

- **Technical Issues**: See [TROUBLESHOOTING.md](TROUBLESHOOTING.md)
- **Bug Reports**: [GitHub Issues](https://github.com/logic-arts-official/LMPlayground/issues)
- **Model Details**: [MODELS.md](MODELS.md)
- **Contributing**: [CONTRIBUTING.md](CONTRIBUTING.md)

## Appendix: Model Details Quick Reference

| Model | Size | Best For | Speed | Quality | RAM Needed |
|-------|------|----------|-------|---------|------------|
| Qwen 3 0.6B | 484MB | Learning, quick tasks | ⭐⭐⭐⭐⭐ | ⭐⭐ | 2GB |
| Gemma 3 1B | 806MB | Instructions, general use | ⭐⭐⭐⭐ | ⭐⭐⭐ | 2GB |
| Llama 3.2 1B | 808MB | General conversations | ⭐⭐⭐⭐ | ⭐⭐⭐ | 2GB |
| Qwen 3 1.7B | 1.28GB | Balanced performance | ⭐⭐⭐⭐ | ⭐⭐⭐ | 3GB |
| DeepSeek 1.5B | 1.12GB | Reasoning tasks | ⭐⭐⭐⭐ | ⭐⭐⭐ | 3GB |
| Llama 3.2 3B | 2.02GB | Advanced conversations | ⭐⭐⭐ | ⭐⭐⭐⭐ | 4GB |
| Phi-4 Mini | 2.49GB | Code & technical | ⭐⭐⭐ | ⭐⭐⭐⭐ | 4GB |
| Qwen 3 4B | 2.5GB | High quality | ⭐⭐⭐ | ⭐⭐⭐⭐ | 5GB |
| Gemma 3 4B | 2.49GB | Complex instructions | ⭐⭐⭐ | ⭐⭐⭐⭐ | 5GB |
| DeepSeek 7B | 4.68GB | Best quality, code | ⭐⭐ | ⭐⭐⭐⭐⭐ | 8GB |

---

**Version**: 0.1.0  
**Last Updated**: November 2025  
**For**: LM Playground Android App

Enjoy your AI-powered conversations! 🚀
