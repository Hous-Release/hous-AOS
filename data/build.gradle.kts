plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation(project(":domain"))

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
    }

    Deps.Test.run {
        testImplementation(junit)
        androidTestImplementation(androidTest)
        androidTestImplementation(espresso)
    }

    Deps.DI.run {
        implementation(hilt)
        kapt(hiltKapt)
    }

    Deps.AndroidX.run {
        implementation(kakao_login)
        implementation(pagingWithoutAndroid)
    }

    Deps.Google.run {
        implementation(platform(firebaseBom))
        implementation(firebaseMessaging)
    }
    testImplementation()
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
