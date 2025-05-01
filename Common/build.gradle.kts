plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.common"
    compileSdk = 35

    defaultConfig {
        minSdk = 29
        //targetSdk = 35
        //versionCode = 1
        //versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        aidl = true
    }
}

dependencies {
    // Add any dependencies if needed
}

// AIDL NDK Compilation Task
tasks.create("compileAidlNdk") {
    doLast {
        val aidl = listOf(
            android.sdkDirectory.absolutePath, 
            "build-tools", 
            android.buildToolsVersion, 
            "aidl"
        ).joinToString(File.separator)

        val outDir = listOf(
            projectDir.absolutePath, 
            "src", 
            "main", 
            "cpp", 
            "aidl"
        ).joinToString(File.separator)

        val headerOutDir = listOf(
            projectDir.absolutePath, 
            "src", 
            "main", 
            "cpp", 
            "includes"
        ).joinToString(File.separator)

        val searchPathForImports = listOf(
            projectDir.absolutePath, 
            "src", 
            "main", 
            "aidl"
        ).joinToString(File.separator)

        val aidlFile = listOf(
            projectDir.absolutePath, 
            "src", 
            "main", 
            "aidl",
            "com", 
            "example", 
            "IMyService.aidl"
        ).joinToString(File.separator)

        project.exec {
            executable = aidl
            args(
                "--lang=ndk", 
                "-o", outDir, 
                "-h", headerOutDir, 
                "-I", searchPathForImports, 
                aidlFile
            )
        }
    }
}

// Clean task to remove generated files
tasks.named("clean") {
    doLast {
        val aidlCppOutDir = listOf(
            projectDir.absolutePath, 
            "src", 
            "main", 
            "cpp", 
            "aidl"
        ).joinToString(File.separator)

        val aidlCppHeaderOutDir = listOf(
            projectDir.absolutePath, 
            "src", 
            "main", 
            "cpp", 
            "includes",
            "aidl"
        ).joinToString(File.separator)

        delete(aidlCppOutDir)
        delete(aidlCppHeaderOutDir)
    }
}

afterEvaluate {
    tasks.named("preBuild") {
        dependsOn("compileAidlNdk")
    }
}
