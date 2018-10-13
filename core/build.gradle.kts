
plugins {
    kotlin("jvm")
}

repositories {
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-eap") }
}

dependencies {
    compile(kotlin("stdlib"))
    testCompile("junit:junit:4.12")
    testCompile(kotlin("test"))
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "0.26.1-eap13")
}
