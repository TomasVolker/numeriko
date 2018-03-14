package tomasvolker.numeriko.complex.fft

import tomasvolker.numeriko.complex.DoubleComplex
import tomasvolker.numeriko.complex.unaryDoubleComplex

fun fft(
        array: Array<DoubleComplex>,
        destination: Array<DoubleComplex> = Array(array.size) { DoubleComplex(0.0)}
): Array<DoubleComplex> {

    cooleyTukeyAlgorithm(
            array = array,
            offset = 0,
            stride = 1,
            size = array.size,
            destination = destination,
            destOffset = 0,
            inverse = false
    )

    return destination
}

fun ifft(
        array: Array<DoubleComplex>,
        destination: Array<DoubleComplex> = Array(array.size) { DoubleComplex(0.0)}
): Array<DoubleComplex> {

    cooleyTukeyAlgorithm(
            array = array,
            offset = 0,
            stride = 1,
            size = array.size,
            destination = destination,
            destOffset = 0,
            inverse = true
    )

    for (n in array.indices) {
        destination[n] = destination[n] / array.size
    }

    return destination
}

fun cooleyTukeyAlgorithm(
        array: Array<DoubleComplex>,
        offset: Int,
        stride: Int,
        size: Int,
        destination: Array<DoubleComplex>,
        destOffset: Int,
        inverse: Boolean
) {

    if (size == 1) {
        destination[destOffset] = array[offset]
        return
    }

    if (size % 2 != 0) {
        throw IllegalArgumentException(
                "The Cooley Tukey Algorithm can only operate on an jvm with length of a power of two"
        )
    }

    val halfSize = size / 2

    cooleyTukeyAlgorithm(
            array = array,
            offset = offset,
            stride = 2 * stride,
            size = halfSize,
            destination = destination,
            destOffset = destOffset,
            inverse = inverse
    )

    cooleyTukeyAlgorithm(
            array = array,
            offset = offset + stride,
            stride = 2 * stride,
            size = halfSize,
            destination = destination,
            destOffset = destOffset + halfSize,
            inverse = inverse
    )

    val twiddleSign = if (inverse) 1.0 else -1.0

    for (k in 0 until halfSize) {

        val twiddle = unaryDoubleComplex(twiddleSign * 2 * Math.PI * k.toDouble() / halfSize.toDouble())

        val evenIndex = destOffset + k
        val oddIndex  = evenIndex + halfSize

        val even = destination[evenIndex]
        val odd  = destination[oddIndex]

        destination[evenIndex] = even + twiddle * odd
        destination[oddIndex]  = even - twiddle * odd

    }

}
