/*
 * Copyright 2017 Shinya Mochida
 * 
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,software
 * Distributed under the License is distributed on an"AS IS"BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mikeneck.junit.starter

import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.contains
import org.gradle.testkit.runner.GradleRunner
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

object JunitStarterNormalPluginTest: Spek({

    given("applying org.mikeneck.junit.starter.normal plugin") {
        val projectDirectory = Files.createTempDirectory("junit-starter-test")
        projectDirectory.resolve("build.gradle") += """
            plugins {
                id("org.mikeneck.junit.starter.normal")
            }
            """

        on("calling tasks") {
            val buildResult = GradleRunner.create()
                    .withProjectDir(projectDirectory.toFile())
                    .withArguments("tasks", "--stacktrace")
                    .withPluginClasspath()
                    .build()

            it("should have junitPlatformTest") {
                assert.that(buildResult.output, contains("junitPlatformTest"))
            }
        }

        on("calling dependencies") {
            val buildResult = GradleRunner.create()
                    .withProjectDir(projectDirectory.toFile())
                    .withArguments("dependencies")
                    .withPluginClasspath()
                    .build()

            it("should have junit-jupiter-api and junit-jupiter-engine") {
                assert.that(buildResult.output, 
                        contains("org.junit.jupiter:junit-jupiter-api")
                                and contains("org.junit.jupiter:junit-jupiter-engine"))
            }
        }
    }

    given("applying org.mikeneck.junit.starter.normal plugin with parameterized test") {
        val projectDirectory = Files.createTempDirectory("junit-starter-test")
        projectDirectory.resolve("build.gradle") += """
            plugins {
                id("org.mikeneck.junit.starter.normal")
            }
            dependencies {
                testCompile junit.params
            }
            """

        on("calling dependencies") {
            val buildResult = GradleRunner.create()
                    .withProjectDir(projectDirectory.toFile())
                    .withArguments("dependencies")
                    .withPluginClasspath()
                    .build()

            it("should have junit-jupiter-params") {
                assert.that(buildResult.output, contains("org.junit.jupiter:junit-jupiter-params"))
            }
        }
    }

    given("applying org.mikeneck.junit.starter.normal plugin with vintage junit") {
        val projectDirectory = Files.createTempDirectory("junit-starter-test")
        projectDirectory.resolve("build.gradle") += """
            plugins {
                id("org.mikeneck.junit.starter.normal")
            }
            dependencies {
                testCompile junit.old
                testRuntime junit.vintage
            }
            """
        on("calling dependencies") {
            val buildResult = GradleRunner.create()
                    .withProjectDir(projectDirectory.toFile())
                    .withArguments("dependencies")
                    .withPluginClasspath()
                    .build()

            it("should have junit-jupiter-params") {
                assert.that(buildResult.output,
                        contains("junit:junit")
                                and contains("org.junit.vintage:junit-vintage-engine"))
            }
        }
    }
})

fun Path.write(content: String, charset: Charset = StandardCharsets.UTF_8): Path = Files.write(this, content.toByteArray(charset))

infix fun <A: Any, B: Any> A.then(b: B): B = this.let { b }

infix operator fun Path.plusAssign(content: String): Unit = this.write(content) then Unit

fun contains(pattern: String): Matcher<String> = contains(Regex(pattern))
