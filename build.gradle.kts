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
    val projects = listOf(":normal", ":library", ":spek")

    "allTests" {
        group = "Verification"
        description = "Runs all tests."

        val tests = projects.map { "$it:junitPlatformTest" }
        dependsOn(tests)

        doLast {
            val states = tests.map { task(it) }.map(Task::getState)
            val results = states.all { it.failure == null }
            if (!results) {
                val failed = states.find { it.failure != null } as TaskState
                throw GradleScriptException("Test failed.", failed.failure as Throwable)
            }
        }
    }

    "allPublish" {
        group = "Plugin Portal"
        description = "Publishes all plugins."
        dependsOn(projects.map { "$it:publishPlugins" })
    }

    "allUpdates" {
        group = "Update check"
        description = "Checks library updates"
        dependsOn((projects + ":junit-starter-core").map { "$it:dependencyUpdates" })
    }

    "allVersions" {
        doLast { 
            (projects + ":junit-starter-core")
                    .map { it to project(it) }
                    .map { it.first to it.second.version }
                    .forEach { println("${it.first} : ${it.second}") }
        }
    }
}
