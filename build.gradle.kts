plugins {
    java
}

repositories {
    mavenCentral()
}

group = "de.cofinpro"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.20.0")
    implementation("info.picocli:picocli:4.7.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.2")
    implementation("org.yaml:snakeyaml:2.0")

    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.mockito:mockito-junit-jupiter:5.1.1")

    testCompileOnly("org.projectlombok:lombok:1.18.26")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.26")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}