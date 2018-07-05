
plugins {
    base
    kotlin("jvm") version "1.2.51" apply false
}

allprojects {

    group = "tomasvolker"
    version = "0.1-SNAPSHOT"

    repositories {
        jcenter()
    }

}

dependencies {

    subprojects.forEach {
        archives(it)
    }

}
