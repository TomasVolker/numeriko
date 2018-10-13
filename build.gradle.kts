
plugins {
    base
    kotlin("jvm") version "1.3.0-rc-146" apply false
}

allprojects {

    group = "tomasvolker"
    version = "0.1"

    repositories {
        jcenter()
    }

}

dependencies {

    subprojects.forEach {
        archives(it)
    }

}
