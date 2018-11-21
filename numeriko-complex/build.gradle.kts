
plugins {
    kotlin("jvm")
}

version = "0.0.1"

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":numeriko-core"))

    testImplementation("junit:junit:4.12")
    testImplementation(kotlin("test"))
}
