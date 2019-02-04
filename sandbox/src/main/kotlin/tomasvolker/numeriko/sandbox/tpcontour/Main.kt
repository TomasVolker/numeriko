package tomasvolker.numeriko.sandbox.tpcontour

import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.div
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.*
import tomasvolker.numeriko.core.interfaces.array2d.double.sumBy
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.primitives.indicator
import tomasvolker.numeriko.core.primitives.squared
import tomasvolker.numeriko.core.probability.continuous.NormalDistribution
import tomasvolker.numeriko.sandbox.image.*
import kotlin.math.exp
import kotlin.math.roundToInt
import kotlin.math.sqrt

fun DoubleArray2D.power() = sumBy { it.squared() }

fun DoubleArray2D.meanPower() = power() / size

interface ImageFilter: (DoubleArray2D)->DoubleArray2D {

    fun filter(image: DoubleArray2D): DoubleArray2D

    override fun invoke(image: DoubleArray2D): DoubleArray2D = filter(image)

}

data class ConvolutionRank1Filter(
        val kernelX: DoubleArray1D,
        val kernelY: DoubleArray1D
): ImageFilter {

    override fun filter(image: DoubleArray2D): DoubleArray2D =
            image.filter2D(D[kernelX]).filter2D(D[kernelY].transpose())

}

data class ConvolutionFilter(
        val kernel: DoubleArray2D
): ImageFilter {

    override fun filter(image: DoubleArray2D): DoubleArray2D =
            image.filter2D(kernel)

}

fun imageFilter(kernel: DoubleArray2D) = ConvolutionFilter(kernel)

fun imageFilter(kernelX: DoubleArray1D, kernelY: DoubleArray1D) = ConvolutionRank1Filter(kernelX, kernelY)

inline fun dynamicArray2DView(
        shape0: Int,
        shape1: Int,
        crossinline getter: (i0: Int, i1: Int)->Double,
        crossinline setter: (value: Double, i0: Int, i1: Int)->Double
): DoubleArray2D = object : DefaultMutableDoubleArray2D() {

    override val shape0: Int
        get() = shape0


    override val shape1: Int
        get() = shape1

    override fun get(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return getter(i0, i1)
    }

    override fun set(i0: Int, i1: Int, value: Double) {
        requireValidIndices(i0, i1)
        setter(value, i0, i1)
    }

}

val view = dynamicArray2DView(
        4, 2,
        getter = { i0, i1 ->  (i0==i1).indicator() },
        setter = { value, i0, i1 -> TODO() }
)

interface GradientExtractor: (DoubleArray2D)->ImageGradient {



}

data class ImageGradient(
        val x: DoubleArray2D,
        val y: DoubleArray2D
)

val sobelX = imageFilter(
        kernelX = D[-1, 0, 1],
        kernelY = D[ 1, 2, 1]
)

val sobelY = imageFilter(
        kernelX = D[ 1, 2, 1],
        kernelY = D[-1, 0, 1]
)

val simpleX = imageFilter(
        kernel = D[D[-1, 0, 1]]
)

val simpleY = imageFilter(
        kernel = simpleX.kernel.transpose()
)

val prewittX = imageFilter(
        kernelX = D[-1, 0, 1],
        kernelY = D[ 1, 1, 1]
)

val prewittY = imageFilter(
        kernelX = prewittX.kernelY,
        kernelY = prewittX.kernelX
)

val laplacian = imageFilter(
        kernel = D[D[ 0, -1,  0],
                   D[-1,  4, -1],
                   D[ 0, -1,  0]]
)

fun main() {

    val folder = "./sandbox/res/contour/"

    val signal = loadGrayScaleImage(folder + "circle.png")

    val signalMeanPower = signal.meanPower()

    val snr = 1.0 / 0.97

    val noiseDeviation = sqrt((snr - 1) * signalMeanPower)

    val noiseDistribution = NormalDistribution(deviation = noiseDeviation)

    val noise = doubleArray2D(signal.shape0, signal.shape1) { _, _ -> noiseDistribution.nextDouble() }

    val noisySignal = signal + noise

    println(signal.meanPower() / noisySignal.meanPower())

    //saveGrayScale(smoothed, File(folder + "noisy_circle_smoothed.png"))

}

fun gaussianFilter1D(deviation: Double): DoubleArray1D {

    val variance = deviation.squared()

    val filterSize = ((3 * deviation).roundToInt() / 2) * 2 + 1
    val center = filterSize / 2

    val filterX = doubleArray1D(filterSize) { i0 ->
        exp(-(i0-center).squared() / variance)
    }

    return filterX / filterX.sum()
}

fun DoubleArray2D.smoothed(deviation: Double): DoubleArray2D {

    val gaussian = gaussianFilter1D(deviation)

    val gaussianFilter = imageFilter(
            kernelX = gaussian,
            kernelY = gaussian
    )

    return gaussianFilter.filter(this)
}
