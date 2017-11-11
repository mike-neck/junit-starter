version = "5.0.1"
group = "org.mikeneck.junit.starter"

subprojects { 
    group = rootProject.group

    tasks.withType<Jar>().matching { it.name == "jar" }.all {
        baseName = "${rootProject.name}-${project.name}"
    }
}
