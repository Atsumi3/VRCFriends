package dependencies

@Suppress("MayBeConstant", "SpellCheckingInspection")
object Dep {
    object GradlePlugin {
        val android = "com.android.tools.build:gradle:3.6.1"
        val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
        val deployGate = "com.deploygate:sdk:4.1.0"
    }

    object AndroidX {
        val coreKtx = "androidx.core:core-ktx:1.2.0"
        val appCompat = "androidx.appcompat:appcompat:1.1.0"
        val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
        val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.2"
        val design = "com.google.android.material:material:1.2.0-alpha05"
    }

    object Kotlin {
        val version = "1.3.61"
        val stdlibJvm = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        val coroutinesVersion = "1.3.3"
        val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutinesVersion}"
    }

    object Test {
        val junit = "junit:junit:4.12"
        val androidJunit4Ktx = "androidx.test.ext:junit-ktx:1.1.1"
        val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        val coroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Kotlin.coroutinesVersion}"
        val koinTest = "org.koin:koin-test:${Koin.version}"
    }

    object OkHttp {
        private val version = "4.4.0"
        val core = "com.squareup.okhttp3:okhttp:$version"
        val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Retrofit {
        private val version = "2.7.0"
        val core = "com.squareup.retrofit2:retrofit:$version"
        val converterMoshi = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object Moshi {
        private val version = "1.9.2"
        val core = "com.squareup.moshi:moshi:$version"
        val kotlin = "com.squareup.moshi:moshi-kotlin:$version"
    }

    object Koin {
        val version = "2.1.3"
        val core = "org.koin:koin-core:$version"
        val android = "org.koin:koin-android:$version"
        val androidxViewModel = "org.koin:koin-androidx-viewmodel:$version"
    }
}