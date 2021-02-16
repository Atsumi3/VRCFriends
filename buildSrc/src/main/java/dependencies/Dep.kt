package dependencies

@Suppress("MayBeConstant", "SpellCheckingInspection")
object Dep {
    object GradlePlugin {
        val android = "com.android.tools.build:gradle:4.0.2"
        val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
        val deployGate = "com.deploygate:sdk:4.1.0"
    }

    object AndroidX {
        val coreKtx = "androidx.core:core-ktx:1.3.2"
        val appCompat = "androidx.appcompat:appcompat:1.2.0"
        val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.5"
        val design = "com.google.android.material:material:1.2.1"
    }

    object Kotlin {
        val version = "1.4.10"
        val stdlibJvm = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        val coroutinesVersion = "1.3.9"
        val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutinesVersion}"
    }

    object Test {
        val junit = "junit:junit:4.13"
        val androidJunit4Ktx = "androidx.test.ext:junit-ktx:1.1.2"
        val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
        val coroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Kotlin.coroutinesVersion}"
        val koinTest = "org.koin:koin-test:${Koin.version}"
    }

    object OkHttp {
        private val version = "4.9.0"
        val core = "com.squareup.okhttp3:okhttp:$version"
        val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Retrofit {
        private val version = "2.9.0"
        val core = "com.squareup.retrofit2:retrofit:$version"
        val converterMoshi = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object Moshi {
        private val version = "1.11.0"
        val core = "com.squareup.moshi:moshi:$version"
        val kotlin = "com.squareup.moshi:moshi-kotlin:$version"
    }

    object Koin {
        val version = "2.1.6"
        val core = "org.koin:koin-core:$version"
        val android = "org.koin:koin-android:$version"
        val androidxViewModel = "org.koin:koin-androidx-viewmodel:$version"
    }
}