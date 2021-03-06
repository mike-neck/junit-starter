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
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path


fun Path.write(content: String, charset: Charset = StandardCharsets.UTF_8): Path = Files.write(this, content.toByteArray(charset))

fun Path.mkdirs(): Path = Files.createDirectories(this)

infix fun <A: Any, B: Any> A.then(b: B): B = this.let { b }

infix operator fun Path.plusAssign(content: String): Unit = this.write(content) then Unit

fun contains(pattern: String): Matcher<String> = com.natpryce.hamkrest.contains(Regex(pattern))

fun gradleProject(projectDir: Path): GradleRunTask = object : GradleRunTask {
    override fun gradle(vararg taskNames: String): BuildResult = GradleRunner.create()
            .withProjectDir(projectDir.toFile())
            .withArguments(taskNames.toList())
            .withPluginClasspath()
            .build()
}

interface GradleRunTask {
    fun gradle(vararg taskNames: String): BuildResult
}
