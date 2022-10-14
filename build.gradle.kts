import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    `java-library`
    `maven-publish`
}

group = "org.okapi.lib"
version = "1.0.1"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation("com.google.guava:guava:30.1.1-jre")
    // bean util dependency
    implementation("org.springframework:spring-beans:5.3.18")
    // json util dependency
    api("com.fasterxml.jackson.core:jackson-databind:2.11.4")
    // kotlin coroutines dependency
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/open-okapi/server-common-util")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}