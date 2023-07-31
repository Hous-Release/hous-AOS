@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())
android {
    packagingOptions {
        resources.excludes.add("META-INF/LICENSE*")
    }
    signingConfigs {
        create("release") {
            storeFile = file(properties.getProperty("STORE_FILE"))
            keyAlias = properties.getProperty("KEY_ALIAS")
            keyPassword = properties.getProperty("KEY_PASSWORD")
            storePassword = properties.getProperty("STORE_PASSWORD")
        }
    }
    compileSdk = AppConfig.compileSdkVersion
    buildToolsVersion = AppConfig.buildToolsVersion

    lint {
        abortOnError = false
        baseline = file("lint-baseline.xml")
        disable.add("JvmStaticProvidesInObjectDetector")
        disable.add("FieldSiteTargetOnQualifierAnnotation")
        disable.add("ModuleCompanionObjects")
        disable.add("ModuleCompanionObjectsNotInModuleParent")
    }
    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] =
            "de.mannodermaus.junit5.AndroidJUnit5Builder"

        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            properties.getProperty("KAKAO_NATIVE_APP_KEY")
        )

        manifestPlaceholders["KAKAO_REDIRECT_SCHEME"] =
            properties.getProperty("KAKAO_REDIRECT_SCHEME")
    }

    lint {
        baseline = file("lint-baseline.xml")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            buildConfigField(
                "String",
                "HOST_URI",
                properties.getProperty("HOST_RELEASE_URI")
            )
        }
        getByName("debug") {
            buildConfigField(
                "String",
                "HOST_URI",
                properties.getProperty("HOST_URI")
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
        viewBinding = true
        dataBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.kotlinCompilerExtensionVersion
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":designsystem"))
    implementation(project(":testing"))
    implementation(project(":feature:todo"))

    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    Deps.AndroidX.run {
        implementation(kakao_login)
        implementation(hilt_navigation)
        implementation(navigation)
        implementation(navigationFragment)
        implementation(core)
        implementation(appcompat)
        implementation(material)
        implementation(constraintLayout)
        implementation(recyclerview)
        implementation(activityKTX)
        implementation(lifecycleKTX)
        implementation(fragmentKTX)
        implementation(security)
        implementation(paging)
    }

    Deps.AndroidX.Compose.run {
        implementation(activity)
        implementation(material)
        implementation(animations)
        implementation(tool)
        implementation(viewModel)
        implementation(mdcTheme)
        implementation(appCompatTheme)
        implementation(lifecycle)
        implementation(navigation)
    }

    Deps.DI.run {
        implementation(hilt)
        implementation(hiltNavigationCompose)
        kapt(hiltKapt)
    }

    Deps.Coroutines.run {
        implementation(core)
        implementation(android)
        testImplementation(test)
    }

    Deps.Network.run {
        implementation(interceptor)
        implementation(gson)
        implementation(retrofit2)
        implementation(retrofit2Converter)
    }

    Deps.Google.run {
        implementation(platform(firebaseBom))
        implementation(firebaseMessaging)
        implementation(analytics)
    }

    Deps.ThirdParty.run {
        implementation(timber)
        implementation(lottie)
        implementation(compose_lottie)
        implementation(ballon)
        implementation(coilCompose)
    }

    Deps.ThirdParty.Glide.run {
        implementation(glide)
        implementation(kapt)
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
