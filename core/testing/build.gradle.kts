plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.screenshot)
}

android {
    namespace = "com.droidknights.app.core.testing"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    testOptions {
        //noinspection WrongGradleMethod
        screenshotTests {
            imageDifferenceThreshold = 0.0001f
        }
    }
    experimentalProperties["android.experimental.enableScreenshotTest"] = true
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    screenshotTestImplementation(libs.androidx.core.ktx)
    screenshotTestImplementation("androidx.compose.foundation:foundation:1.8.1")
    screenshotTestImplementation(libs.androidx.appcompat)
    screenshotTestImplementation(libs.androidx.material)
    screenshotTestImplementation(libs.androidx.activity.compose)
    screenshotTestImplementation(libs.ui.tooling)
    screenshotTestImplementation(projects.core.designsystem)
    screenshotTestImplementation(projects.feature.bookmark)
    screenshotTestImplementation(projects.feature.contributor)
    screenshotTestImplementation(projects.feature.home)
    screenshotTestImplementation(projects.feature.main)
    screenshotTestImplementation(projects.feature.session)
    screenshotTestImplementation(projects.feature.setting)
}