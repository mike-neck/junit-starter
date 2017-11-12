version = "5.0.1"
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
        dependsOn(projects.map { "$it:junitPlatformTest" })
    }

    "allPublish" {
        dependsOn(projects.map { "$it:publishPlugins" })
    }
}
