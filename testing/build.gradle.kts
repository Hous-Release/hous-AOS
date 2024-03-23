plugins {
    id("kotlin")
    id("java-library")
    kotlin("jvm")
    kotlin("kapt")
}

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
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