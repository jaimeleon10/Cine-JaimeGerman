// Variables del properties
val loggingVersion: String by project
val logbackVersion: String by project
val kotlinSerializationVersion: String by project
val mybatisVersion: String by project
val sqliteJdbcVersion: String by project
val mockkVersion: String by project
val sqldelightVersion: String by project
val koinBomVersion: String by project
val resultRopVersion: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    // Plugin para la generación de código SQLDelight
    id("app.cash.sqldelight") version "2.0.2"
    application
    id("org.jetbrains.dokka") version "1.9.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

dependencies {
    // Driver de la base de datos a usar
    // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
    implementation("org.xerial:sqlite-jdbc:$sqliteJdbcVersion")
    // SQLDelight para SQLite
    implementation("app.cash.sqldelight:sqlite-driver:$sqldelightVersion")
    // Para cargar scripts de la base de datos
    implementation("org.mybatis:mybatis:$mybatisVersion")
    // logger
    implementation("org.lighthousegames:logging:$loggingVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    //serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
    //Koin
    implementation(platform("io.insert-koin:koin-bom:$koinBomVersion"))
    implementation("io.insert-koin:koin-core") // Core
    implementation("io.insert-koin:koin-test") // Para test y usar checkModules
    // Result ROP
    implementation("com.michael-bull.kotlin-result:kotlin-result:$resultRopVersion")

    // tests
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    // Mockk
    testImplementation("io.mockk:mockk:$mockkVersion")
    // Koin
    testImplementation("io.insert-koin:koin-test-junit5") // Para test con JUnit5
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
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

// Configuración del plugin de SqlDeLight
sqldelight {
    databases {
        // Nombre de la base de datos
        create("Database") {
            // Paquete donde se generan las clases
            packageName.set("dev.jaimeleon.database")
        }
    }
}