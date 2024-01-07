plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("io.github.takahirom.roborazzi")
}

android {
    namespace = "com.github.goutarouh.notionboost"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.github.goutarouh.notionboost"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation(project(":repository"))
    testImplementation(project(":test"))
    testImplementation(project(":data"))

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    // Glance
    implementation("androidx.glance:glance:1.0.0")
    implementation("androidx.glance:glance-appwidget:1.0.0")

    // Retrofit & Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    implementation("androidx.hilt:hilt-work:1.1.0")
    ksp("com.google.dagger:hilt-compiler:2.49")
    ksp("androidx.hilt:hilt-compiler:1.1.0")
    testImplementation("com.google.dagger:hilt-android-testing:2.49")
    kspTest("com.google.dagger:hilt-compiler:2.49")
    kspTest("androidx.hilt:hilt-compiler:1.1.0")
    androidTestAnnotationProcessor("com.google.dagger:hilt-android-compiler:2.49")

    // Coroutine
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // WorkManger
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    // DataStore
    implementation("androidx.datastore:datastore:1.0.0")

    // Test
    testImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation("org.robolectric:robolectric:4.11.1")

    // roborazzi
    testImplementation("io.github.takahirom.roborazzi:roborazzi:1.8.0-rc-1")
    testImplementation("io.github.takahirom.roborazzi:roborazzi-junit-rule:1.8.0-rc-1")
    testImplementation("io.github.takahirom.roborazzi:roborazzi-compose:1.8.0-rc-1")
}