import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

repositories {
    // KyPlot dependency
    maven { setUrl("https://raw.github.com/aliceinnets/maven-repository/master/") }
}

dependencies {
    compile(kotlin("stdlib"))
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "0.26.1-eap13")

    testCompile("junit:junit:4.12")
    testCompile(kotlin("test"))
    testCompile(files("libs/kyscript-0.1.jar"))
    testCompile(files("libs/kyplot-0.1.jar"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}