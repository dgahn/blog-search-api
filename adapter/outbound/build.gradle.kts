apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

dependencies {
    implementation(project(":domain"))
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.h2database:h2:1.4.200")
}
