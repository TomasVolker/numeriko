
plugins {
    kotlin("jvm")
}

version = "0.0.1"

repositories {
    maven { url = uri("http://dl.bintray.com/kyonifer/maven") }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":numeriko-core"))
    implementation(project(":numeriko-complex"))
    implementation(project(":kyplot"))
    implementation(group = "com.kyonifer", name = "koma-core-ejml", version = "0.12")

    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "0.26.1-eap13")

    implementation("org.deeplearning4j:deeplearning4j-core:1.0.0-beta2")
    implementation("org.nd4j:nd4j-native-platform:1.0.0-beta2")

    testImplementation("junit:junit:4.12")
    testImplementation(kotlin("test"))
}
