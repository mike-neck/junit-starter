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

interface JunitDependency {
    val asString: String get() = "$group:$artifact:$version"
    val group: String
    val artifact: String
    val version: String
}

interface DependencyName {
    val dependencyName: String
}

interface JunitApiDependency: JunitDependency, DependencyName

interface JunitEngineDependency: JunitDependency, DependencyName

data class DefaultJunitDependency(override val group: String, override val artifact: String, override val version: String) : JunitDependency

data class DefaultJunitApiDependency(override val dependencyName: String, private val junitDependency: JunitDependency): JunitApiDependency {
    override val asString: String get() = junitDependency.asString

    override val group: String get() = junitDependency.group
    override val artifact: String get() = junitDependency.artifact
    override val version: String get() = junitDependency.version
}

data class DefaultJunitEngineDependency(override val dependencyName: String, private val junitDependency: JunitDependency): JunitEngineDependency {
    override val asString: String get() = junitDependency.asString

    override val group: String get() = junitDependency.group
    override val artifact: String get() = junitDependency.artifact
    override val version: String get() = junitDependency.version
}
