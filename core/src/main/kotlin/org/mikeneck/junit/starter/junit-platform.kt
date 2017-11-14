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

interface JunitPlatform {
    fun api(dependencyName: String): JunitApiDependency
    fun engine(dependencyName: String): JunitEngineDependency
}

object JunitJupiter: JunitPlatform {

    private val junitJupiter = "org.junit.jupiter"

    private val junitJupiterApi = "junit-jupiter-api"
    private val junitJupiterEngine = "junit-jupiter-engine"
    private val junitJupiterParams = "junit-jupiter-params"

    override fun api(dependencyName: String): JunitApiDependency = DefaultJunitApiDependency(dependencyName, apiDependency)

    override fun engine(dependencyName: String): JunitEngineDependency = DefaultJunitEngineDependency(dependencyName, engineDependency)

    private val apiDependency: JunitDependency = DefaultJunitDependency(junitJupiter, junitJupiterApi, JunitVersion.junitJupiterVersion)
    private val engineDependency: JunitDependency = DefaultJunitDependency(junitJupiter, junitJupiterEngine, JunitVersion.junitJupiterVersion)
    val paramsDependency: JunitDependency = DefaultJunitDependency(junitJupiter, junitJupiterParams, JunitVersion.junitJupiterVersion)

    @Suppress("unused")
    val params: String = paramsDependency.asString
}

object JunitVintage: JunitPlatform {
    override fun api(dependencyName: String): JunitApiDependency = DefaultJunitApiDependency(dependencyName, apiDependency)

    override fun engine(dependencyName: String): JunitEngineDependency = DefaultJunitEngineDependency(dependencyName, engineDependency)

    private val junitVintage = "org.junit.vintage"
    private val junitVintageEngine = "junit-vintage-engine"

    val apiDependency: JunitDependency = DefaultJunitDependency("junit", "junit", JunitVersion.junitVersion)
    val engineDependency: JunitDependency = DefaultJunitDependency(junitVintage, junitVintageEngine, JunitVersion.junitVintageVersion)
}
