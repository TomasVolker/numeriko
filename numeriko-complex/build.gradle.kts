import com.jfrog.bintray.gradle.BintrayExtension

plugins {
    kotlin("jvm")
    `maven-publish`
    id("com.jfrog.bintray")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":array-core"))

    testImplementation("junit:junit:4.12")
    testImplementation(kotlin("test"))
}

val projectGroup   = "tomasvolker"
val projectName    = "array-complex"
val projectVersion = "0.0.2"

version = projectVersion

val sourcesJar = tasks.create<Jar>("sourcesJar") {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    classifier = "sources"
    from(sourceSets["main"].allSource)
}


publishing {
    publications {
        create("library", MavenPublication::class) {
            groupId    = projectGroup
            artifactId = projectName
            version    = projectVersion
            from(components.findByName("java"))
            artifact(sourcesJar)
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key  = System.getenv("BINTRAY_KEY")
    publish = true
    setPublications("library")
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = projectName
        userOrg = projectGroup
        vcsUrl = "https://github.com/tomasvolker/array/gradle-bintray-plugin.git"
        description = "N-dimensional complex array library for Kotlin"
        setLabels("kotlin")
        setLicenses("Apache-2.0")
        desc = description
        version(delegateClosureOf<BintrayExtension.VersionConfig> {
            name = projectVersion
        })
    })
}
