plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.danilopianini:gson-extras:0.2.1")
}

application {
    mainClass.set("ui.MainWindow")
}

tasks.test {
    useJUnitPlatform()
}