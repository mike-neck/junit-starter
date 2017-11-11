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

import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler

operator fun RepositoryHandler.invoke(action: RepositoryHandler.() -> Any): RepositoryHandler = this.apply { this.action() }


operator fun ConfigurationContainer.invoke(action: ConfigurationContainer.() -> Any): ConfigurationContainer = this.apply { this.action() }

operator fun DependencyHandler.invoke(action: DependencyHandler.() -> Any): DependencyHandler = this.apply { this.action() }

fun DependencyHandler.junitApi(dependency: JunitApiDependency): Dependency = this.add(dependency.dependencyName, dependency.artifactName)
fun DependencyHandler.junitEngine(dependency: JunitEngineDependency): Dependency = this.add(dependency.dependencyName, dependency.artifactName)



val junitPlugin: String = "org.junit.platform.gradle.plugin"

val junitApi: String = "junitApi"
val junitEngine: String = "junitEngine"


interface JunitDependency {
    val artifactName: String
}

interface DependencyName {
    val dependencyName: String
}

interface JunitApiDependency: JunitDependency, DependencyName

interface JunitEngineDependency: JunitDependency, DependencyName

data class DefaultJunitDependency(override val artifactName: String): JunitDependency

data class DefaultJunitApiDependency(override val dependencyName: String, private val junitDependency: JunitDependency): JunitApiDependency {
    override val artifactName: String get() = junitDependency.artifactName
}

data class DefaultJunitEngineDependency(override val dependencyName: String, private val junitDependency: JunitDependency): JunitEngineDependency {
    override val artifactName: String get() = junitDependency.artifactName
}

interface JunitPlatform {
    fun api(dependencyName: String): JunitApiDependency
    fun engine(dependencyName: String): JunitEngineDependency
}

object JunitJupiter: JunitPlatform {
    override fun api(dependencyName: String): JunitApiDependency = DefaultJunitApiDependency(dependencyName, apiDependency)

    override fun engine(dependencyName: String): JunitEngineDependency = DefaultJunitEngineDependency(dependencyName, engineDependency)

    fun params(dependencyName: String): JunitApiDependency = DefaultJunitApiDependency(dependencyName, paramsDependency)

    private val junitJupiterVersion = "5.0.1"

    private val junitJupiter = "org.junit.jupiter"

    private val junitJupiterApi = "junit-jupiter-api"
    private val junitJupiterEngine = "junit-jupiter-engine"
    private val junitJupiterParams = "junit-jupiter-params"

    private val apiDependency: JunitDependency = DefaultJunitDependency("$junitJupiter:$junitJupiterApi:$junitJupiterVersion")
    private val engineDependency: JunitDependency = DefaultJunitDependency("$junitJupiter:$junitJupiterEngine:$junitJupiterVersion")
    private val paramsDependency: JunitDependency = DefaultJunitDependency("$junitJupiter:$junitJupiterParams:$junitJupiterVersion")
}

object JunitVintage {

    val junitVintage = "org.junit.vintage"
    val junitVintageVersion = "4.12.1"
    val junitVintageEngine = "junit-vintage-engine"
}
