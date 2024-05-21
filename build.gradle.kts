plugins {
    java
    val flywayVersion = "10.12.0"
    id("org.springframework.boot") version "3.2.3"
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
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.flywaydb:flyway-database-postgresql:10.12.0")
    implementation("com.vaadin:vaadin-spring-boot-starter:24.3.12")
    implementation("com.vaadin:vaadin-bom:24.3.12")
}

buildscript {
    dependencies {
        classpath("org.postgresql:postgresql:42.7.3")
        classpath("org.flywaydb:flyway-database-postgresql:10.12.0")
    }
}

flyway {
    url = "jdbc:postgresql://localhost:5432/manga-project"
    user = "postgres"
    val dbPassword = System.getenv("DB_PASSWORD") ?: ""
    password = dbPassword
    println("Using DB_PASSWORD: $dbPassword") // для отладки
}

vaadin {
    productionMode = false
}

tasks.withType<Test> {
    useJUnitPlatform()
}
