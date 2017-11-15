plugins {
    id("java-library")
    kotlin("jvm") version("1.1.60")
    id("com.github.ben-manes.versions") version("0.17.0")
}

repositories {
    mavenCentral()
}

val kotlinVersion by project

dependencies {
    api("com.natpryce:hamkrest:1.4.2.2")
    api(kotlin(module = "stdlib-jre8", version = "$kotlinVersion"))
    api(gradleTestKit())
}
