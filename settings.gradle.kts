pluginManagement {
    repositories {
        mavenCentral()
        maven { setUrl("https://plugins.gradle.org/m2/") }
    }
}

include("numeriko-core", "numeriko-complex", "kyscript", "kyplot", "sandbox")
