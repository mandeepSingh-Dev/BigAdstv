plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")

    id ("kotlin-parcelize")

}

android {
    namespace = "com.appsinvo.bigadstv"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.appsinvo.bigadstv"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

  /*   signingConfigs{
        getByName("debug"){
            keyAlias = ""
            keyPassword = ""
            storeFile = File("")
            storePassword =""
        }
        create("release"){
            keyAlias = ""
            keyPassword = ""
            storeFile = File("")
            storePassword =""
        }
    } */

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
         //   signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }

  /*   bundle {
        language {
            enableSplit = false
        }
    } */
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    val activity_version = "1.8.0"
    implementation("androidx.activity:activity-ktx:$activity_version")


    implementation("com.intuit.sdp:sdp-android:1.1.0")


    implementation("androidx.leanback:leanback:1.0.0")

    val nav_version = "2.7.6"
    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")


    implementation("androidx.fragment:fragment-ktx:1.6.1")


    //DAGGER-HILT
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")

    //DATA-STORE
    implementation("androidx.datastore:datastore-preferences:1.0.0")



    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:v2.9.0")
    //GSon Converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //Okhttp Logging Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //Coil
    implementation("io.coil-kt:coil:2.4.0")
    // For media playback using ExoPlayer
    val media3_version = "1.1.1"
    implementation("androidx.media3:media3-exoplayer:$media3_version")
    implementation("androidx.media3:media3-exoplayer-dash:$media3_version")
    implementation("androidx.media3:media3-ui:$media3_version")


    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")


    //Exoplayer
    implementation("androidx.media3:media3-exoplayer:1.2.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.2.1")
    implementation("androidx.media3:media3-ui:1.2.1")

    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    implementation("com.facebook.shimmer:shimmer:0.5.0")





}