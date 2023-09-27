plugins {
    id("java-library")
}

group = "rs.iggy"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.httpcomponents.client5:httpclient5:5.2.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.15.2")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testRuntimeOnly("ch.qos.logback:logback-classic:1.4.11")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
