apply(from = "gradle/repositories.settings.gradle")

plugins {
    kmp
    alias(libs.plugins.kotlinx.serialization)
}

kotlin.sourceSets {
    jvmTest {
        tasks.withType<Test>().configureEach { useJUnitPlatform() }
        dependencies {
            implementation(libs.kotest.junit.runner)
            implementation(libs.kotest.engine)
            implementation(libs.kotest.assertion)
            implementation(libs.kotest.property)
            implementation(libs.kotlin.reflect)
        }
    }
    commonMain {
        dependencies {
            implementation(libs.ktor.network)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.core)
        }
    }
}
