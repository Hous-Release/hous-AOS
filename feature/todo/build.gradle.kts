@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
}

tasks.withType<Test> {
    useJUnitPlatform()
}

android {
    namespace = "hous.release.feature.todo"
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] =
            "de.mannodermaus.junit5.AndroidJUnit5Builder"
        consumerProguardFiles("consumer-rules.pro")
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
    java {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlinOptions {
        jvmTarget = jvmVersion
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.kotlinCompilerExtensionVersion
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":designsystem"))
    implementation(project(":testing"))

    Deps.AndroidX.Compose.run {
        implementation(activity)
        implementation(material)
        implementation(animations)
        implementation(tool)
        implementation(viewModel)
        implementation(mdcTheme)
        implementation(appCompatTheme)
        implementation(lifecycle)
    }

    Deps.Coroutines.run {
        implementation(core)
        implementation(android)
    }

    Deps.DI.run {
        implementation(hilt)
        kapt(hiltKapt)
    }

    Deps.ThirdParty.run {
        implementation(timber)
    }

    testImplementation()
    androidTestImplementation()
}

ktlint {
    android.set(true)
    coloredOutput.set(true)
    verbose.set(true)
    outputToConsole.set(true)
    disabledRules.set(setOf("max-line-length", "no-wildcard-imports", "import-ordering"))
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}
