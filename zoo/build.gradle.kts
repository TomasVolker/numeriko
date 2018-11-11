import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":core"))
    implementation(project(":complex"))
    implementation(project(":kyplot"))
    implementation(group = "com.kyonifer", name = "koma-core-ejml", version = "0.12")

    implementation("org.deeplearning4j:deeplearning4j-core:1.0.0-beta2")
    implementation("org.nd4j:nd4j-native-platform:1.0.0-beta2")

    testImplementation("junit:junit:4.12")
    testImplementation(kotlin("test"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
