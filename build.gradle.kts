

plugins {
    id("java")
}

group = "org.luke"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.testng/testng
    testImplementation("org.testng:testng:7.7.1")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
}

tasks.test {
    useTestNG()
}