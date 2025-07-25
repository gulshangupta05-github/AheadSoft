plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.abhedwebsoft"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.abhedwebsoft"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {

        dataBinding=true
    }
}

dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.recyclerview)
        implementation(libs.constraintlayout)

        // Lifecycle ViewModel + LiveData
        implementation(libs.lifecycle.viewmodel.ktx)
        implementation(libs.lifecycle.livedata.ktx)

        // Coroutines
        implementation(libs.coroutines.android)
        implementation(libs.coroutines.core)

        // Retrofit
        implementation(libs.retrofit2)
        implementation(libs.retrofit2ConverterGson)

        // Glide
        implementation(libs.glide)
        kapt(libs.glide.compiler)

    implementation(libs.circleimageview.lib)

    // Activity ViewModel delegate
        implementation(libs.androidx.activity.ktx)

        // Testing
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)



}