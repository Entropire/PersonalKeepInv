plugins {
    java
}

group = "dev.entropire"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 8
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:-options")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.register<Copy>("deploy") {
    from(tasks.jar)
    into(providers.gradleProperty("serverDir").map { "$it/plugins" })
}

tasks.jar {
    archiveBaseName = "PersonalKeepInv"
    from("LICENSE") {
        into("META-INF")
    }
}
