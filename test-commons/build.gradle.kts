plugins {
    id("java-library")
    kotlin("jvm") version("1.1.51")
}

repositories {
    mavenCentral()
}

dependencies {
    api("com.natpryce:hamkrest:1.4.2.2")
    api(kotlin("stdlib-jre8"))
}
