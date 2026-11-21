plugins {
    java
}

group = "dev.entropire"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // Match the build number of your server jar (paper-26.3-257.jar -> build 257)
    compileOnly("io.papermc.paper:paper-api:26.3.build.257-stable")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filesMatching("plugin.yml") {
        expand(props)
    }
}

// ./gradlew deploy  -> builds the jar and copies it into the server's plugins folder
tasks.register<Copy>("deploy") {
    from(tasks.jar)
    into(providers.gradleProperty("serverDir").map { "$it/plugins" })
}

tasks.jar{
    from("LICENSE"){
        into("META-INF")
    }
}