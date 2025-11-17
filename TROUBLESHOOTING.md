# Troubleshooting Guide

This guide helps you solve common issues with LM Playground.

## Table of Contents

- [Installation Issues](#installation-issues)
- [Model Download Problems](#model-download-problems)
- [Model Loading Issues](#model-loading-issues)
- [Performance Problems](#performance-problems)
- [App Crashes](#app-crashes)
- [Conversation Issues](#conversation-issues)
- [Storage and Memory](#storage-and-memory)
- [Build and Development Issues](#build-and-development-issues)

## Installation Issues

### App Won't Install

**Problem**: Installation fails or is blocked

**Solutions**:

1. **Check Android Version**:
   - LM Playground requires Android 11 (API 30) or higher
   - Go to Settings → About Phone → Android version
   - If below Android 11, device is not supported

2. **Enable Unknown Sources** (if installing APK directly):
   - Settings → Security → Unknown sources
   - Or: Settings → Apps → Special access → Install unknown apps
   - Enable for your file manager or browser

3. **Check Storage Space**:
   - Need at least 100MB for app installation
   - Plus 2-5GB for models
   - Settings → Storage to check available space

4. **Check Architecture**:
   - App requires ARM64 (arm64-v8a) or x86_64
   - Most modern phones use ARM64
   - Very old phones may not be supported

### App Won't Open After Install

**Problem**: App crashes immediately on startup

**Solutions**:

1. **Restart Device**: Simple but often effective
2. **Clear App Data**:
   - Settings → Apps → LM Playground → Storage → Clear data
3. **Reinstall App**: Uninstall completely, then reinstall
4. **Check Logs**:
   - Enable developer options
   - Use `adb logcat` to see error messages

## Model Download Problems

### Download Fails or Stops

**Problem**: Model download doesn't complete

**Solutions**:

1. **Check Internet Connection**:
   - Ensure stable WiFi or mobile data
   - Test with other apps
   - Try different network if possible

2. **Check Storage Space**:
   - Need space for full model size:
     - Smallest: 484MB (Qwen 3 0.6B)
     - Largest: 4.68GB (DeepSeek 7B)
   - Check Settings → Storage

3. **Restart Download**:
   - Cancel current download
   - Close and reopen app
   - Try downloading again

4. **Check Download Manager**:
   - Some phones block downloads in background
   - Check notification bar for download status
   - Settings → Apps → Download Manager (ensure enabled)

5. **Try Different Model**:
   - Some Hugging Face servers may be temporarily slow
   - Try a different model first
   - Come back to original model later

### Download is Very Slow

**Problem**: Download takes hours or appears stuck

**Solutions**:

1. **Use WiFi**: Mobile data may be slow or have download limits
2. **Check Network Speed**: Test internet speed with speed test app
3. **Try Different Time**: Hugging Face servers may be busy, try off-peak hours
4. **Keep Screen On**: Some phones pause downloads when screen locks
   - Settings → Display → Screen timeout → Increase duration
5. **Disable Battery Optimization**:
   - Settings → Apps → LM Playground → Battery → Unrestricted

### Download Completes But Model Won't Load

**Problem**: Download shows 100% but model doesn't appear

**Solutions**:

1. **Check Downloads Folder**:
   - Open Files app → Downloads
   - Look for `.gguf` files
   - Ensure file size matches expected size

2. **Verify File Integrity**:
   - Files should be several hundred MB to several GB
   - If file is tiny (few KB), download was corrupted
   - Delete and re-download

3. **Check File Name**:
   - Should end in `.gguf`
   - Should match expected name (e.g., `Qwen3-0.6B-Q4_K_M.gguf`)
   - Some browsers add `.tmp` or other extensions - remove them

4. **Refresh Model List**:
   - Close and reopen app
   - App scans Downloads folder on startup

## Model Loading Issues

### "Failed to Load Model" Error

**Problem**: Model downloaded but won't load

**Solutions**:

1. **Check Available RAM**:
   - Close other apps to free memory
   - Restart device to clear RAM
   - Check Settings → Memory for available RAM
   - Minimum requirements:
     - 0.6B-1B models: 2GB RAM
     - 1.7B-3B models: 3-4GB RAM
     - 4B models: 5GB RAM
     - 7B models: 8GB RAM

2. **Try Smaller Model**:
   - If 4B model fails, try 1.7B
   - Device may not have enough RAM for larger models

3. **Restart App**:
   - Close app completely (swipe away from recent apps)
   - Reopen and try loading again

4. **Reinstall App**:
   - May fix corrupted native libraries
   - Models stay in Downloads, no need to re-download

5. **Check Model File**:
   - Ensure file isn't corrupted
   - Compare file size to expected size in MODELS.md
   - Re-download if size doesn't match

### Model Loading is Extremely Slow

**Problem**: Loading progress bar stuck at same percentage

**Solutions**:

1. **Be Patient**:
   - First load of large models can take 2-5 minutes
   - Normal for 4B+ models on mid-range devices
   - Loading happens once per session

2. **Check Progress**:
   - Progress bar should move, even if slowly
   - If truly stuck (no movement for 5+ minutes), there's a problem

3. **Free Up RAM**:
   - Close all background apps
   - Restart device if necessary

4. **Cool Down Device**:
   - If device is hot, let it cool down
   - Thermal throttling slows loading

5. **Force Close and Retry**:
   - If truly stuck, force close app
   - Try loading again

### Model Keeps Unloading

**Problem**: Model loaded successfully but then becomes unavailable

**Solutions**:

1. **Memory Pressure**:
   - Other apps may be forcing LM Playground to release memory
   - Close unnecessary apps
   - Restart device to clear memory

2. **Don't Switch Apps**:
   - Switching to other apps may cause Android to unload model
   - Stay in LM Playground while using it

3. **Disable Battery Optimization**:
   - Settings → Apps → LM Playground → Battery → Unrestricted
   - Prevents system from killing app in background

## Performance Problems

### Responses are Very Slow

**Problem**: AI takes forever to generate responses

**Solutions**:

1. **Expected Speed**:
   - Small models (0.6B-1B): 1-3 seconds per sentence
   - Medium models (1.7B-3B): 3-7 seconds per sentence  
   - Large models (4B-7B): 7-15+ seconds per sentence
   - This is normal for on-device AI

2. **Switch to Smaller Model**:
   - For faster responses, use Qwen 3 0.6B or Gemma 3 1B
   - Speed vs quality tradeoff

3. **Close Background Apps**:
   - Free up CPU and RAM
   - Check running apps in recent apps menu

4. **Cool Down Device**:
   - Thermal throttling slows processing
   - Remove phone case
   - Stop using phone until cool

5. **Charge Device**:
   - Battery saver mode slows CPU
   - Plug in or disable battery saver

6. **Restart App**:
   - Fresh start may improve performance
   - Clears memory leaks (if any)

### App is Using Too Much Battery

**Problem**: Battery drains quickly when using app

**Solutions**:

1. **Normal Behavior**:
   - Running AI models is CPU-intensive
   - High battery usage is expected during active use

2. **Use Smaller Models**:
   - Smaller models use less power
   - Switch to 0.6B or 1B models for battery saving

3. **Reduce Usage Time**:
   - Use in short sessions
   - Close app when not needed

4. **Enable Battery Saver** (when not using app):
   - But disable when actively using for better performance

5. **Check Battery Health**:
   - Old batteries drain faster
   - Consider battery replacement if device is old

### Phone Gets Very Hot

**Problem**: Device temperature increases significantly

**Solutions**:

1. **Normal During Use**:
   - AI inference generates heat
   - Brief warmth is normal

2. **Take Breaks**:
   - Don't use continuously for long periods
   - Let device cool between sessions

3. **Remove Case**:
   - Cases trap heat
   - Remove for better cooling

4. **Reduce Model Size**:
   - Smaller models generate less heat
   - Try 0.6B or 1B models

5. **Avoid Charging While Using**:
   - Charging + AI = more heat
   - Use on battery if possible

6. **If Extremely Hot**:
   - Close app immediately
   - Let device cool completely
   - May indicate hardware issue if happens repeatedly

## App Crashes

### App Crashes During Model Loading

**Problem**: App closes unexpectedly while loading model

**Solutions**:

1. **Out of Memory**:
   - Most common cause
   - Try smaller model
   - Close all other apps
   - Restart device

2. **Corrupted Model File**:
   - Delete model file from Downloads
   - Re-download from app

3. **Check Crash Logs**:
   - If you're technical: `adb logcat | grep LMPlayground`
   - Look for "OutOfMemoryError" or segmentation faults

4. **Report Bug**:
   - If crash persists, report on GitHub
   - Include: device model, RAM, Android version, model being loaded

### App Crashes During Conversation

**Problem**: App closes while generating response

**Solutions**:

1. **Memory Leak** (potential):
   - Restart app after several conversations
   - Report issue on GitHub with details

2. **Long Conversation**:
   - Very long conversations may exceed memory
   - Start new conversation if many exchanges

3. **Update App**:
   - Check for latest version
   - Bug may be fixed in update

4. **Clear App Data**:
   - Settings → Apps → LM Playground → Storage → Clear data
   - Will need to reload model

### Random Crashes

**Problem**: App crashes unpredictably

**Solutions**:

1. **Update Android**:
   - Ensure system is up to date
   - Settings → System → System update

2. **Clear Cache**:
   - Settings → Apps → LM Playground → Storage → Clear cache

3. **Reinstall App**:
   - Complete uninstall
   - Fresh install

4. **Check Device Storage**:
   - Very low storage can cause instability
   - Keep at least 1GB free

5. **Report with Logs**:
   - If persistent, report issue with:
     - Device model and specs
     - Android version
     - Steps to reproduce
     - Logcat output if possible

## Conversation Issues

### AI Responses Don't Make Sense

**Problem**: Generated text is gibberish or irrelevant

**Solutions**:

1. **Check Model Loaded Correctly**:
   - Reload model
   - Try different model to test

2. **Provide Better Context**:
   - Be more specific in questions
   - Give background information
   - Use complete sentences

3. **Model Limitations**:
   - Smaller models (0.6B) are less capable
   - Try larger model for better quality

4. **Corrupted Model**:
   - Re-download model file
   - Verify file size matches MODELS.md

### AI Keeps Repeating Itself

**Problem**: Response loops or repeats phrases

**Solutions**:

1. **Stop Generation**:
   - Currently requires closing app (future: stop button)

2. **Rephrase Question**:
   - Try asking differently
   - Be more specific

3. **Try Different Model**:
   - Some models handle certain prompts better
   - Switch to different model family

4. **Known Limitation**:
   - Current version doesn't expose repetition penalty
   - Future versions may add parameter controls

### No Response Generated

**Problem**: Send message but no AI response appears

**Solutions**:

1. **Check Model Loaded**:
   - Ensure model shows as loaded in top bar
   - Reload if necessary

2. **Wait Longer**:
   - First response of session may be slower
   - Large models can take 15+ seconds to start

3. **Check Input**:
   - Ensure message was sent (not just typed)
   - Look for your message in chat history

4. **Restart Generation**:
   - Close and reopen app
   - Reload model
   - Try again

### Can't Type New Message

**Problem**: Input field is disabled

**Solutions**:

1. **Generation in Progress**:
   - Wait for current response to complete
   - Input disabled during generation

2. **Model Not Ready**:
   - Ensure model is fully loaded
   - Check for progress bar

3. **App State Issue**:
   - Close and reopen app

## Storage and Memory

### "Storage Full" Error

**Problem**: Can't download models due to storage

**Solutions**:

1. **Free Up Space**:
   - Delete unused apps
   - Clear app caches
   - Move photos/videos to cloud
   - Delete old downloads

2. **Delete Unused Models**:
   - Check Downloads folder
   - Remove `.gguf` files you don't use
   - Each model is 0.5GB - 5GB

3. **Use External Storage** (if supported):
   - Some devices support SD cards
   - Move other files to SD card to free internal storage

4. **Check Storage Usage**:
   - Settings → Storage
   - See what's using space

### "Out of Memory" Error

**Problem**: App reports insufficient memory

**Solutions**:

1. **Free RAM**:
   - Close all other apps
   - Restart device
   - Don't multitask while using LM Playground

2. **Use Smaller Model**:
   - RAM requirements:
     - 0.6B: 2GB
     - 1B: 2GB
     - 1.7B: 3GB
     - 3B: 4GB
     - 4B: 5GB
     - 7B: 8GB+

3. **Check Available RAM**:
   - Settings → About Phone → Memory
   - Compare to model requirements

4. **Upgrade Device** (if persistent):
   - If device has less than 4GB total RAM
   - Consider device with 8GB+ RAM for better experience

## Build and Development Issues

*(For developers building from source)*

### Build Fails: "NDK not found"

**Solutions**:
1. Install NDK: `sdkmanager "ndk;27.2.12479018"`
2. Check `local.properties` has correct SDK path
3. Sync Gradle again

### Build Fails: "Submodule empty"

**Solutions**:
1. Clone with `--recursive` flag: `git clone --recursive ...`
2. Or initialize submodules: `git submodule update --init --recursive`

### Native Build Fails

**Solutions**:
1. Ensure NDK version matches build.gradle.kts
2. Check CMake is installed
3. Clean and rebuild: `./gradlew clean build`

### Tests Fail

**Solutions**:
1. Ensure device/emulator has sufficient RAM
2. Check AVD settings if using emulator
3. Some tests may require specific device features

For more development help, see [CONTRIBUTING.md](CONTRIBUTING.md).

## Still Having Issues?

### Before Reporting a Bug

1. **Search Existing Issues**: Check [GitHub Issues](https://github.com/logic-arts-official/LMPlayground/issues)
2. **Try Basic Troubleshooting**:
   - Restart app
   - Restart device  
   - Clear app data
   - Reinstall app

3. **Gather Information**:
   - Device model and manufacturer
   - Android version
   - RAM amount
   - Model trying to use
   - Exact error message (if any)
   - Steps to reproduce

### Reporting a Bug

When opening an issue, include:

```markdown
**Device**: [e.g., Samsung Galaxy S21]
**Android Version**: [e.g., Android 14]
**RAM**: [e.g., 8GB]
**App Version**: [e.g., 0.1.0]
**Model**: [e.g., Qwen 3 4B]

**Problem**: [Clear description]

**Steps to Reproduce**:
1. 
2. 
3. 

**Expected**: [What should happen]
**Actual**: [What actually happens]

**Logs**: [If available, paste logcat output]
```

### Getting Help

- **Technical Issues**: [GitHub Issues](https://github.com/logic-arts-official/LMPlayground/issues)
- **Usage Questions**: See [USER_GUIDE.md](USER_GUIDE.md)
- **Security Issues**: See [SECURITY.md](SECURITY.md)

## Quick Fixes Checklist

When something goes wrong, try these in order:

- [ ] Close and reopen app
- [ ] Clear app cache (Settings → Apps → LM Playground → Clear cache)
- [ ] Restart device
- [ ] Ensure sufficient storage space (2-5GB free)
- [ ] Close background apps to free RAM
- [ ] Try smaller model
- [ ] Check internet connection (for downloads)
- [ ] Clear app data (Settings → Apps → LM Playground → Clear data)
- [ ] Reinstall app
- [ ] Check for app updates

---

**Last Updated**: November 2025  
**For**: LM Playground v0.1.0

If this guide doesn't solve your problem, please [open an issue](https://github.com/logic-arts-official/LMPlayground/issues) with details!
