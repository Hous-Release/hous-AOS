plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
}

android {
    compileSdk = AppConfig.compileSdkVersion
    buildToolsVersion = AppConfig.buildToolsVersion

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    lint {
        baseline = file("lint-baseline.xml")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    Deps.AndroidX.run {
        implementation(core)
        implementation(appcompat)
        implementation(material)
        implementation(constraintLayout)
        implementation(recyclerview)
        implementation(activityKTX)
        implementation(lifecycleKTX)
        implementation(fragmentKTX)
    }

    Deps.AndroidX.Compose.run {
        implementation(activity)
        implementation(material)
        implementation(animations)
        implementation(tool)
        implementation(viewModel)
        implementation(mdcTheme)
        implementation(appCompatTheme)
    }

    Deps.DI.run {
        implementation(hilt)
        kapt(hiltKapt)
    }

    Deps.Coroutines.run {
        implementation(core)
        implementation(android)
    }

    Deps.Network.run {
        implementation(interceptor)
        implementation(gson)
        implementation(retrofit2)
        implementation(retrofit2Converter)
    }

    Deps.ThirdParty.run {
        implementation(timber)
        implementation(lottie)
    }

    Deps.ThirdParty.Glide.run {
        implementation(glide)
        implementation(kapt)
    }

    Deps.Test.run {
        testImplementation(junit)
        androidTestImplementation(androidTest)
        androidTestImplementation(espresso)
        androidTestImplementation(compose)
    }
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
