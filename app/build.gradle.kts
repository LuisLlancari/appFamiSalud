plugins {
    id("com.android.application")

    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services")

}

android {
    namespace = "com.famisalud.famisalud"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.famisalud.famisalud"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment:2.7.3")
    implementation("androidx.navigation:navigation-ui:2.7.3")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.google.android.material:material:1.4.0")

    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.2.0")




    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.22"))

    //  Dependencia para el Slider
    //  Documentación de la libreria para que puedan configurar el Carrusel
    //  https://github.com/denzcoskun/ImageSlideshow
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    //implementation ("com.airbnb.android:lottie:3.8.0")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database")

    implementation ("com.android.volley:volley:1.2.1")


    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-firestore:24.8.1")
    implementation("com.google.firebase:firebase-auth")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("com.squareup.picasso:picasso:2.8")

    implementation ("androidx.room:room-runtime:2.6.0")
    annotationProcessor ("androidx.room:room-compiler:2.6.0")
    implementation ("androidx.cardview:cardview:1.0.0")

    //  Documentación
    //  https://github.com/bumptech/glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    //  https://ourcodeworld.com/articles/read/928/how-to-use-sweet-alert-dialogs-in-android
    implementation("com.github.f0ris.sweetalert:library:1.5.6")


}