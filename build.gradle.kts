import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    kotlin("jvm") version "1.3.0" apply false
}

allprojects {

    group = "tomasvolker"
    version = "0.1"

    repositories {
        jcenter()
        maven { url = uri("http://dl.bintray.com/kyonifer/maven") }
    }

}

dependencies {

    subprojects.forEach {
        archives(it)
    }

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
