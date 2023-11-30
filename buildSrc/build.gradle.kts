apply(from = "../gradle/repositories.settings.gradle")
plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
}