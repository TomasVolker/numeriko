package tomasvolker.numeriko.complex.transforms.fht.array

import tomasvolker.numeriko.complex.DoubleComplex
import tomasvolker.numeriko.complex.*
import tomasvolker.numeriko.complex.j
import tomasvolker.numeriko.complex.transforms.mapBoth
import tomasvolker.numeriko.complex.transforms.partitionByIndex
import tomasvolker.numeriko.core.primitives.isEven
import tomasvolker.numeriko.core.primitives.modulo
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun List<Double>.fht() = recursiveFht().map { it / sqrt(this.size.toDouble()) }

fun List<Double>.recursiveFht(): List<Double> = when {
    size < 2 -> this
    size % 2 != 0 -> throw IllegalArgumentException(
        "The Cooley Tukey Algorithm can only operate with length of a power of two"
    )
    else -> {
        val (evenFht, oddFht) = this.partitionByIndex { it.isEven() }.mapBoth {
            it.recursiveFht()
        }

        val halfSize = size / 2
        val frequency = 2 * PI / size

        List(size) { k ->
            evenFht[k modulo halfSize] +
            cos(frequency * k) * oddFht[k modulo halfSize] +
            sin(frequency * k) * oddFht[(halfSize - k) modulo halfSize]
        }
    }
}


fun List<Double>.dftFromDHT(): List<DoubleComplex> =
    fht().let { dht ->
        List(size) { k ->
            (dht[k] + dht[(-k) modulo size]) / 2 + (dht[k] - dht[(-k) modulo size]) / 2.j
        }
    }
