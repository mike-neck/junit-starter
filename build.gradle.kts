val junitJupiterVersion by project

version = "$junitJupiterVersion"
group = "org.mikeneck.junit.starter"

subprojects { 
    group = rootProject.group
    version = rootProject.version

    tasks.withType<Jar>().all {
        baseName = "${rootProject.name}-${project.name}"
    }
}

tasks {
    val projects = listOf(":normal", ":library", "spek")

    "allTests" {
        group = "Verification"
        description = "Runs all tests."
        dependsOn(projects.map { "$it:junitPlatformTest" })
    }

    "allPublish" {
        group = "Plugin Portal"
        description = "Publishes all plugins."
        dependsOn(projects.map { "$it:publishPlugins" })
    }
}
