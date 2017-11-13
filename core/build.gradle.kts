import groovy.lang.Closure
import groovy.lang.GroovyObject
import org.gradle.api.publication.maven.internal.deployer.DefaultGroovyMavenDeployer
import org.gradle.api.publication.maven.internal.deployer.MavenRemoteRepository
import org.gradle.api.internal.plugins.DslObject
import org.gradle.kotlin.dsl.withConvention
import org.gradle.kotlin.dsl.withGroovyBuilder
import org.gradle.util.ConfigureUtil
import java.net.URI
import org.junit.platform.gradle.plugin.EnginesExtension
import org.junit.platform.gradle.plugin.FiltersExtension
import org.junit.platform.gradle.plugin.JUnitPlatformExtension
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberFunctions

plugins {
    id("java-library")
    kotlin("jvm") version("1.1.51")
    id("org.junit.platform.gradle.plugin")
    id("maven")
    id("signing")
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
    mavenLocal()
}

val junitPlatformVersion by project
val spekVersion by project
val kotlinVersion by project

dependencies {
    
    api(gradleApi())
    api("org.junit.platform:junit-platform-gradle-plugin:$junitPlatformVersion")
    api(kotlin(module ="stdlib-jre8", version = "$kotlinVersion"))
    testImplementation("org.jetbrains.spek:spek-api:$spekVersion")
    testRuntimeOnly("org.jetbrains.spek:spek-junit-platform-engine:$spekVersion")
}

val sonatypeUrl: Any? by project
val sonatypeUsername: Any? by project
val sonatypePassword: Any? by project


tasks {
    withType<Jar> {
        baseName = project.name
    }

    "javadocJar" (Jar::class) {
        dependsOn("javadoc")
        from("javadoc")
        classifier = "javadoc"
    }

    "sourcesJar" (Jar::class) {
        classifier = "sources"
        from(project.sourceSets.get("main").allSource)
    }

    if (sonatypeUrl != null && sonatypeUsername != null && sonatypePassword != null) {
        "uploadArchives"(Upload::class) {
            repositories {
                withConvention(MavenRepositoryHandlerConvention::class) {
                    mavenDeployer {
                        beforeDeployment {
                            project.signing.signPom(this)
                        }
                        withGroovyBuilder {
                            "repository"("url" to sonatypeUrl) {
                                "authentication"(mapOf("userName" to sonatypeUsername, "password" to sonatypePassword))
                            }
                        }
                        pom.project {
                            withGroovyBuilder { 
                                "name"("junit-starter-core")
                                "packaging"("jar")
                                "description"("support functions for gradle junit-starter plugin")
                                "url"("https://github.com/mike-neck/junit-starter")

                                "scm" {
                                    "connection"("scm:git:https://github.com/mike-neck/junit-starter")
                                    "developerConnection"("scm:git:https://github.com/mike-neck/junit-starter")
                                    "url"("https://github.com/mike-neck/junit-starter")
                                }

                                "licenses" {
                                    "license" {
                                        "name"("The Apache Software License, Version 2.0")
                                        "url"("http://www.apache.org/licenses/LICENSE-2.0.txt")
                                    }
                                }

                                "developers" {
                                    "developer" {
                                        "id"("mikeneck")
                                        "name"("Shinya Mochida")
                                        "email"("jkrt3333[at]gmail.com")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

signing { 
    sign(project.configurations["archives"])
}

artifacts { 
    add("archives", tasks["javadocJar"])
    add("archives", tasks["sourcesJar"])
}


val Project.sourceSets: SourceSetContainer get() = this.convention.getPlugin(JavaPluginConvention::class.java).sourceSets

@Suppress("ObjectLiteralToLambda")
fun Project.signing(action: SigningExtension.() -> Unit) = this.extensions.configure<SigningExtension>("signing", { this.action() })

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
