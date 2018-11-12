pluginManagement {
    repositories {
        maven { setUrl("http://dl.bintray.com/kotlin/kotlin-eap") }

        mavenCentral()

        maven { setUrl("https://plugins.gradle.org/m2/") }
    }
}
include("core", "kyplot", "complex", "sandbox")
