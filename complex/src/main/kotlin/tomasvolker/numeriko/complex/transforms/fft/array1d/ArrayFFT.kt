package tomasvolker.numeriko.complex.transforms.fft.array1d


import tomasvolker.numeriko.complex.array.ComplexArray1D
import tomasvolker.numeriko.complex.array.MutableComplexArray1D
import tomasvolker.numeriko.complex.array.complexZeros
import tomasvolker.numeriko.core.primitives.isPowerOf2
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun fft(
        array: ComplexArray1D,
        destination: MutableComplexArray1D = complexZeros(array.size).asMutable(),
        inverse: Boolean = false
): ComplexArray1D {

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

    return destination.applyDiv(normalization)
}

fun ifft(
        array: ComplexArray1D,
        destination: MutableComplexArray1D = complexZeros(array.size).asMutable()
): ComplexArray1D = fft(
        array = array,
        destination = destination,
        inverse = true
)

fun cooleyTukeyAlgorithm(
        array: ComplexArray1D,
        offset: Int,
        stride: Int,
        size: Int,
        destination: MutableComplexArray1D,
        destOffset: Int,
        inverse: Boolean
) {

    if (size == 1) {
        destination.setReal(array.getReal(offset), destOffset)
        destination.setImag(array.getImag(offset), destOffset)
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

        val evenReal = destination.getReal(evenIndex)
        val evenImag = destination.getImag(evenIndex)

        val oddReal  = destination.getReal(oddIndex)
        val oddImag  = destination.getImag(oddIndex)

        // destination[evenIndex] =  even + twiddle * odd
        destination.setReal(evenReal + twiddleReal * oddReal - twiddleImag * oddImag, evenIndex)
        destination.setImag(evenImag + twiddleReal * oddImag + twiddleImag * oddReal, evenIndex)

        // destination[oddIndex]  =  even - twiddle * odd
        destination.setReal(evenReal - twiddleReal * oddReal + twiddleImag * oddImag, oddIndex)
        destination.setImag(evenImag - twiddleReal * oddImag - twiddleImag * oddReal, oddIndex)

    }

}