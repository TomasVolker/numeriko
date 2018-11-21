
buildscript {

    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.0")
    }

}

allprojects {

    group = "tomasvolker"

    repositories {
        mavenCentral()
        jcenter()
    }

}
