/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.4.1/userguide/building_java_projects.html
 */

import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.spring") version "1.7.0"
    id("io.qameta.allure") version "2.10.0"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

val restassuredVersion by extra("5.1.1")
val allureVersion by extra("2.18.1")
val junit5Version by extra("5.9.0-M1")

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit5Version")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit5Version")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junit5Version")
    testImplementation("org.junit.jupiter:junit-jupiter:$junit5Version")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.1.1-jre")

    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test") { exclude("junit", "junit") }

    testImplementation("io.rest-assured:rest-assured:$restassuredVersion")
    testImplementation("io.rest-assured:json-path:$restassuredVersion")
    testImplementation("io.rest-assured:xml-path:$restassuredVersion")

    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")

    testImplementation("io.qameta.allure:allure-java-commons:$allureVersion")
    testImplementation("io.qameta.allure:allure-rest-assured:$allureVersion")
    testImplementation("io.qameta.allure:allure-junit5:$allureVersion")

    testImplementation("org.hamcrest:hamcrest:2.2")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

application {
    // Define the main class for the application.
    mainClass.set("affinidi.Application")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging {
        events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
        exceptionFormat = TestExceptionFormat.SHORT
        showCauses = true
        showExceptions = true
        showStackTraces = true
    }

    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
}

allure {
    adapter {
        frameworks {
            junit5 {
                adapterVersion.set(allureVersion)
                enabled.set(true)
            }
        }
        autoconfigure.set(true)
    }
    version.set(allureVersion)
}
