plugins {
    id("java")
}

group = "com.copago"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":coupang-shop-common"))
    implementation("org.telegram:telegrambots:5.2.0")
    implementation("org.jsoup:jsoup:1.15.3")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

configurations {
    val archivesBaseName = "coupang-shop-message-staging"
}

tasks.getByName<Jar>("jar") {
    enabled = false
    archiveBaseName.set("message-staging")
}

springBoot {
    buildInfo()
}