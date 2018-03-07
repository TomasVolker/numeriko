
plugins {
    kotlin("jvm")
}

dependencies {
    compile(kotlin("stdlib"))
    compile(project(":core"))
    testCompile("junit:junit:4.12")
}
