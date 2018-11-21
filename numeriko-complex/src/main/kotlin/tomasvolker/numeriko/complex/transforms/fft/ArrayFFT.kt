package tomasvolker.numeriko.complex.transforms.fft

import tomasvolker.numeriko.complex.*

import tomasvolker.numeriko.complex.transforms.replaceAll
import tomasvolker.numeriko.core.primitives.isPowerOf2
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun fft(
        array: Array<Complex>,
        destination: Array<Complex> = Array(array.size) { 0.0.j },
        inverse: Boolean = false
): Array<Complex> {

    if (!array.size.isPowerOf2()) {
        throw IllegalArgumentException(
            "The Cooley Tukey Algorithm can only operate with length of a power of two"
        )
    }

    cooleyTukeyAlgorithm(
            array = array,
            offset = 0,
            stride = 1,
            size = array.size,
            destination = destination,
            destOffset = 0,
            inverse = inverse
    )

    val normalization = sqrt(array.size.toDouble())

    return destination.apply { replaceAll { it / normalization } }
}

fun ifft(
        array: Array<Complex>,
        destination: Array<Complex> = Array(array.size) { 0.0.j }
): Array<Complex> = fft(
        array = array,
        destination = destination,
        inverse = true
)

fun cooleyTukeyAlgorithm(
        array: Array<Complex>,
        offset: Int,
        stride: Int,
        size: Int,
        destination: Array<Complex>,
        destOffset: Int,
        inverse: Boolean
) {

    if (size == 1) {
        destination[destOffset] = array[offset]
        return
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

        val twiddle = unaryDoubleComplex(twiddleSign * 2 * PI * k.toDouble() / size)

        val evenIndex = destOffset + k
        val oddIndex  = evenIndex + halfSize

        val even = destination[evenIndex]
        val odd  = destination[oddIndex]

        destination[evenIndex] =  even + twiddle * odd
        destination[oddIndex]  =  even - twiddle * odd

    }

}


fun fft(
        array: ComplexArray,
        destination: ComplexArray = ComplexArray(array.size),
        inverse: Boolean = false
): ComplexArray {

    if (!array.size.isPowerOf2()) {
        throw IllegalArgumentException(
            "The Cooley Tukey Algorithm can only operate with length of a power of two"
        )
    }

    cooleyTukeyAlgorithm(
            array = array,
            offset = 0,
            stride = 1,
            size = array.size,
            destination = destination,
            destOffset = 0,
            inverse = inverse
    )

    val normalization = sqrt(array.size.toDouble())

    return destination.apply {
        for (i in 0 until size) {
            destination.realArray[i] /= normalization
            destination.imagArray[i] /= normalization
        }
    }
}

fun ifft(
        array: ComplexArray,
        destination: ComplexArray = ComplexArray(array.size)
): ComplexArray = fft(
        array = array,
        destination = destination,
        inverse = true
)

fun cooleyTukeyAlgorithm(
        array: ComplexArray,
        offset: Int,
        stride: Int,
        size: Int,
        destination: ComplexArray,
        destOffset: Int,
        inverse: Boolean
) {

    if (size == 1) {
        destination.realArray[destOffset] = array.realArray[offset]
        destination.imagArray[destOffset] = array.imagArray[offset]
        return
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

        val twiddleReal = cos(twiddleSign * 2 * PI * k.toDouble() / size)
        val twiddleImag = sin(twiddleSign * 2 * PI * k.toDouble() / size)

        val evenIndex = destOffset + k
        val oddIndex  = evenIndex + halfSize

        val evenReal = destination.realArray[evenIndex]
        val evenImag = destination.imagArray[evenIndex]

        val oddReal  = destination.realArray[oddIndex]
        val oddImag  = destination.imagArray[oddIndex]

        // destination[evenIndex] =  even + twiddle * odd
        destination.realArray[evenIndex] =  evenReal + twiddleReal * oddReal - twiddleImag * oddImag
        destination.imagArray[evenIndex] =  evenImag + twiddleReal * oddImag + twiddleImag * oddReal

        // destination[oddIndex]  =  even - twiddle * odd
        destination.realArray[oddIndex]  =  evenReal - twiddleReal * oddReal + twiddleImag * oddImag
        destination.imagArray[oddIndex]  =  evenImag - twiddleReal * oddImag - twiddleImag * oddReal

    }

}