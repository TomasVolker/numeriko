# Numeriko Complex Module

This module contains an implementation of complex numbers,
complex n-dimensional arrays and associated algorithms.

Below is an example of an FFT implemented with 'numeriko-complex'.


```kotlin

fun ComplexArray1D.fft(): ComplexArray1D = when(size) {
    0, 1 -> this.copy()
    else -> {
        val even = this[0..Last step 2].fft()
        val odd  = this[1..Last step 2].fft()
        complexArray1D(size) { k ->
            even[k modulo size / 2] + exp(-2.j * PI * k / size) * odd[k modulo size / 2]
        }
    }
}

```