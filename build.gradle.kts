

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
    implementation("org.testng:testng:7.1.0")



    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
    implementation("org.seleniumhq.selenium:selenium-java:4.8.3")

    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-android-driver
    implementation("org.seleniumhq.selenium:selenium-android-driver:2.39.0")
// https://mvnrepository.com/artifact/io.appium/java-client
    implementation("io.appium:java-client:8.3.0")

    implementation ("org.slf4j:slf4j-api:1.7.33")
    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20230227")
    // https://mvnrepository.com/artifact/org.glassfish/javax.json
    implementation("org.glassfish:javax.json:1.1.4")
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10.1")


    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
    // https://mvnrepository.com/artifact/org.testng/testng
    testImplementation("org.testng:testng:7.7.1")
    testImplementation("junit:junit:4.13.1")
    // https://mvnrepository.com/artifact/io.rest-assured/rest-assured
    testImplementation("io.rest-assured:rest-assured:5.3.0")

}

tasks.test {
    useTestNG()
}
