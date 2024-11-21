rootProject.name = "spring-boot-testing"

include("todos-app", "todos-with-users")

pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        gradlePluginPortal()
    }
    plugins {
        id("org.springframework.boot") version "3.4.0-RC1"
        id("io.spring.dependency-management") version "1.1.6"
        id("io.spring.javaformat") version "0.0.43"
    }
}