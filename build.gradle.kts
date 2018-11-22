
plugins {
    kotlin("jvm") version "1.3.0"
    id("com.jfrog.bintray") version "1.8.4"
}

val projectGroup = "tomasvolker"

allprojects {

    group = projectGroup

    repositories {
        mavenCentral()
        jcenter()
        maven {
            setUrl("http://dl.bintray.com/tomasvolker/maven")
        }
    }

}
