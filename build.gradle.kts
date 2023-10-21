plugins {
    java
}

repositories {
    mavenCentral()
}

java.toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
}

group = "de.cofinpro"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.21.0")
    implementation("info.picocli:picocli:4.7.5")
    val jacksonVersion = "2.15.3"
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
    implementation("org.yaml:snakeyaml:2.2")

    val lombokVersion = "1.18.30"
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.6.0")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}