repositories {
  mavenCentral()
  google()
  jcenter()
  gradlePluginPortal()
  maven(url = "https://plugins.gradle.org/m2/")
}

plugins {
  `kotlin-dsl`
  `kotlin-dsl-precompiled-script-plugins`
}

gradlePlugin {
  plugins {
    register("demo-modules-plugin") {
      id = "demo-modules-plugin"
      implementationClass = "com.example.demomodules.plugin.DemoModulesAppPlugin"
    }
  }
}

object PluginVersions {
  const val kotlin = "1.8.0"
  const val androidGradle = "8.2.1"
  const val navigationSafeArgs = "2.4.0-beta02"
  const val googleServices = "4.3.10"
  const val crashlytics = "2.7.1"
  const val spotless = "5.17.0"
  const val gradleVersions = "0.39.0"
  const val daggerHilt = "2.40.2"
}

object Plugins {
  const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginVersions.kotlin}"
  const val androidGradle = "com.android.tools.build:gradle:${PluginVersions.androidGradle}"
  const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${PluginVersions.navigationSafeArgs}"
  const val googleServices = "com.google.gms:google-services:${PluginVersions.googleServices}"
  const val crashlytics = "com.google.firebase:firebase-crashlytics-gradle:${PluginVersions.crashlytics}"
  const val spotless = "com.diffplug.spotless:spotless-plugin-gradle:${PluginVersions.spotless}"
  const val gradleVersions = "com.github.ben-manes:gradle-versions-plugin:${PluginVersions.gradleVersions}"
  const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${PluginVersions.daggerHilt}"
}

dependencies {
  implementation(Plugins.androidGradle)
  implementation(Plugins.kotlin)

  implementation(Plugins.navigationSafeArgs)
  implementation(Plugins.daggerHilt)

  implementation(Plugins.googleServices)
  implementation(Plugins.crashlytics)

  implementation(Plugins.gradleVersions)
  implementation(Plugins.spotless)
}