import java.net.URI

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = URI.create("https://dl.bintray.com/kotlin/kotlin-dev/")
        }
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.junit.platform.gradle.plugin") {
                useModule("org.junit.platform:junit-platform-gradle-plugin:1.0.2")
            }
        }
    }
}

rootProject.name = "junit-starter"
include("core")
include("normal")
include("library")
include("spek")
include("test-commons")

project(":core").name = "junit-starter-core"
