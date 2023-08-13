import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("kotlin")
    id("java-library")
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
}

val properties = Properties().apply {
    load(File(rootDir, "local.properties").inputStream())
}
val housS3BaseUrl = properties.getProperty("HOUS_S3_BASE_URL")
    ?: throw IllegalArgumentException("HOUS_S3_BASE_URL must be set in local.properties")

tasks {
    register("generatePropertiesSource") {
        doLast {
            val outputDir = file("$buildDir/generated/source/myProperties")
            outputDir.mkdirs()

            file("$outputDir/HousConfig.kt").apply {
                writeText(
                    """
                     
                    object HousConfig {
                        const val HOUS_S3_BASE_URL = "$housS3BaseUrl"
                    }
                    """.trimIndent()
                )
            }
        }
    }

    named("compileKotlin") {
        dependsOn("generatePropertiesSource")
    }

    withType<Test> {
        useJUnitPlatform()
    }
}

sourceSets {
    main {
        kotlin {
            srcDir("$buildDir/generated/source/myProperties")
        }
    }
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
