package tomasvolker.numeriko.sandbox.tpmovement

import koma.pow
import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.elementWise
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.elementWise
import tomasvolker.numeriko.core.interfaces.array2d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.unsafeGetView
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.toDoubleArray1D
import tomasvolker.numeriko.core.operations.reduction.reduce
import tomasvolker.numeriko.core.primitives.squared
import tomasvolker.numeriko.core.primitives.sumDouble
import tomasvolker.numeriko.sandbox.image.loadRGBAImage
import tomasvolker.numeriko.sandbox.image.showImage
import tomasvolker.numeriko.sandbox.tpcontour.smoothed
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

operator fun DoubleArrayND.get(vararg indices: Any) = unsafeGetView(*indices)
operator fun MutableDoubleArrayND.get(vararg indices: Any) = unsafeGetView(*indices)

fun DoubleArray1D.average(): Double = sum() / size

fun DoubleArrayND.average(axis: Int): DoubleArrayND =
        reduce(axis) { it.average() }

data class AffineTransform(
        val intercept: DoubleArray1D,
        val matrix: DoubleArray2D
): (DoubleArray1D)->DoubleArray1D {

    override fun invoke(input: DoubleArray1D): DoubleArray1D =
            intercept + (matrix matMul input)

    fun inverse(): AffineTransform {
        val matrixInverse = matrix.inverse()
        return AffineTransform(
                intercept = -(matrixInverse matMul intercept),
                matrix = matrixInverse
        )
    }

}

fun DoubleArray2D.applyTransform(transform: AffineTransform): DoubleArray2D {

    val inverseTransform = transform.inverse()

    return doubleArray2D(shape0, shape1) { i0, i1 ->
        val (k0, k1) = inverseTransform(D[i0, i1]).map { it.roundToInt() }
        if (k0 in 0 until shape0 && k1 in 0 until shape1)
            this[k0, k1]
        else
            0.0
    }
}

fun rotationTransform(center: DoubleArray1D, angle: Double): AffineTransform {
    val rotationMatrix = D[D[cos(angle), -sin(angle)],
                           D[sin(angle),  cos(angle)]]
    return AffineTransform(
            intercept = center - (rotationMatrix matMul center),
            matrix = rotationMatrix
    )
}

fun main(args: Array<String>) {

    val directory = "./sandbox/res/"

    val lena = loadRGBAImage(directory + "lena.png")

    val lenaGray = lena.average(axis = 2).as2D()
/*
    showPlot {

        histogram(lena[All, All, 0].toList()) {
            bins = 50
            alpha = 0.5
            color = Color.RED
            label = "red"
        }

        histogram(lena[All, All, 1].toList()) {
            bins = 50
            alpha = 0.5
            color = Color.GREEN
            label = "green"
        }

        histogram(lena[All, All, 2].toList()) {
            bins = 50
            alpha = 0.5
            color = Color.BLUE
            label = "blue"
        }

        legend.visible = true
    }
*/
    val pixelValues = lenaGray.toList().sorted().toDoubleArray1D()
/*
    showHistogram(pixelValues) {
        bins = 100
        alpha = 0.5
    }

    showLine {
        x = pixelValues
        y = pixelValues.indices
    }
*/
    val min = pixelValues[0]
    val max = pixelValues[Last]

    println("min: $min max: $max")

    val equalized = lenaGray.elementWise { (it - min) / (max - min) }

    println(lenaGray.imageMoment(2))

    showImage(equalized.smoothed(5.0))

    val rotation = rotationTransform(
            center = lenaGray.shape.toDoubleArray1D().elementWise { it / 2 },
            angle = Math.toRadians(16.0)
    )

    showImage(lenaGray.applyTransform(rotation))
}

fun Int.intPow(other: Int): Int = when {
    other == 0 -> 1
    other == 1 -> this
    other == 2 -> this.squared()
    other == 3 -> this * this * this
    other < 0 -> error("")
    other % 2 == 0 -> intPow(other / 2).squared()
    other % 3 == 0 -> intPow(other / 3).intPow(3)
    else -> intPow(other - 1) * this
}

fun Double.intPow(other: Int): Double = when {
    other == 0 -> 1.0
    other == 1 -> this
    other == 2 -> this.squared()
    other == 3 -> this * this * this
    other < 0 -> (1.0 / this).intPow(other)
    other % 2 == 0 -> intPow(other / 2).squared()
    other % 3 == 0 -> intPow(other / 3).intPow(3)
    else -> intPow(other - 1) * this
}

fun DoubleArray2D.imageMoment(order: Int): DoubleArray1D {

    var moment0 = 0.0
    var moment1 = 0.0

    forEachIndex { i0, i1 ->
        val value = this[i0, i1]
        moment0 += i0.intPow(order) * value
        moment1 += i1.intPow(order) * value
    }

    return D[moment0, moment1] / sum()
}

fun DoubleArray1D.moment(order: Int): Double =
        sumDouble(0 until size) { i -> this[i].pow(order) } / size

fun DoubleArray1D.centeredMoment(order: Int): Double {
    val moment1 = average()
    return sumDouble(0 until size) { i -> (this[i] - moment1).pow(order) } / size
}
