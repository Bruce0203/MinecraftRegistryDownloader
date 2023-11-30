plugins {
    base
    kotlin("multiplatform")
}

allprojects {
    group = rootProject.group
    version = rootProject.version
}

kotlin {
    jvm {
        jvmToolchain(17)
        compilations {
            val main = getByName("main")
            tasks {
                register<Jar>("fatJar") {
                    group = "application"
                    manifest {
                        attributes["Implementation-Title"] = "Gradle Jar File Example"
                        attributes["Implementation-Version"] = archiveVersion
                        attributes["Main-Class"] = "com.app.BackendAppKt"
                    }
                    archiveBaseName.set("${project.name}-fat")
                    from(main.output.classesDirs, main.compileDependencyFiles)
                    with(tasks["jvmJar"] as CopySpec)
                }
                register<JavaExec>("runLocally") {
                    group = "application"
                    setMain("com.app.BackendAppKt")
                    classpath = main.output.classesDirs
                    classpath += main.compileDependencyFiles
                }
            }
        }

    }
}