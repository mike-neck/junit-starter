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

import org.gradle.api.Plugin
import org.gradle.api.Project

class JunitStarterSpekPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        project.applyPluginIfNot(kotlin)
        project.applyPluginIfNot(junitPlugin)
        project.repositories.mavenCentral()
        project.dependencies {
            junitApi(api("testCompile"))
            junitApi(reflect("testRuntime"))
            junitEngine(engine("testRuntime"))
        }
    }

    companion object: JunitPlatform {

        val kotlin = "org.jetbrains.kotlin.jvm"

        private val spekVersion = "1.1.5"
        private val spek = "org.jetbrains.spek"
        private val spekApi = "spek-api"
        private val spekEngine = "spek-junit-platform-engine"

        override fun api(dependencyName: String): JunitApiDependency =
                DefaultJunitApiDependency(dependencyName, DefaultJunitDependency("$spek:$spekApi:$spekVersion"))

        fun reflect(dependencyName: String): JunitApiDependency =
                DefaultJunitApiDependency(dependencyName, DefaultJunitDependency("org.jetbrains.kotlin:kotlin-reflect:1.1.51"))

        override fun engine(dependencyName: String): JunitEngineDependency =
                DefaultJunitEngineDependency(dependencyName, DefaultJunitDependency("$spek:$spekEngine:$spekVersion"))
    }
}
