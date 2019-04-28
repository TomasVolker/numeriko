# NumeriKo

NDArray library for Kotlin in development.

Modules:

- numeriko-core: ArrayND interfaces and operation definitions along with a basic backend
- numeriko-complex: Complex number implementation

## Build instructions

Add the following maven repository to the gradle build script:

##### Groovy DSL
```groovy
repositories {
    maven {
        url "http://dl.bintray.com/tomasvolker/maven"
    }
}
```
##### Kotlin DSL
```kotlin
repositories {
    maven {
        setUrl("http://dl.bintray.com/tomasvolker/maven")
    }
}
```

Then add the following dependencies:

##### Groovy DSL
```groovy
dependencies {
    // Core library
    implementation group: "tomasvolker", name: array, version: "0.0.2"
    
    // Complex arrays
    implementation group: "tomasvolker", name: array, version: "0.0.1"
    
}

```
##### Kotlin DSL
```kotlin
dependencies {
    // Core library
    implementation(group = "tomasvolker", name = "array", version = "0.0.2")
    
    // Complex arrays
    implementation(group = "tomasvolker", name = "array", version = "0.0.1")
    
}
```

