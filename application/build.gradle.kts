dependencies {
    implementation(project(":adapter:outbound"))
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.1")
}
