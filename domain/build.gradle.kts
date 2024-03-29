plugins {
    id("kotlin")
    id("java-library")
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
}

tasks.withType<Test> {
    useJUnitPlatform()
}

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

dependencies {
    Deps.AndroidX.run {
        implementation(inject)
        implementation(pagingWithoutAndroid)
    }

    Deps.Coroutines.run {
        implementation(core)
        implementation(android)
    }
    testImplementation()
}

ktlint {
    android.set(true)
    coloredOutput.set(true)
    verbose.set(true)
    outputToConsole.set(true)
    disabledRules.set(setOf("max-line-length", "import-ordering"))
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}
