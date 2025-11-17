# Deployment Guide

This document describes the release and deployment process for LM Playground, intended for project maintainers and product owners.

## Table of Contents

- [Release Process Overview](#release-process-overview)
- [Version Management](#version-management)
- [Pre-Release Checklist](#pre-release-checklist)
- [Build and Release](#build-and-release)
- [Distribution Channels](#distribution-channels)
- [Post-Release Activities](#post-release-activities)
- [Rollback Procedures](#rollback-procedures)

## Release Process Overview

### Release Cycle

LM Playground follows a flexible release schedule:

- **Major Releases (X.0.0)**: Significant architectural changes or major features
- **Minor Releases (0.X.0)**: New models, features, or notable improvements
- **Patch Releases (0.0.X)**: Bug fixes, documentation updates, security patches

### Typical Release Timeline

```
Week 1-2: Development
Week 3: Feature freeze, testing
Week 4: Release candidate testing
Week 5: Final release and deployment
```

For critical security fixes, this timeline may be accelerated.

## Version Management

### Semantic Versioning

LM Playground follows [Semantic Versioning 2.0.0](https://semver.org/):

- **MAJOR**: Incompatible API changes, major architectural shifts
- **MINOR**: Backward-compatible new features, model additions
- **PATCH**: Backward-compatible bug fixes, documentation

### Version Configuration

**build.gradle.kts**:
```kotlin
android {
    defaultConfig {
        applicationId = "com.druk.lmplayground"
        minSdk = 30
        targetSdk = 35
        versionCode = 1  // Increment for each release
        versionName = "0.1.0"  // Semantic version
    }
}
```

**Version Code Strategy**:
- Increment by 1 for each release (1, 2, 3, ...)
- Used by Google Play to determine newer versions
- Must always increase

**Version Name Strategy**:
- User-facing version string
- Format: MAJOR.MINOR.PATCH
- Example: 0.1.0, 0.1.1, 0.2.0, 1.0.0

### Git Tagging

Tag each release:
```bash
git tag -a v0.1.0 -m "Release version 0.1.0"
git push origin v0.1.0
```

## Pre-Release Checklist

### Code Quality

- [ ] All tests passing (`./gradlew test`)
- [ ] Instrumented tests passing (`./gradlew connectedAndroidTest`)
- [ ] No critical TODOs remaining
- [ ] Code reviewed and approved
- [ ] Lint warnings addressed (`./gradlew lint`)
- [ ] Security scan completed (no critical issues)

### Documentation

- [ ] README.md updated with new features
- [ ] CHANGELOG.md updated with release notes
- [ ] MODELS.md updated if models changed
- [ ] API_REFERENCE.md updated if API changed
- [ ] Version numbers updated everywhere

### Feature Verification

- [ ] All new features tested on multiple devices
- [ ] Model downloads working from Hugging Face
- [ ] Model loading tested for all supported models
- [ ] Conversation functionality working correctly
- [ ] UI/UX reviewed and approved
- [ ] Performance benchmarked (no regressions)

### Compatibility Testing

**Device Testing**:
- [ ] Tested on Android 11 (minimum supported)
- [ ] Tested on Android 14/15 (latest versions)
- [ ] Tested on low-end device (4GB RAM)
- [ ] Tested on mid-range device (6-8GB RAM)
- [ ] Tested on high-end device (12GB+ RAM)
- [ ] Tested on tablet (if available)

**Architecture Testing**:
- [ ] Tested on ARM64 device (primary)
- [ ] Tested on x86_64 emulator (secondary)

**Manufacturer Testing** (if possible):
- [ ] Samsung device
- [ ] Google Pixel device
- [ ] At least one other manufacturer (OnePlus, Xiaomi, etc.)

### Build Verification

- [ ] Release build completes successfully
- [ ] APK size reasonable (< 50MB for app, models separate)
- [ ] ProGuard/R8 not breaking functionality
- [ ] Native libraries included correctly (arm64-v8a, x86_64)
- [ ] APK installs and runs on test devices

### Legal and Compliance

- [ ] License information up to date (MIT)
- [ ] Third-party licenses documented
- [ ] Attribution for llama.cpp and models
- [ ] No proprietary or unlicensed code
- [ ] Privacy policy reviewed (if applicable)
- [ ] SECURITY.md up to date

## Build and Release

### Creating a Release Build

**1. Update Version**

Edit `app/build.gradle.kts`:
```kotlin
versionCode = 2  // Increment from 1
versionName = "0.1.1"  // Update version
```

**2. Update Changelog**

Edit `CHANGELOG.md`:
```markdown
## [0.1.1] - 2025-11-XX

### Added
- New feature X

### Fixed
- Bug Y
- Issue Z

### Changed
- Improvement A
```

**3. Build Release APK**

```bash
# Clean previous builds
./gradlew clean

# Build release APK
./gradlew assembleRelease

# Output location:
# app/build/outputs/apk/release/app-release.apk
```

**4. Test Release Build**

```bash
# Install on test device
adb install app/build/outputs/apk/release/app-release.apk

# Verify functionality:
# - App launches
# - Model downloads work
# - Model loads correctly
# - Conversation works
# - No crashes
```

**5. Create Git Tag**

```bash
git add .
git commit -m "Release version 0.1.1"
git tag -a v0.1.1 -m "Release version 0.1.1"
git push origin main
git push origin v0.1.1
```

### Signing the APK

For Play Store distribution, APK must be signed.

**Generate Signing Key** (first time only):
```bash
keytool -genkey -v -keystore lmplayground-release.keystore \
  -alias lmplayground -keyalg RSA -keysize 2048 -validity 10000
```

**Configure Signing in build.gradle.kts**:
```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("../lmplayground-release.keystore")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = "lmplayground"
            keyPassword = System.getenv("KEY_PASSWORD")
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

**Build Signed APK**:
```bash
# Set environment variables
export KEYSTORE_PASSWORD="your_password"
export KEY_PASSWORD="your_password"

# Build signed APK
./gradlew assembleRelease
```

**Security Note**: 
- Never commit keystore files to Git
- Store passwords securely (environment variables, secrets manager)
- Back up keystore file securely - losing it prevents app updates

## Distribution Channels

### GitHub Releases

**Creating a Release**:

1. Go to https://github.com/logic-arts-official/LMPlayground/releases
2. Click "Draft a new release"
3. Fill in:
   - **Tag**: v0.1.1
   - **Title**: LM Playground v0.1.1
   - **Description**: Copy from CHANGELOG.md
4. Upload APK file
5. Publish release

**Release Notes Template**:
```markdown
## LM Playground v0.1.1

### What's New
- Feature A
- Feature B

### Improvements
- Performance enhancement X
- UI/UX improvement Y

### Bug Fixes
- Fixed issue Z

### Installation
Download `app-release.apk` and install on your Android device.

**Requirements**: Android 11+, 4GB RAM minimum

### Supported Models
See [MODELS.md](MODELS.md) for complete list.

**Full Changelog**: https://github.com/logic-arts-official/LMPlayground/blob/main/CHANGELOG.md
```

### Google Play Store

**Initial Setup**:
1. Create Google Play Console account
2. Create new app listing
3. Complete store listing (title, description, screenshots, icon)
4. Set up pricing (free)
5. Configure content rating
6. Complete privacy policy

**Store Listing Content**:

**Title**: LM Playground

**Short Description** (80 chars):
```
Run AI language models on your device. Private, fast, offline-capable.
```

**Full Description** (4000 chars):
```
LM Playground: On-Device AI Language Models

Run powerful AI language models directly on your Android device with complete privacy and no internet required (after model download).

FEATURES
• 10+ state-of-the-art AI models
• On-device inference - no servers, complete privacy
• Works offline after model download
• Interactive chat with streaming responses
• Modern Material 3 design
• Free and open source

SUPPORTED MODELS
Choose from models by leading AI organizations:
• Qwen (Alibaba Cloud) - 0.6B to 4B parameters
• Gemma (Google) - 1B to 4B parameters  
• Llama (Meta) - 1B to 3B parameters
• Phi-4 (Microsoft) - 3.8B parameters
• DeepSeek (DeepSeek AI) - 1.5B to 7B parameters

REQUIREMENTS
• Android 11 or newer
• 4GB RAM minimum (8GB recommended)
• 2-5GB storage for models
• ARM64 or x86_64 architecture

PRIVACY FOCUSED
All AI processing happens on your device. No data sent to servers. No tracking. No analytics.

Open source: https://github.com/logic-arts-official/LMPlayground
```

**Screenshots**:
- Main chat interface
- Model selection dialog
- Conversation example
- Model loading screen
- Multiple device sizes (phone, tablet)

**Upload Process**:
1. Go to Release → Production
2. Create new release
3. Upload signed APK or AAB
4. Add release notes
5. Review and roll out

**Release Timeline**:
- Upload: Immediate
- Review: 1-7 days typically
- Available: After approval

### F-Droid

For F-Droid inclusion:

1. Fork https://gitlab.com/fdroid/fdroiddata
2. Add app metadata
3. Submit merge request
4. Wait for review and approval

**Advantages**:
- Trusted by privacy-conscious users
- Automatic updates
- Free distribution
- Open source aligned

## Post-Release Activities

### Monitoring

**First 24 Hours**:
- [ ] Monitor GitHub issues for bug reports
- [ ] Check Play Console for crash reports
- [ ] Verify download metrics
- [ ] Test on additional devices if issues reported

**First Week**:
- [ ] Collect user feedback
- [ ] Address critical issues immediately
- [ ] Plan hotfix release if needed
- [ ] Update documentation based on user questions

### Communication

**Announce Release**:
- [ ] GitHub Discussions post
- [ ] Update README.md with latest version info
- [ ] Social media (if applicable)
- [ ] Community channels

**Template**:
```markdown
🚀 LM Playground v0.1.1 Released!

What's new:
• Feature A
• Feature B
• Bug fixes

Download: [GitHub Releases](link)
Play Store: [Coming soon/Available now]

Full details: [CHANGELOG.md](link)
```

### Metrics Tracking

**Key Metrics**:
- Download count (GitHub/Play Store)
- Active installations (Play Store)
- Crash rate (Play Console)
- User ratings (Play Store)
- Issue reports (GitHub)

**Success Criteria**:
- Crash-free rate > 99%
- Average rating > 4.0 stars
- No critical bugs in first week

### Feedback Collection

**Sources**:
- GitHub Issues
- Play Store reviews
- GitHub Discussions
- Direct user feedback

**Process**:
1. Triage: Critical, High, Medium, Low
2. Prioritize for next release
3. Respond to user feedback
4. Plan improvements

## Rollback Procedures

### When to Rollback

Consider rollback if:
- Critical crash affecting > 10% of users
- Data loss or corruption
- Security vulnerability introduced
- Major feature completely broken
- Unpublishable issue (legal, compliance)

### GitHub Rollback

```bash
# Revert to previous tag
git checkout v0.1.0

# Create hotfix branch
git checkout -b hotfix/rollback-0.1.1

# Push previous version
git push origin hotfix/rollback-0.1.1

# Create new release with previous APK
# Mark v0.1.1 as deprecated in release notes
```

### Play Store Rollback

**Halt Rollout**:
1. Go to Release → Production
2. Click "Halt rollout"
3. Only affects users who haven't received update yet

**Full Rollback**:
Not directly possible. Instead:
1. Quickly release previous version as new version
2. Increment version code
3. Use previous version's APK
4. Expedite review if possible

**Communication**:
```markdown
We've identified a critical issue in v0.1.1 and have reverted to v0.1.0. 
Users on v0.1.1 should reinstall or wait for v0.1.2 with the fix.
We apologize for the inconvenience.
```

### Hotfix Process

For critical issues:

1. **Immediate**:
   - Halt rollout if possible
   - Document issue
   - Notify users

2. **Fix** (within 24 hours):
   - Create hotfix branch
   - Implement minimal fix
   - Test thoroughly
   - Fast-track review

3. **Release** (ASAP):
   - Version: 0.1.2 (next patch)
   - Expedited testing
   - Immediate deployment
   - Clear communication

## Release Checklist Summary

### Pre-Release
- [ ] Code quality verified
- [ ] Tests passing
- [ ] Documentation updated
- [ ] Compatibility tested
- [ ] Build verified
- [ ] Version numbers updated

### Release
- [ ] Release build created
- [ ] APK tested
- [ ] Git tagged
- [ ] GitHub release published
- [ ] Play Store updated (when ready)

### Post-Release
- [ ] Monitoring active
- [ ] Communication sent
- [ ] Metrics tracked
- [ ] Feedback collected
- [ ] Next version planned

## Resources

- [Android Publishing Guide](https://developer.android.com/studio/publish)
- [Google Play Console](https://play.google.com/console)
- [Semantic Versioning](https://semver.org/)
- [Keep a Changelog](https://keepachangelog.com/)

## Contact

For release management questions:
- **Technical**: Open GitHub issue
- **Business/Legal**: Contact maintainers via GitHub profiles

---

**Last Updated**: November 2025  
**For**: LM Playground Release Management

This guide should be reviewed and updated with each major release.
