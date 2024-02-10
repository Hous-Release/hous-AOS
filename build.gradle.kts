// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath(Deps.DI.hiltClassPath)
        classpath(Deps.Google.googleServices)
        classpath(Deps.Test.junit5Path)
        classpath(Deps.ThirdParty.Glide.transformations)
    }
}

plugins {
    id("androidx.navigation.safeargs.kotlin") version Deps.AndroidX.nav_version apply false
    id("com.android.application") version "7.2.0" apply false
    id("com.android.library") version "7.2.0" apply false
    id("org.jetbrains.kotlin.android") version kotlinVersion apply false
    id("org.jetbrains.kotlin.jvm") version kotlinVersion apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
