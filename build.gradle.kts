plugins {
    java
    val flywayVersion = "10.13.0"
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.flywaydb.flyway") version flywayVersion
    id("com.vaadin") version "24.3.12"
}

group = "com.gorstreller"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.vaadin.com/vaadin-addons") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    runtimeOnly("org.postgresql:postgresql:42.7.3")
    implementation("org.flywaydb:flyway-core:10.13.0")
    implementation("org.flywaydb:flyway-database-postgresql:10.13.0")

    implementation("com.vaadin:vaadin-spring-boot-starter:24.3.12")
    implementation("com.vaadin:vaadin-bom:24.3.12")

    implementation ("javax.persistence:javax.persistence-api:2.2")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")

    implementation("com.amazonaws:aws-java-sdk-s3:1.12.729")

    implementation("redis.clients:jedis:5.1.5")
}

buildscript {
    dependencies {
        classpath("org.postgresql:postgresql:42.7.3")
        classpath("org.flywaydb:flyway-database-postgresql:10.13.0")
    }
}

flyway {
    url = "jdbc:postgresql://localhost:5432/manga-project"
    user = "postgres"
    val dbPassword = System.getenv("DB_PASSWORD") ?: ""
    password = dbPassword
    println("Using DB_PASSWORD: $dbPassword")
}

vaadin {
    productionMode = true
}

tasks.withType<Test> {
    useJUnitPlatform()
}
