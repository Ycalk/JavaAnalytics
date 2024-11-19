plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.vk.api:sdk:1.0.16")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.slf4j:slf4j-api:1.7.36")
}

tasks.test {
    useJUnitPlatform()
}