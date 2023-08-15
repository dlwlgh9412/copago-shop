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
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    implementation("org.springframework.boot:spring-boot-starter-aop")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.getByName<Jar>("jar") {
    enabled = false
}