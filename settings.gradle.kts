rootProject.name = "spring-boot-testing"

include("todos-app", "todos-with-users")

pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        gradlePluginPortal()
    }
}
