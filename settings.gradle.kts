pluginManagement {
    repositories {
        mavenCentral()
        maven { setUrl("https://plugins.gradle.org/m2/") }
    }
}

include("numeriko-core", "numeriko-complex", "numeriko-low-rank")
