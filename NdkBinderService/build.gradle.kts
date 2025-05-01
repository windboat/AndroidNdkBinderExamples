plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.ndkbinderservice"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ndkbinderservice"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        externalNativeBuild {
            cmake {
                cppFlags("-std=c++17")
                arguments(
                    "-Daidl_src_dir=${project(":Common").projectDir.absolutePath}/src/main/cpp/aidl".replace("\\", "/"),
                    "-Dcommon_inc_dir=${project(":Common").projectDir.absolutePath}/src/main/cpp/includes".replace("\\", "/")
                )
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), 
                "proguard-rules.pro"
            )
        }
    }

    externalNativeBuild {
        cmake {
            path("src/main/cpp/CMakeLists.txt")
            version = "3.10.2"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(project(":Common"))
}
