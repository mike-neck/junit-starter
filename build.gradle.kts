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
    val normalTest = ":normal:junitPlatformTest"
    val libraryTest = ":library:junitPlatformTest"
    val spekTest = ":spek:junitPlatformTest"

    "test" {
        dependsOn(normalTest, libraryTest, spekTest)
    }

    "compileGroovy" {
        dependsOn("compileKotlin")
    }
}
