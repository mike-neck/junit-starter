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

import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.plugins.PluginContainer

fun Project.applyPluginIfNot(pluginName: String): PluginContainer = this.plugins
        .also { if (!it.hasPlugin(pluginName)) it.apply(pluginName) }

operator fun RepositoryHandler.invoke(action: RepositoryHandler.() -> Any): RepositoryHandler = this.apply { this.action() }

operator fun ConfigurationContainer.invoke(action: ConfigurationContainer.() -> Any): ConfigurationContainer = this.apply { this.action() }

operator fun DependencyHandler.invoke(action: DependencyHandler.() -> Any): DependencyHandler = this.apply { this.action() }

fun DependencyHandler.junitApi(dependency: JunitApiDependency): Dependency = this.add(dependency.dependencyName, dependency.artifactName)
fun DependencyHandler.junitEngine(dependency: JunitEngineDependency): Dependency = this.add(dependency.dependencyName, dependency.artifactName)

val junitPlugin: String = "org.junit.platform.gradle.plugin"
