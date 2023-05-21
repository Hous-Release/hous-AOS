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
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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
    testImplementation("com.google.truth:truth:1.1.3")
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
