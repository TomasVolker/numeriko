package tomasvolker.numeriko.sandbox.tpcontour

import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.sumBy
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.primitives.squared
import tomasvolker.numeriko.core.probability.continuous.NormalDistribution
import tomasvolker.numeriko.sandbox.image.*
import kotlin.math.exp
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

fun DoubleArray2D.power() = sumBy { it.squared() }

fun DoubleArray2D.meanPower() = power() / size

interface ImageFilter: (DoubleArray2D)->DoubleArray2D {

    fun filter(image: DoubleArray2D): DoubleArray2D

    override fun invoke(image: DoubleArray2D): DoubleArray2D = filter(image)

}

data class ConvolutionFilter(
        val kernel: DoubleArray2D
): ImageFilter {

    override fun filter(image: DoubleArray2D): DoubleArray2D =
            image.filter2D(kernel)

}

fun imageFilter(kernel: DoubleArray2D) = ConvolutionFilter(kernel)

interface GradientExtractor: (DoubleArray2D)->ImageGradient {



}

data class ImageGradient(
        val x: DoubleArray2D,
        val y: DoubleArray2D
)

val sobelX = imageFilter(
        kernel = D[D[-1,  0, 1],
                   D[-2,  0, 2],
                   D[-1,  0, 1]]
)

val sobelY = imageFilter(
        kernel = sobelX.kernel.transpose()
)

val simpleX = imageFilter(
        kernel = D[D[-1, 0, 1]]
)

val simpleY = imageFilter(
        kernel = simpleX.kernel.transpose()
)

val prewittX = imageFilter(
        kernel = D[D[-1,  0, 1],
                   D[-1,  0, 1],
                   D[-1,  0, 1]]
)

val prewittY = imageFilter(
        kernel = prewittX.kernel.transpose()
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


    for (i in 0..10) {
        showImage(noisySignal.smooth(1.0 * i))
    }


    //saveGrayScale(smoothed, File(folder + "noisy_circle_smoothed.png"))

}

fun gaussianFilter2D(deviation: Double): DoubleArray2D {

    val variance = deviation.squared()

    val filterSize = ((3 * deviation).roundToInt() / 2) * 2 + 1
    val center = filterSize / 2

    val filter = doubleArray2D(filterSize, filterSize) { i0, i1 ->
        exp(-((i0-center).squared() + (i1-center).squared()) / variance)
    }

    return filter / filter.sum()
}

fun DoubleArray2D.smooth(deviation: Double): DoubleArray2D {
    return filter2D(gaussianFilter2D(deviation))
}
