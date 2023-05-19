plugins {
    id("kotlin")
    id("java-library")
    kotlin("jvm")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    Deps.Coroutines.run {
        implementation(core)
        implementation(android)
        implementation(reflect)
        implementation(test)
    }
    Deps.Test.run {
        implementation(junit5Engine)
        implementation(junit5Api)
    }
}