plugins {
    id("java")
}

group = "com.anonymouslyfast"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("net.minestom:minestom:2025.10.18-1.21.10")
}

tasks.test {
    useJUnitPlatform()
}