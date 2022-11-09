plugins {
    kotlin("jvm") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "dev.kobalt"
version = "0000.00.00.00.00.00.000"

repositories {
    mavenCentral()
    maven(url = "https://maven.google.com")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

fun kobalt(module: String, version: String) = "dev.kobalt:$module:$version"
fun ktor(module: String, version: String) = "io.ktor:ktor-$module:$version"
fun exposed(module: String, version: String) = "org.jetbrains.exposed:exposed-$module:$version"
fun general(module: String, version: String) = "$module:$version"
fun kotlinx(module: String, version: String) = "org.jetbrains.kotlinx:kotlinx-$module:$version"
fun kotlinw(module: String, version: String) = "org.jetbrains.kotlin-wrappers:kotlin-$module:$version"

fun DependencyHandler.httpClient() {
    implementation(ktor("client-apache", "2.1.3"))
    implementation(ktor("server-netty", "2.1.3"))
    implementation(ktor("server-status-pages", "2.1.3"))
    implementation(ktor("server-html-builder", "2.1.3"))
}

fun DependencyHandler.serialization() {
    implementation(kotlinx("serialization-json", "1.4.1"))
    implementation(kotlinx("serialization-core", "1.4.1"))
}

fun DependencyHandler.commandLineInterface() {
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.3")
}

fun DependencyHandler.standardLibrary() {
    implementation(kotlin("stdlib", "1.7.21"))
}

fun DependencyHandler.logger() {
    implementation(general("org.slf4j:slf4j-simple", "1.7.35"))
}

fun DependencyHandler.htmlParser() {
    implementation(general("org.jsoup:jsoup", "1.14.3"))
}

fun DependencyHandler.htmlDsl() {
    implementation(kotlinx("html-jvm", "0.7.3"))
}

fun DependencyHandler.cssDsl() {
    implementation(kotlinw("css-jvm", "1.0.0-pre.298-kotlin-1.6.10"))
}

dependencies {
    standardLibrary()
    httpClient()
    htmlParser()
    htmlDsl()
    cssDsl()
    logger()
    serialization()
    commandLineInterface()
    implementation("org.jetbrains.xodus:xodus-openAPI:2.0.1")
    implementation("org.jetbrains.xodus:xodus-vfs:2.0.1")
    implementation("org.jetbrains.xodus:xodus-environment:2.0.1")
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveFileName.set("waybackmachineproxy.jar")
        mergeServiceFiles()
        // minimize()
        manifest {
            attributes("Main-Class" to "dev.kobalt.waybackmachineproxy.jvm.MainKt")
        }
    }
}