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

import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.assertion.assert
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object JunitVersionSpek: Spek({
    given("load JunitVersion object") {

        on("get junitJupiterVersion") {
            it("is 5.0.2") {
                assert.that(JunitVersion.junitJupiterVersion, equalTo("5.0.2"))
            }
        }

        on("get junitPlatformVersion") {
            it("is 1.0.2") {
                assert.that(JunitVersion.junitPlatformVersion, equalTo("1.0.2"))
            }
        }

        on("get spekVersion") {
            it("is 1.1.5") {
                assert.that(JunitVersion.spekVersion, equalTo("1.1.5"))
            }
        }

        on("get kotlinVersion") {
            it("is 1.1.60") {
                assert.that(JunitVersion.kotlinVersion, equalTo("1.1.60"))
            }
        }

        on("get junitVintageVersion") {
            it("is 4.12.2") {
                assert.that(JunitVersion.junitVintageVersion, equalTo("4.12.2"))
            }
        }

        on("get junitVersion") {
            it("is 4.12") {
                assert.that(JunitVersion.junitVersion, equalTo("4.12"))
            }
        }
    }
})
