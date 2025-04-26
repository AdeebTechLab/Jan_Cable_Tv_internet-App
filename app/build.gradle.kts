plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.AdeebTechLab.JCTvN"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.AdeebTechLab.JCTvN"
        minSdk = 24
        targetSdk = 35
        versionCode = 5
        versionName = "0.5"

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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Firebase BOM for unified dependency versioning
    implementation(platform("com.google.firebase:firebase-bom:32.1.0"))

    // Firebase libraries managed by BOM
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-storage")

    // Material Design components and Google services
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.android.gms:play-services-auth:20.6.0")

    // Biometric Authentication
    implementation("androidx.biometric:biometric:1.1.0")

    // Lottie Animations for smooth UI
    implementation("com.airbnb.android:lottie:6.0.0")

    // SwipeRefreshLayout for pull-to-refresh feature
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // OneSignal for Notifications
    implementation("com.onesignal:OneSignal:5.0.0")

    implementation ("com.google.firebase:firebase-auth:22.0.0")
    implementation ("com.google.firebase:firebase-firestore:24.9.1")

}