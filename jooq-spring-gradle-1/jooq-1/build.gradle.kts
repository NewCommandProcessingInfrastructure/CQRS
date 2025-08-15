import org.jooq.meta.kotlin.forcedType

val jooqVersion = "3.20.5"

buildscript {
    configurations["classpath"].resolutionStrategy.eachDependency {
        if (requested.group.startsWith("org.jooq") && requested.name.startsWith("jooq")) {
            useVersion("3.20.5")
        }
    }
}

plugins {
    java
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("nu.studer.jooq") version "10.1.1"
}

group = "com.java.jooq"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot - JOOQ"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.jooq:jooq:$jooqVersion")
    jooqGenerator("org.jooq:jooq-codegen:$jooqVersion")
    jooqGenerator("org.jooq:jooq-meta:$jooqVersion")
    jooqGenerator("org.jooq:jooq-meta-extensions:$jooqVersion")
    jooqGenerator("mysql:mysql-connector-java:8.0.33")

    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jooq") {
                useVersion(jooqVersion)
                because("Force all jOOQ modules to use the same version")
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jooq {
    version.set("3.20.5")
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)

            jooqConfiguration {
                logging = org.jooq.meta.jaxb.Logging.WARN

                jdbc.apply {
                    driver = "com.mysql.cj.jdbc.Driver"
                    url = "jdbc:mysql://localhost:3306/jooq_query?useSSL=false&serverTimezone=UTC"
                    user = "root"
                    password = "mysql"
                }

                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"

                    database.apply {
                        name = "org.jooq.meta.mysql.MySQLDatabase"
                        inputSchema = "jooq_query"

                        // Optional: forced types for MySQL
                        forcedTypes.apply {
                            forcedType {
                                name = "varchar"
                                includeExpression = ".*"
                                includeTypes = "JSON"
                            }
                            forcedType {
                                name = "boolean"
                                includeExpression = ".*"
                                includeTypes = "BIT"
                            }
                        }
                    }

                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }

                    target.apply {
                        packageName = "com.java.jooq.generated"
                        directory = "src/main/java/generated"
                    }

                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

// Add this after your jooqCodegenTask definition
tasks.named("clean") {
    doFirst {
        // adjust the path to your generated sources directory
        val generatedDir = file("src/main/java/generated")
        if (generatedDir.exists()) {
            println("Deleting generated sources at $generatedDir")
            generatedDir.deleteRecursively()
        }
    }
}