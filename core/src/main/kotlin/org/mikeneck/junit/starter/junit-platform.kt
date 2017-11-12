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

    private val junitJupiterVersion = "5.0.1"

    private val junitJupiter = "org.junit.jupiter"

    private val junitJupiterApi = "junit-jupiter-api"
    private val junitJupiterEngine = "junit-jupiter-engine"
    private val junitJupiterParams = "junit-jupiter-params"

    override fun api(dependencyName: String): JunitApiDependency = DefaultJunitApiDependency(dependencyName, apiDependency)

    override fun engine(dependencyName: String): JunitEngineDependency = DefaultJunitEngineDependency(dependencyName, engineDependency)

    fun params(dependencyName: String): JunitApiDependency = DefaultJunitApiDependency(dependencyName, paramsDependency)

    private val apiDependency: JunitDependency = DefaultJunitDependency("$junitJupiter:$junitJupiterApi:$junitJupiterVersion")
    private val engineDependency: JunitDependency = DefaultJunitDependency("$junitJupiter:$junitJupiterEngine:$junitJupiterVersion")
    val paramsDependency: JunitDependency = DefaultJunitDependency("$junitJupiter:$junitJupiterParams:$junitJupiterVersion")

    @Suppress("unused")
    val params: String = paramsDependency.artifactName
}

object JunitVintage: JunitPlatform {
    override fun api(dependencyName: String): JunitApiDependency = DefaultJunitApiDependency(dependencyName, apiDependency)

    override fun engine(dependencyName: String): JunitEngineDependency = DefaultJunitEngineDependency(dependencyName, engineDependency)

    private val junitVintage = "org.junit.vintage"
    private val junitVintageVersion = "4.12.1"
    private val junitVintageEngine = "junit-vintage-engine"

    val apiDependency: JunitDependency = DefaultJunitDependency("junit:junit:4.12")
    val engineDependency: JunitDependency = DefaultJunitDependency("$junitVintage:$junitVintageEngine:$junitVintageVersion")
}
