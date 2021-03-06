import org.apache.tools.ant.filters.ReplaceTokens
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.kotlin.dsl.*
import org.gradle.language.jvm.tasks.ProcessResources

group = "frontend"
version = "0.0.1-SNAPSHOT"

buildscript {
    val kotlinVersion by extra { "1.2.51" }
    val kotlinTestVersion by extra { "2.0.7" }
    val springBootVersion by extra { "2.0.3.RELEASE" }
    val junitVersion by extra { "5.1.0" }
    val jacksonVersion by extra { "2.9.+" }
}
val kotlinVersion: String by extra
val kotlinTestVersion: String by extra
val springBootVersion: String by extra
val junitVersion: String by extra
val jacksonVersion: String by extra

repositories {
    jcenter()
    mavenCentral()
}

plugins {
    kotlin("jvm") version "1.2.51"
    kotlin("plugin.spring") version "1.2.51"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.2.51"
    id("org.springframework.boot") version "2.0.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.5.RELEASE"
    id("org.unbroken-dome.test-sets") version "1.5.0"
}

noArg {
    annotation("javax.persistence.Entity")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        apiVersion = "1.2"
        languageVersion = "1.2"
        suppressWarnings = true
    }
}

repositories {
    mavenCentral()
}


dependencies {
    compile(kotlin("stdlib", kotlinVersion))
    compile(kotlin("reflect", kotlinVersion))
    compile("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    compile("org.springframework.boot:spring-boot-starter-data-rest:$springBootVersion")
    compile("org.springframework.boot:spring-boot-starter-data-mongodb:$springBootVersion")
    compile("commons-fileupload:commons-fileupload:1.3.3")
    testCompile("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testCompile(kotlin("test"))
    testCompile("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}


val dbHost = System.getenv("DB_HOST") ?: "localhost"
val dbPort = System.getenv("DB_PORT") ?: 27017
val dbName = System.getenv("DB_NAME") ?: "templates"
val dbUser = System.getenv("DB_USER") ?: "mongo"
val dbPassword = System.getenv("DB_PASSWORD") ?: "mongo"

val dbProperties = mapOf(
    "dbHost" to dbHost,
    "dbPort" to dbPort,
    "dbName" to dbName,
    "dbUser" to dbUser,
    "dbPassword" to dbPassword
)

tasks.withType<ProcessResources> {
    filesMatching("application.properties") {
        expand(dbProperties)
    }
    onlyIf { file("src/main/resources/application.properties").exists() }
}


testSets {
    val intTest = create("integrationTest")
    add(intTest)
}

tasks.withType<Test> {
    useJUnitPlatform {
        // includeEngines("spek")
    }
    testLogging {
        events("PASSED", "FAILED", "SKIPPED")
    }
}

val webpack by tasks.creating(Exec::class) {
    commandLine("webpack")
}
/*
"src/main/resources/static/js/angular.min.js", "src/main/resources/static/js/templates.js", "$buildDir/resources/main/staticjs/bundle.js")

task webpack(type: Exec) {
    commandLine "$projectDir/node_modules/.bin/webpack", "src/main/resources/*.js", "$buildDir/resources/main/staticjs/bundle.js"
}
*/