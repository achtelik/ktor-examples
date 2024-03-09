val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.22"
    id("io.ktor.plugin") version "2.3.7"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    id("org.openapi.generator") version "7.3.0"
}

group = "it.achtelik"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor Server
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-swagger-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-resources:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm")
    // Ktor Other
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    // Ktor Client
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    // Other
    implementation("ch.qos.logback:logback-classic:$logback_version")
    // Test
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}


// ----------- Openapi Generator --------------------------------
openApiGenerate {
    configOptions.put("enumPropertyNaming", "UPPERCASE")
    generatorName.set("kotlin-server")
    inputSpec.set("$rootDir/src/main/resources/openapi/documentation.yaml")
    library.set("ktor")
    outputDir.set("$rootDir")
    packageName.set("it.achtelik.ktorOpenapi.generated.openapi")
    templateDir.set("$rootDir/.openapi-generator/template")
    //ignoreFileOverride.set("$rootDir/.openapi-generator/.openapi-generator-ignore")
}