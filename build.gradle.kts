

plugins {
    id("java")
}

group = "org.luke"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    // https://mvnrepository.com/artifact/org.testng/testng
    testImplementation("org.testng:testng:7.7.1")
    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20230227")
    // https://mvnrepository.com/artifact/org.glassfish/javax.json
    implementation("org.glassfish:javax.json:1.1.4")


    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
}

tasks.test {
    useTestNG()
}