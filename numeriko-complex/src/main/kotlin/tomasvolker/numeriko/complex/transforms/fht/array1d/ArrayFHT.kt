package tomasvolker.numeriko.complex.transforms.fht.array1d

import tomasvolker.numeriko.complex.primitives.ComplexArray
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros
import tomasvolker.numeriko.core.primitives.modulo
import tomasvolker.numeriko.core.primitives.sqrt
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun dftFromDHT(
        array: DoubleArray1D,
        destination: ComplexArray = ComplexArray(array.size)
): ComplexArray {

    val dht = fht(array)

    for (k in 0 until array.size) {
        destination.realArray[k] =  (dht[k] + dht[(-k) modulo dht.size]) / 2
        destination.imagArray[k] = -(dht[k] - dht[(-k) modulo dht.size]) / 2
    }

    return destination
}

fun DoubleArray1D.fastHartleyTransform(): DoubleArray1D = fht(this)

fun fht(
    array: DoubleArray1D,
    destination: MutableDoubleArray1D = doubleZeros(array.size).asMutable()
): DoubleArray1D {

    cooleyTukeyFht(
            array = array,
            offset = 0,
            stride = 1,
            size = array.size,
            destination = destination,
            destOffset = 0
    )

    val normalization = sqrt(array.size).toDouble()

    return destination.applyDiv(normalization)
}

fun cooleyTukeyFht(
    array: DoubleArray1D,
    offset: Int,
    stride: Int,
    size: Int,
    destination: MutableDoubleArray1D,
    destOffset: Int
) {

    if (size == 1) {
        destination[destOffset] = array[offset]
        return
    }

    if (size % 2 != 0) {
        throw IllegalArgumentException(
            "The Cooley Tukey Algorithm can only operate with length of a power of two"
        )
    }

    val halfSize = size / 2
    val quarterSize = halfSize / 2

    cooleyTukeyFht(
            array = array,
            offset = offset,
            stride = 2 * stride,
            size = halfSize,
            destination = destination,
            destOffset = destOffset
    )

    cooleyTukeyFht(
            array = array,
            offset = offset + stride,
            stride = 2 * stride,
            size = halfSize,
            destination = destination,
            destOffset = destOffset + halfSize
    )

    // Special cases of k = 0 and  k = size / 2
    val evenIndex0 = destOffset
    val oddIndex0  = destOffset + halfSize

    val even0 = destination[evenIndex0]
    val odd0  = destination[oddIndex0]

    destination[evenIndex0] = even0 + odd0
    destination[oddIndex0]  = even0 - odd0

    // General case
    for (k1 in 1..quarterSize) {

        val k2 = (halfSize - k1) % halfSize

        val phase1 = 2 * PI * k1.toDouble() / size
        val twiddleCos = cos(phase1)
        val twiddleSin = sin(phase1)

        val evenIndex1 = destOffset + k1
        val evenIndex2 = destOffset + k2
        val oddIndex1  = halfSize + evenIndex1
        val oddIndex2  = halfSize + evenIndex2

        val even1 = destination[evenIndex1]
        val even2 = destination[evenIndex2]
        val odd1  = destination[oddIndex1]
        val odd2  = destination[oddIndex2]

        val oddPart1 = twiddleCos * odd1 + twiddleSin * odd2
        val oddPart2 = - twiddleCos * odd2 + twiddleSin * odd1

        destination[evenIndex1] = even1 + oddPart1
        destination[evenIndex2] = even2 + oddPart2
        destination[oddIndex1]  = even1 - oddPart1
        destination[oddIndex2]  = even2 - oddPart2

    }

}

