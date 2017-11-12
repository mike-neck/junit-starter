import com.gradle.publish.PluginBundleExtension
import com.gradle.publish.PluginConfig
import java.net.URI
import org.junit.platform.gradle.plugin.EnginesExtension
import org.junit.platform.gradle.plugin.FiltersExtension
import org.junit.platform.gradle.plugin.JUnitPlatformExtension
import groovy.lang.Closure

plugins {
    id("java-library")
    id("java-gradle-plugin")
    kotlin("jvm") version("1.1.51")
    id("org.junit.platform.gradle.plugin")
    id("com.gradle.plugin-publish")
}

configure<JUnitPlatformExtension> {
    filters {
        engines {
            include("spek")
        }
    }
}

repositories {
    jcenter()
}

dependencies {
    api(project(":core"))
    testImplementation(project(":test-commons"))
    testImplementation(gradleTestKit())
    testImplementation("com.natpryce:hamkrest:1.4.2.2")
    testImplementation("org.jetbrains.spek:spek-api:1.1.5") {
        exclude(module = "kotlin-reflect")
    }
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.1.51")
    testRuntimeOnly("org.jetbrains.spek:spek-junit-platform-engine:1.1.5")
}

tasks {
    "junitPlatformTest" {
        dependsOn("pluginUnderTestMetadata")
    }
}

pluginBundle {
    website = "https://github.com/mike-neck/junit-starter"
    vcsUrl = "https://github.com/mike-neck/junit-starter"
    description = "Gradle plugin that provides minimum settings of junit-jupiter(junit5)."
    tags = listOf("test", "junit", "junit-jupiter", "junit5")
    version = "5.0.1"

    plugins { 
        this.create("junitStarterNormalPlugin") {
            id = "org.mikeneck.junit.starter.normal"
            displayName = "Gradle JUnit5 Starter Normal Plugin"
        }
    }
}

fun JUnitPlatformExtension.filters(setup: FiltersExtension.() -> Unit) {
    when (this) {
        is ExtensionAware -> extensions.getByType(FiltersExtension::class.java).setup()
        else -> throw Exception("${this::class} must be an instance of ExtensionAware")
    }
}

fun FiltersExtension.engines(setup: EnginesExtension.() -> Unit) {
    when (this) {
        is ExtensionAware -> extensions.getByType(EnginesExtension::class.java).setup()
        else -> throw Exception("${this::class} must be an instance of ExtensionAware")
    }
}

fun <D> D.closure(f: D.() -> Unit): Closure<Unit> = object: Closure<Unit>(this) {
    fun doCall() = this@closure.f()
}

fun PluginBundleExtension.plugins(configuration: NamedDomainObjectContainer<PluginConfig>.() -> Unit) = this.plugins((this.plugins as NamedDomainObjectContainer<PluginConfig>).closure(configuration))
