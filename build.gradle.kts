plugins {
    id ("com.android.application") version "8.2.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.9.20" apply false

}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Add the Safe Args plugin classpath here
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.20")

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3") // Safe Args plugin
    }
}