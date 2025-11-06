# Security Policy

## Supported Versions

We are currently supporting the following versions with security updates:

| Version | Supported          |
| ------- | ------------------ |
| 0.1.x   | :white_check_mark: |
| < 0.1   | :x:                |

## Reporting a Vulnerability

We take the security of LM Playground seriously. If you discover a security vulnerability, please follow these steps:

### Where to Report

**Do NOT** open a public GitHub issue for security vulnerabilities.

Instead, please report security vulnerabilities via:

1. **GitHub Security Advisories**: [Report a vulnerability](https://github.com/logic-arts-official/LMPlayground/security/advisories/new)
2. **Email**: Contact the project maintainers through GitHub profile contacts

### What to Include

When reporting a vulnerability, please include:

- **Description**: Clear description of the vulnerability
- **Impact**: Potential impact and severity
- **Steps to Reproduce**: Detailed steps to reproduce the issue
- **Affected Versions**: Which versions are affected
- **Suggested Fix**: If you have suggestions for fixing the issue
- **Environment**: Device model, Android version, app version
- **Proof of Concept**: Code snippets, screenshots, or logs (if applicable)

### Response Timeline

- **Acknowledgment**: We aim to acknowledge receipt within 48 hours
- **Initial Assessment**: Initial assessment within 5 business days
- **Status Updates**: Regular updates as we work on a fix
- **Resolution**: Timeline depends on severity and complexity
- **Disclosure**: Public disclosure after fix is released (coordinated with reporter)

## Security Considerations

### On-Device Processing

LM Playground processes all data on-device:

- ✅ No data is sent to external servers (except model downloads)
- ✅ Conversations stay on your device
- ✅ No telemetry or analytics
- ✅ Network permission only used for model downloads from Hugging Face

### Data Storage

- Models are stored in public Downloads directory
- App data follows Android security model
- No sensitive user data is collected or stored

### Model Sources

- All models are downloaded from Hugging Face
- Models are in GGUF format from trusted community conversions
- Users should verify model integrity if concerned

### Permissions

The app requests minimal permissions:

- **INTERNET**: For downloading models from Hugging Face
- **Storage**: For accessing downloaded model files

### Native Code Security

- Native code (C++) is compiled from llama.cpp
- llama.cpp is a well-audited open-source project
- JNI bridge follows secure coding practices
- No arbitrary code execution from user input

## Known Security Considerations

### Model Download Integrity

Currently, the app does not verify checksums of downloaded models. Users concerned about model integrity should:

1. Verify model checksums manually
2. Only use models from trusted sources
3. Check Hugging Face model cards for information

**Future Enhancement**: We plan to add checksum verification in a future release.

### Memory Safety

- The app uses native C++ code which requires careful memory management
- llama.cpp is well-tested for memory safety
- JNI reference management follows best practices

### Input Validation

- User inputs are processed by LLM models
- No server-side processing or injection risks
- Native layer sanitizes inputs before processing

## Best Practices for Users

### Device Security

- Keep your Android device updated
- Use device encryption
- Set up screen lock
- Only install apps from trusted sources

### Model Selection

- Only download models from official Hugging Face repositories
- Check model licenses and terms
- Be aware of model capabilities and limitations
- Larger models require more resources and may impact device performance

### Privacy

- Be mindful of what you type in conversations
- Models process data on-device, but device backups may include app data
- Clear app data to remove conversation history

## Scope

### In Scope

- Vulnerabilities in app code (Kotlin/Java)
- Security issues in native code (C++/JNI)
- Insecure data storage or transmission
- Authentication/authorization bypasses
- Injection vulnerabilities
- Memory safety issues
- Build and dependency security

### Out of Scope

- Issues in llama.cpp library (report to upstream: https://github.com/ggerganov/llama.cpp)
- Model behavior or outputs (not security vulnerabilities)
- Android OS vulnerabilities
- Third-party library vulnerabilities (unless we're using them insecurely)
- Social engineering attacks
- Physical device access attacks

## Security Updates

Security updates will be released as soon as possible after verification:

1. **Critical**: Within 24-48 hours
2. **High**: Within 1 week
3. **Medium**: Within 2-4 weeks
4. **Low**: In next regular release

Updates will be announced via:
- GitHub Security Advisories
- Release notes
- README updates

## Disclosure Policy

We follow **Coordinated Disclosure**:

1. Reporter notifies us privately
2. We acknowledge and investigate
3. We develop and test a fix
4. We release the fix
5. Public disclosure (typically 90 days after fix)
6. Credit to reporter (if desired)

## Third-Party Dependencies

We monitor dependencies for known vulnerabilities:

- Dependabot alerts for Gradle dependencies
- Regular updates to Android libraries
- Monitoring llama.cpp releases for security fixes
- NDK version updates as recommended by Google

## Build Security

- Source code is open and auditable
- Releases are built using GitHub Actions
- NDK and build tools are from official sources
- Gradle wrapper is from official Gradle distribution

## Contact

For security concerns:

- **Security Issues**: Use GitHub Security Advisories
- **General Questions**: Open a GitHub Issue (non-security)
- **Project Maintainers**: Via GitHub profile contacts

## Hall of Fame

We appreciate security researchers who responsibly disclose vulnerabilities. Contributors will be acknowledged here (with their permission):

*No reported vulnerabilities yet.*

## Additional Resources

- [Android Security Best Practices](https://developer.android.com/topic/security/best-practices)
- [OWASP Mobile Security](https://owasp.org/www-project-mobile-security/)
- [llama.cpp Security](https://github.com/ggerganov/llama.cpp/security)

---

**Last Updated**: 2025-11-06

This security policy may be updated periodically. Please check back for the latest version.
