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

import java.io.InputStreamReader
import java.util.*

object JunitVersion {
    private val propertiesFile: String get() = "junit-starter.properties"

    private val properties: Map<String, String> by lazy {
        JunitVersion::class.java.classLoader.getResourceAsStream(propertiesFile)
                .let { s -> InputStreamReader(s, Charsets.UTF_8) }
                .use { r ->
                    Properties().apply { this.load(r) }
                            .let { p ->
                                mutableMapOf<String, String>()
                                        .apply { 
                                            p.stringPropertyNames().forEach { this[it] = p.getProperty(it) }
                                        }
                            }
                }
    }

    val junitJupiterVersion: String get() = properties["junitJupiterVersion"]?: throw IllegalStateException("property junitJupiterVersion is not initialized.")
    val junitPlatformVersion: String get() = properties["junitPlatformVersion"]?: throw IllegalStateException("property junitPlatformVersion is not initialized.")
    val spekVersion: String get() = properties["spekVersion"]?: throw IllegalStateException("property spekVersion is not initialized.")
    val kotlinVersion: String get() = properties["kotlinVersion"]?: throw IllegalStateException("property kotlinVersion is not initialized.")
    val junitVintageVersion: String get() = properties["junitVintageVersion"]?: throw IllegalStateException("property junitVintageVersion is not initialized.")
    val junitVersion: String get() = properties["junitVersion"]?: throw IllegalStateException("property junitVintageVersion is not initialized.")
}
