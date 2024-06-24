import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.gradle.plugin-publish") version "1.2.1"
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
    `java-gradle-plugin`
    `kotlin-dsl`
}

group = "io.github.klahap.mccgen"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.16.0")
    implementation("org.jsoup:jsoup:1.15.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        freeCompilerArgs += "-Xcontext-receivers"
        jvmTarget = "21"
    }
}

gradlePlugin {
    website = "https://github.com/klahap/mccgen"
    vcsUrl = "https://github.com/klahap/mccgen.git"

    val generateMccCodes by plugins.creating {
        id = "io.github.klahap.mccgen"
        implementationClass = "io.github.klahap.mccgen.Plugin"
        displayName = "Generate Kotlin code for MCC"
        description = "A plugin that generates Kotlin code for MCC (Merchant category code)"
        tags = listOf("Kotlin", "MCC", "generate", "Stripe")
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = "1.9"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    languageVersion = "1.9"
}
