
plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":core"))
    testImplementation("junit:junit:4.12")
}
