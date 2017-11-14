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
        group = "Verification"
        description = "Runs all tests."
        dependsOn(projects.map { "$it:junitPlatformTest" })
    }

    "allPublish" {
        group = "Plugin Portal"
        description = "Publishes all plugins."
        dependsOn(projects.map { "$it:publishPlugins" })
    }

    "junitStarterProperties"(Copy::class) {
        group = "Build Setup"
        description = "Copies gradle.properties file(has each library's version information) into core project."
        from(file("gradle.properties"))
        into(file("core/src/main/resources"))
        rename { _ -> "junit-starter.properties" }
    }
}
