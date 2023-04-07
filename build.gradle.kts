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
    val jacksonVersion = "2.14.2"
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
    implementation("org.yaml:snakeyaml:2.0")

    val lombokVersion = "1.18.26"
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.mockito:mockito-junit-jupiter:5.2.0")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}