plugins {
    id("java-library")
    id("maven-publish")
    id("org.jreleaser") version ("1.14.0")
}

group = "rs.iggy"
version = "0.1.0"

repositories {
    mavenCentral()
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation("org.apache.httpcomponents.client5:httpclient5:5.2.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.15.2")
    implementation("org.apache.commons:commons-lang3:3.13.0")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("io.projectreactor:reactor-core:3.5.10")
    implementation("io.projectreactor.netty:reactor-netty-core:1.1.11")
    testImplementation("org.testcontainers:testcontainers:1.19.0")
    testImplementation("org.testcontainers:junit-jupiter:1.19.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testRuntimeOnly("ch.qos.logback:logback-classic:1.4.11")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "iggy-java-sdk"

            from(components["java"])

            pom {
                name = "Iggy Java Client SDK"
                description = "Official Java client SDK for Iggy.rs message streaming"
                url = "https://github.com/iggy-rs/iggy-java-client"
                licenses {
                    license {
                        name = "MIT License"
                        url = "https://github.com/iggy-rs/iggy-java-client/blob/main/LICENSE"
                    }
                }
                developers {
                    developer {
                        id = "mmodzelewski"
                        name = "Maciej Modzelewski"
                        email = "maciej@modzelewski.dev"
                    }
                }
                scm {
                    url = "https://github.com/iggy-rs/iggy-java-client"
                    connection = "scm:git:git://github.com/iggy-rs/iggy-java-client.git"
                    developerConnection = "scm:git:git://github.com/iggy-rs/iggy-java-client.git"
                }
            }
        }
    }

    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("staging-deploy"))
        }
    }
}

jreleaser {
    signing {
        setActive("ALWAYS")
        armored = true
    }
    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    setActive("ALWAYS")
                    url = "https://central.sonatype.com/api/v1/publisher"
                    stagingRepository("build/staging-deploy")
                }
            }
        }
    }
}
