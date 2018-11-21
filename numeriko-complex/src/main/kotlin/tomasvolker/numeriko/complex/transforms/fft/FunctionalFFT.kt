package tomasvolker.numeriko.complex.transforms.fft

import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.complex.primitives.toComplex
import tomasvolker.numeriko.complex.transforms.mapBoth
import tomasvolker.numeriko.complex.transforms.partitionByIndex
import tomasvolker.numeriko.complex.primitives.unaryDoubleComplex
import tomasvolker.numeriko.core.primitives.isEven
import tomasvolker.numeriko.core.primitives.modulo
import kotlin.math.PI
import kotlin.math.sqrt

fun List<Complex>.fft() = cooleyTukey2(inverse = false).map { it / sqrt(size.toDouble()) }
fun List<Complex>.ifft() = cooleyTukey2(inverse = true).map { it / sqrt(size.toDouble()) }

fun List<Complex>.cooleyTukey2(inverse: Boolean = false): List<Complex> = when {
    size < 2 -> this
    size % 2 != 0 -> throw IllegalArgumentException(
        "The Cooley Tukey Algorithm can only operate with length of a power of two"
    )
    else -> {
        val (evenFft, oddFft) = this.partitionByIndex { it.isEven() }.mapBoth {
            it.cooleyTukey2()
        }

        val frequency = (if(inverse) -1.0 else 1.0) * 2 * PI / size

        val halfSize = size / 2

        List(size) { k ->
            evenFft[k modulo halfSize] + unaryDoubleComplex(frequency * k) * oddFft[k modulo halfSize]
        }
    }
}

fun <T> List<List<T>>.transposed(): List<List<T>> =
    List(first().size) { n2 ->
        List(this.size) { n1 -> this[n1][n2] }
    }

inline fun <T> List<List<T>>.deepMapIndexed(operation: (i0: Int, i1: Int, value: T)->T): List<List<T>> =
    mapIndexed { i0, list ->
        list.mapIndexed { i1, value -> operation(i0, i1, value) }
    }

inline fun <T> List<List<T>>.deepMap(operation: (T)->T): List<List<T>> =
    map { it.map(operation) }

fun <T> Boolean.unfold(trueVal: T, falseVal: T) =
    if(this) trueVal else falseVal

fun twiddle(inverse: Boolean, size: Int, exponent: Int) =
        unaryDoubleComplex(inverse.unfold(-1.0, 1.0) * 2 * PI / size * exponent)

fun List<Complex>.cooleyTukey(
    inverse: Boolean = false,
    timeDecimation: Int = 2
): List<Complex> = when(size) {
    0, 1 -> this
    2 -> listOf(this[0] + this[1], this[0] - this[1])
    else -> this.chunked(timeDecimation)
                .transposed()
                .map { it.cooleyTukey(inverse) }
                .deepMapIndexed { n1, k2, x ->
                    x * twiddle(inverse, size, n1 * k2)
                }
                .transposed()
                .map { it.cooleyTukey(inverse) }
                .transposed()
                .flatten()
}

inline fun <T> Iterable<T>.sumByComplex(operation: (T)-> Complex) =
        fold(0.0.toComplex()) { acc, next -> acc + operation(next) }

fun List<Complex>.dft(inverse: Boolean) =
        List(size) { k ->
            (0 until size).sumByComplex { n ->
                this[n] * twiddle(inverse, size, k * n)
            }
        }
