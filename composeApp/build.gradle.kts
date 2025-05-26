import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.androidApplication)
    id("droidknights.kotlin.multiplatform")
    id("droidknights.compose.multiplatform")
    alias(libs.plugins.screenshot)
}

kotlin {

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }

        //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }
    targets
        .filterIsInstance<KotlinNativeTarget>()
        .forEach { target ->
            target.binaries {
                framework {
                    baseName = "ComposeApp"
                    isStatic = true
                }
            }
        }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName.set("composeApp")
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(projects.core.designsystem)

            implementation(projects.core.data.dataSession)
            implementation(projects.core.data.dataSetting)
            implementation(projects.core.data.dataSettingApi)

            implementation(projects.core.domain.domainSession)

            implementation(projects.feature.main)
            implementation(projects.feature.session)
            implementation(projects.feature.setting)

            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.koin.compose.viewmodel.navigation)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.droidknights.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.droidknights.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
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
    androidTestImplementation(libs.androidx.uitest.junit4)
    debugImplementation(libs.androidx.uitest.testManifest)
    debugImplementation(compose.uiTooling)

    screenshotTestImplementation(libs.androidx.compose.ui.tooling)
    screenshotTestImplementation(compose.runtime)
    screenshotTestImplementation(compose.material)
    screenshotTestImplementation(compose.foundation)
    screenshotTestImplementation(compose.components.uiToolingPreview)
    screenshotTestImplementation(compose.components.resources)

    screenshotTestImplementation(projects.core.designsystem)
    screenshotTestImplementation(projects.feature.bookmark)
    screenshotTestImplementation(projects.feature.contributor)
    screenshotTestImplementation(projects.feature.home)
    screenshotTestImplementation(projects.feature.main)
    screenshotTestImplementation(projects.feature.session)
    screenshotTestImplementation(projects.feature.setting)
}

compose.desktop {
    application {
        mainClass = "com.droidknights.app.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.droidknights.app"
            packageVersion = "1.0.0"
        }
    }
}

// From KotlinConf App
// https://github.com/JetBrains/kotlinconf-app/blob/c81492ee57a8da67390d84ad29f41b08128fe0e1/shared/build.gradle.kts#L193
val buildWebApp by tasks.registering(Copy::class) {
    val wasmDist = "wasmJsBrowserDistribution"

    from(tasks.named(wasmDist).get().outputs.files)

    into(layout.buildDirectory.dir("webApp"))

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
