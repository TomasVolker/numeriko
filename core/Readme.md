# Numeriko Core Module

This module contains the core interfaces for N-dimensional arrays 
and default implementations of the most common algorithms. It is 
experimental and currently in development so it may change without warning.

It's goal is to provide complete and efficient N-dimensional array containers
for general purposes, in particular for numerical computing, following the 
kotlin style and idiomatic practices.

##Construction

Generic arrays are constructed with factory functions based on kotlin standard
library or with the DSL.
```kotlin
// standard library style constructor
val anArray = array1DOf("a", "1D", "array") // Array1D<String>

// DSL constructor
val withDsl = A["another", "1D", "array"] // Array1D<String>

// lambda constructor
val withLambda = array1D(3) { i -> "data$i" } // [data0, data1, data2]

// 
val twoDimensions = array2D(2, 2) { i0, i1 -> "data$i0$i1" } // [[data00, data01], [data10, data11]]

```

In a very similar way primitive specializations are also constructed.
```kotlin
// standard library style constructor
val numbers = doubleArray1DOf(1.0, 2.0, 3.0) // DoubleArray1D

// DSL constructor, can use be constructed from various Number
val withDsl = D[4.0, 5, 6.5] // DoubleArray1D

// Can have more dimensions
val withDsl2D = D[D[4.0, 5,  6.5],
                  D[7.0, 8, -9  ]] // DoubleArray2D

// lambda constructor
val withLambda = doubleArray1D(4) { i -> 2.0 * i } // [0.0, 2.0, 4.0, 6.0]

// lambda constructor
val twoDimensions = doubleArray2D(4, 4) { i0, i1 -> if (i0 <= i1) 1.0 else 0.0 } // upper triangular

```

To obtain a mutable array, just call `asMutable()` to expose the mutable interface.

```kotlin
// There is also a zero initialized array factory function
val aLotOfZeros = doubleZeros(1000).asMutable() // MutableDoubleArray1D
val evenMoreZeros = doubleZeros(1000, 1000).asMutable() // MutableDoubleArray2D

```

## Basic Operations

### Access

For specialized interfaces, a get and set operator is available, for both
indices and ranges. In the case of ranges a view of the array is returned,
not a copy.

```kotlin
val myArray = doubleArray1D(10) { i -> i.toDouble() } // DoubleArray1D

myArray[4] // 4.0
myArray[2..6] // [2.0, 3.0, 4.0, 5.0, 6.0]
myArray[Last] // 9.0

val mutable = myArray.asMutable() // MutableDoubleArray1D

mutable[9] = -1.0
mutable[7..Last] // [7.0, 8.0, -1.0]
myArray[Last] // -1.0

val myArray2D = doubleArray(4, 3) { i0, i1 -> 2.0 * i0 - i1 } // DoubleArray2D

myArray2D[0, 1] // -1.0
myArrat2D[Last, Last] // 5.0

```

### Operators

Basic operator overloads are implemented to have a clean syntax on element
wise operations and index access.

```kotlin
// myArray and yourArray are DoubleArray1D

myArray + yourArray // Element wise addition
myArray - yourArray // Element wise subtraction
myArray * yourArray // Element wise multiplication
myArray / yourArray // Element wise division

3.14 * myArray // Element wise multiplication
```

##The ArrayND interfaces

Al the array interfaces comply with the `Collection<T>` interface and
follow kotlin's convention for separation of the read-only and the
mutable interfaces. This enables out variance on the read-only interfaces
as well as self-documenting signatures specifying if the array is being 
modified or just read from.

The parent interface is `ArrayND<T>` which is read-only, with 
`MutableArrayND<T>` inheriting from it and extending it with content modifying
methods. As N-dimensional arrays tend to occupy a lot of memory, it is usually
desired to access the array with a mutable interface. This is why `ArrayND`
implementations must implement `asMutable()` to provide a mutable interface
to it.

In addition to `ArrayND<T>`, specific implementations for primitives are planned
to be developed, such `DoubleArrayND` which is currently available. This follows
kotlin's standard library conventions for `Array` such as `DoubleArray` or `IntArray`.
It's mutable versions are also available, for example `MutableDoubleArrayND`.

For additional type safety, low rank arrays have specialized interfaces, such
as `Array1D<T>` and `Array2D<T>`. Again, primitive specializations and mutable
versions are also available (e.g. `MutableDoubleArray2D`).


