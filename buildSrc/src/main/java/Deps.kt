const val ktlintVersion = "10.3.0"
const val kotlinVersion = "1.7.10"

object AppConfig {
    const val applicationId = "hous.release.android"
    const val buildToolsVersion = "30.0.3"
    const val compileSdkVersion = 32
    const val minSdkVersion = 26
    const val targetSdkVersion = 32
    const val versionCode = 1
    const val versionName = "1.0"
}

object Deps {
    object AndroidX {
        const val kakao_login = "com.kakao.sdk:v2-user:2.11.0"
        const val hilt_navigation = "androidx.hilt:hilt-navigation-fragment:1.0.0"
        private const val version = "2.5.1"
        const val navigation = "androidx.navigation:navigation-ui-ktx:$version"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$version"
        const val core = "androidx.core:core-ktx:1.8.0"
        const val appcompat = "androidx.appcompat:appcompat:1.5.0"
        const val material = "com.google.android.material:material:1.6.1"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
        const val inject = "javax.inject:javax.inject:1"
        const val activityKTX = "androidx.activity:activity-ktx:1.5.1"
        const val lifecycleKTX = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.0-alpha01"
        const val fragmentKTX = "androidx.fragment:fragment-ktx:1.5.0"
        const val security = "androidx.security:security-crypto-ktx:1.1.0-alpha03"

        object Compose {
            const val activity = "androidx.activity:activity-compose:1.5.1"
            const val material = "androidx.compose.material:material:1.2.1"
            const val animations = "androidx.compose.animation:animation:1.2.1"
            const val tool = "androidx.compose.ui:ui-tooling:1.2.1"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
            const val mdcTheme = "com.google.android.material:compose-theme-adapter:1.1.16"
            const val appCompatTheme = "com.google.accompanist:accompanist-appcompat-theme:0.25.1"
        }
    }

    object DI {
        private const val version = "2.43.2"
        const val hiltClassPath = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val hiltKapt = "com.google.dagger:hilt-compiler:$version"
    }

    object Coroutines {
        private const val version = "1.6.4"

        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object Network {
        private const val version = "2.9.0"

        const val interceptor = "com.squareup.okhttp3:logging-interceptor:4.10.0"
        const val gson = "com.google.code.gson:gson:$version"
        const val retrofit2 = "com.squareup.retrofit2:retrofit:$version"
        const val retrofit2Converter = "com.squareup.retrofit2:converter-gson:$version"
    }

    object ThirdParty {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
        const val lottie = "com.airbnb.android:lottie:5.2.0"
        const val ballon = "com.github.skydoves:balloon:1.4.7"
        const val coilCompose = "io.coil-kt:coil-compose:2.2.2"

        object Glide {
            private const val version = "4.13.2"

            const val glide = "com.github.bumptech.glide:glide:$version"
            const val kapt = "com.github.bumptech.glide:compiler:$version"
        }
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val androidTest = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        const val compose = "androidx.compose.ui:ui-test-junit4:1.2.1"
    }

    object Google {
        const val googleServices = "com.google.gms:google-services:4.3.13"
        const val firebaseBom = "com.google.firebase:firebase-bom:30.4.1"
        const val firebaseMessaging = "com.google.firebase:firebase-messaging-ktx"
    }
}
