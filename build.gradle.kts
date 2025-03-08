plugins {
    kotlin("jvm") version "2.1.0"
    id("org.jetbrains.dokka") version "1.9.20"

}

group = ""
version = ""

repositories {
    mavenCentral()
}

dependencies {

    // librerías para el logger
    implementation("org.lighthousegames:logging:1.5.0")
    implementation("ch.qos.logback:logback-classic:1.5.12")
    // librerías para la lectura de datos JSON
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    // librerias para lectura de datos XML
    implementation("io.github.pdvrieze.xmlutil:serialization-jvm:0.90.3")
    // mock
    testImplementation("io.mockk:mockk:1.13.16")
    // test normales
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    configurations["compileClasspath"].forEach { file ->
        from(zipTree(file.absoluteFile))
    }
    archiveFileName.set("futbol_team.jar")
}