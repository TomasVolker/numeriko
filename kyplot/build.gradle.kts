
plugins {
    kotlin("jvm")
}

version = "0.0.1"

dependencies {

    implementation(kotlin("stdlib"))
    implementation(project(":numeriko-core"))
    implementation(project(":kyscript"))

    testImplementation("junit", "junit", "4.12")
}
