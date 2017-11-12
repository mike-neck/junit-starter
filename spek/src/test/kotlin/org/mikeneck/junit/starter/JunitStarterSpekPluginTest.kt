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

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import org.gradle.testkit.runner.TaskOutcome
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.nio.file.Files

object JunitStarterSpekPluginTest: Spek({

    given("applying org.mikeneck.junit.starter.spek plugin") {
        val projectDirectory = Files.createTempDirectory("junit-starter-test")

        projectDirectory.resolve("build.gradle") += """
            plugins {
                id("org.mikeneck.junit.starter.spek")
            }
            repositories {
                mavenCentral()
            }
            dependencies {
                testCompile "com.natpryce:hamkrest:1.4.2.2"
            }
            """

        val range = 0..9
        val reversed = range.reversed()
        reversed.last

        projectDirectory.resolve("src/main/test/com/example").mkdirs()

        projectDirectory.resolve("src/main/test/com/example/SpekSpecification.kt") += """
            package com.example
            import org.jetbrains.spek.api.Spek
            import org.jetbrains.spek.api.dsl.given
            import org.jetbrains.spek.api.dsl.it
            import org.jetbrains.spek.api.dsl.on
            import com.natpryce.hamkrest.and
            import com.natpryce.hamkrest.assertion.assert
            import com.natpryce.hamkrest.equalTo

            object SpekSpecification: Spek({
                given("range from 0 to 9") {
                    val range = 0..9
                    on("reversed") {
                        val reversed = range.reversed()
                        it("has 9 at first") {
                            assert.that(reversed.first, equalTo(9))
                        }
                        it("has 0 at last") {
                            assert.that(reversed.last, equalTo(0))
                        }
                    }
                }
            })
            """

        on("calling tasks") {
            val buildResult = gradleProject(projectDirectory).gradle("tasks")

            it("should have junitPlatformTest and compileKotlin") {
                assert.that(buildResult.output, contains("junitPlatformTest"))
            }
        }

        on("calling dependencies") {
            val buildResult = gradleProject(projectDirectory).gradle("dependencies")

            it("should have spek-api and spek-junit-platform-engine") {
                assert.that(buildResult.output,
                        contains("org.jetbrains.spek:spek-api")
                                and contains("org.jetbrains.spek:spek-junit-platform-engine"))
            }
        }

        on("calling junitPlatformTest") {
            val buildResult = gradleProject(projectDirectory).gradle("junitPlatformTest")

            it("should run spek") {
                assert.that(buildResult.task(":junitPlatformTest")?.outcome, equalTo(TaskOutcome.SUCCESS))
                assert.that(buildResult.tasks.map { it.path }, hasElement(":compileTestKotlin"))
            }
        }
    }
})
