import com.android.build.api.dsl.ManagedVirtualDevice
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // you can also alias this in your TOML, but for now use the literal:
    id("org.jetbrains.kotlin.plugin.compose")
}


android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.druk.lmplayground"

    defaultConfig {
        applicationId = "com.druk.lmplayground"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "0.1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        externalNativeBuild {
            cmake {
                arguments += "-DLLAMA_CURL=OFF"
                arguments += "-DLLAMA_BUILD_COMMON=ON"
                arguments += "-DGGML_LLAMAFILE=OFF"
                cppFlags += "-std=c++11"
            }
        }

        ndk {
            abiFilters += setOf("arm64-v8a", "x86_64")
        }
    }

    ndkVersion = "27.2.12479018"

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        // kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.version#.get()
    }

    kotlin {
        compilerOptions {
            // replace kotlinOptions.jvmTarget = "1.8"
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    signingConfigs {
        val userKeystore = File(System.getProperty("user.home"), ".android/keystore.jks")
        val localKeystore = rootProject.file("debug.keystore")
        val hasKeyInfo = userKeystore.exists()
        named("debug") {
            storeFile = if (hasKeyInfo) userKeystore else localKeystore
            storePassword = if (hasKeyInfo) System.getenv("STORE_PASSWORD") else "android"
            keyAlias = if (hasKeyInfo) System.getenv("LM_PLAYGROUND_KEY_ALIAS") else "androiddebugkey"
            keyPassword = if (hasKeyInfo) System.getenv("LM_PLAYGROUND_KEY_PASSWORD") else "android"
        }
    }

    buildTypes {
        getByName("debug") {
            isJniDebuggable = false
        }

        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }


    splits {
        abi {
            isEnable = true
            reset()
            include("arm64-v8a", "x86_64")
            isUniversalApk = true
        }
    }

    testOptions {
        managedDevices {
            allDevices {
                register<ManagedVirtualDevice>("mvdApi35") {
                    device = "Pixel"
                    apiLevel = 35
                    systemImageSource = "google"
                    require64Bit = true
                }
            }
        }
    }

    packaging.resources {
        excludes += "/META-INF/AL2.0"
        excludes += "/META-INF/LGPL2.1"
    }
    dependenciesInfo {
        includeInApk = true
        includeInBundle = true
    }
}

// Align Kotlin JVM target using compilerOptions DSL
kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

// Dependencies for Material3, Compose3, and Navigation

dependencies {
    implementation(libs.google.android.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.fragment.ktx.v260)
    implementation(libs.androidx.navigation.ui.ktx.v260)

    // 1. Use the BOM the right way (platform(...) or enforcedPlatform(...))
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.bom)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.materialWindow)

    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")        // only needed in debug
    implementation("androidx.compose.ui:ui-viewbinding")
    implementation("androidx.compose.runtime:runtime-livedata")

    // Unit‑test support
    testImplementation("junit:junit:4.13.2")

    // Compose + AndroidX instrumentation test stack
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.11.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")            // provides createAndroidComposeRule
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")

    // Manifest stub so Compose previews & test‑manifest resolve correctly
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
