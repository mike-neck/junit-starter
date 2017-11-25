import com.gradle.publish.PluginBundleExtension
import com.gradle.publish.PluginConfig
import groovy.lang.Closure
import java.net.URI
import org.junit.platform.gradle.plugin.EnginesExtension
import org.junit.platform.gradle.plugin.FiltersExtension
import org.junit.platform.gradle.plugin.JUnitPlatformExtension

plugins {
    id("java-library")
    id("java-gradle-plugin")
    kotlin("jvm") version("1.1.61")
    id("org.junit.platform.gradle.plugin")
    id("com.gradle.plugin-publish")
    id("com.github.ben-manes.versions") version("0.17.0")
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
    maven {
        url = URI("http://dl.bintray.com/jetbrains/spek")
    }
}

val spekVersion by project
val kotlinVersion by project

dependencies {
    api(project(":junit-starter-core")) {
    }
    testImplementation(project(":test-commons"))
    testImplementation(gradleTestKit())
    testImplementation("com.natpryce:hamkrest:1.4.2.2")
    testImplementation("org.jetbrains.spek:spek-api:$spekVersion") {
        exclude(module = "kotlin-reflect")
    }
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    testRuntimeOnly("org.jetbrains.spek:spek-junit-platform-engine:$spekVersion")
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
    version = project.version

    plugins {
        this.create("junitStarterLibraryPlugin") {
            id = "org.mikeneck.junit.starter.library"
            displayName = "Gradle JUnit5 Starter Library Plugin"
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
