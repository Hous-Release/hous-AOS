plugins {
    id("kotlin")
    id("java-library")
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    Deps.AndroidX.run {
        implementation(inject)
    }

    Deps.Coroutines.run {
        implementation(core)
        implementation(android)
    }
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
