plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    // ./gradlew clean installDebug -Pflavor=development
    // ./gradlew clean installDebug -Pflavor=production
    val flavor: String = (project.findProperty("flavor") as? String) ?: "development"
    println("Flavor: $flavor")

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(project(":config:$flavor"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.rakangsoftware.flavors"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
