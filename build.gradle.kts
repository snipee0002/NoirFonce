import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

group = "de.yuuto"
version = "1.0"

repositories {
    mavenCentral()

}

dependencies {
    implementation("io.ktor:ktor-client-core:2.1.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.1.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.3")
    implementation("io.ktor:ktor-network:2.1.3")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("io.ktor:ktor-client-apache-jvm:2.1.2")
    implementation("dev.kord:kord-core:0.7.4")
    implementation("co.touchlab:stately-isolate:1.2.3")
}

application {
    mainClass.set("de.yuuto.monitor.MainKt")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}