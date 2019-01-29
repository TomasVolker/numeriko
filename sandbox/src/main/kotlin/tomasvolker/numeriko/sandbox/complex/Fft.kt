package tomasvolker.numeriko.sandbox.complex

import tomasvolker.numeriko.complex.primitives.exp
import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array2d.ComplexArray2D
import tomasvolker.numeriko.complex.interfaces.arraynd.ComplexArrayND
import tomasvolker.numeriko.complex.interfaces.arraynd.unsafeGetView
import tomasvolker.numeriko.complex.interfaces.factory.complexArray1D
import tomasvolker.numeriko.complex.interfaces.factory.complexZeros
import tomasvolker.numeriko.complex.primitives.j
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.index.rangeTo
import tomasvolker.numeriko.core.index.step
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices0
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices1
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.performance.fastForEachIndices
import tomasvolker.numeriko.core.primitives.modulo
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun dftFromDHT(
        dht: DoubleArray1D
): ComplexArray1D =
        complexArray1D(
                size = dht.size,
                initReal = { k ->  (dht[k] + dht[(-k) modulo dht.size]) / 2 },
                initImag = { k -> -(dht[k] - dht[(-k) modulo dht.size]) / 2 }
        )


fun DoubleArray1D.fft() = dftFromDHT(this.fht())


fun ComplexArray1D.fft(): ComplexArray1D = when(size) {
    0, 1 -> this
    else -> {
        val even = this[0..Last step 2].fft()
        val odd  = this[1..Last step 2].fft()
        complexArray1D(size) { k ->
            even[k modulo size / 2] + exp(-2.j * PI * k / size) * odd[k modulo size / 2]
        }
    }
}

fun ComplexArray1D.ifft(): ComplexArray1D {
    val fft = fft()
    return complexArray1D(size) { k ->
        fft[-k modulo size] / size
    }
}

fun DoubleArray1D.fht(): DoubleArray1D = when(size) {
    0, 1 -> this
    else -> {
        val even = this[0..Last step 2].fht()
        val odd  = this[1..Last step 2].fht()

        doubleArray1D(size) { k ->
            even[k modulo size / 2] +
                    cos(2 * PI * k / size) * odd[k modulo size / 2] +
                    sin(2 * PI * k / size) * odd[(size / 2 - k) modulo size / 2]
        }
    }
}

fun ComplexArray2D.fft2D(): ComplexArray2D {

    val horizontal = complexZeros(shape0, shape1).asMutable()

    for (n0 in indices0) {
        horizontal[n0, All] = this[n0, All].fft()
    }

    val result = complexZeros(shape0, shape1).asMutable()

    for (n1 in indices1) {
        result[All, n1] = horizontal[All, n1].fft()
    }

    return result
}

fun ComplexArray2D.ifft2D(): ComplexArray2D {

    val horizontal = complexZeros(shape0, shape1).asMutable()

    for (i0 in indices0) {
        horizontal[i0, All] = this[i0, All].ifft()
    }

    val result = complexZeros(shape0, shape1).asMutable()

    for (i1 in indices1) {
        result[All, i1] = horizontal[All, i1].ifft()
    }

    return result
}


fun DoubleArray2D.fft2D(): ComplexArray2D {

    val horizontal = complexZeros(shape0, shape1).asMutable()

    for (i0 in indices0) {
        horizontal[i0, All] = this[i0, All].fft()
    }

    val result = complexZeros(shape0, shape1).asMutable()

    for (i1 in indices1) {
        result[All, i1] = horizontal[All, i1].fft()
    }

    return result
}

fun ComplexArrayND.fft(axis: Int): ComplexArrayND {

    val result = complexZeros(this.shape).asMutable()

    fastForEachIndices { indices ->

        if (indices[axis] == 0) {

            val indxs = Array<Any>(rank) { a ->
                if (a == axis) All else 0
            }
            result.unsafeGetView(*indxs).setValue(
                    this.unsafeGetView(*indxs).as1D().fastFourierTransform()
            )
        }

    }

    return result
}
