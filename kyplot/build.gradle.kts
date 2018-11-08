import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

version = "0.1"

dependencies {

    implementation(kotlin("stdlib-jdk8"))
    testImplementation("junit", "junit", "4.12")

    implementation(files("libs/kyscript-0.1.jar"))
    implementation(files("libs/kyscript-0.1-sources.jar"))

}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}