import io.gitlab.arturbosch.detekt.Detekt

plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
    id("org.jmailen.kotlinter") version "3.9.0"
}

val rootDir = projectDir

allprojects {
    group = "me.dgahn"
    version = "0.1.0"

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jmailen.kotlinter")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "io.spring.dependency-management")

    allOpen {
        annotation("org.springframework.boot.autoconfigure.SpringBootApplication")
        annotation("org.springframework.stereotype.Component")
    }

    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    detekt {
        buildUponDefaultConfig = false // preconfigure defaults
        allRules = false // activate all available (even unstable) rules.
        config =
            files("$rootDir/config/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
        baseline = file("$rootDir/config/baseline.xml") // a way of suppressing issues before introducing detekt
    }

    kotlinter {
        ignoreFailures = false
        indentSize = 4
        reporters = arrayOf("checkstyle", "plain")
        experimentalRules = false
        disabledRules = emptyArray()
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")

        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "junit", module = "junit")
        }
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.0")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
        testImplementation("io.kotest:kotest-assertions-core:5.4.2")
    }

    tasks {
        withType<Test> {
            useJUnitPlatform()
            testLogging {
                exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            }
        }
        java {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        compileKotlin {
            kotlinOptions {
                jvmTarget = "11"
            }
            dependsOn(detekt)
        }
        compileTestKotlin {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
        withType<Detekt> {
            dependsOn(formatKotlin)
        }
    }
}
