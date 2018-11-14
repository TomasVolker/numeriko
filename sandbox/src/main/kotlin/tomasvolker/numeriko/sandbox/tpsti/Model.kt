package tomasvolker.numeriko.sandbox.tpsti

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.elementWise
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleDiagonal
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros
import tomasvolker.numeriko.core.operations.stack
import tomasvolker.numeriko.core.primitives.sumDouble
import kotlin.math.sqrt

interface Getter<T> {
    operator fun get(i: Int): T
}

inline fun <T> getter(crossinline getter: (i: Int)->T): Getter<T> =
        object: Getter<T> {
            override operator fun get(i: Int) = getter(i)
        }

class SignalEnsemble(
        val values: DoubleArray2D
) {

    val channelCount = values.shape0

    val duration = values.shape1

    val timeSteps = 0 until duration

    val channel = getter { getChannel(it) }
    val samples = getter { getSamples(it) }

    val timeArray: DoubleArray1D = doubleArray1D(duration) { i -> i.toDouble() }

    fun getChannel(channel: Int): DoubleArray1D =
            values.getView(channel, All)

    fun getSamples(timeStep: Int): DoubleArray1D =
            values.getView(All, timeStep)

    fun getSampleAtChannel(channel: Int, timeStep: Int): Double =
            values[channel, timeStep]

}


fun SignalEnsemble.estimateAutoCorrelation(i0: Int, i1: Int, delta: Int): Double {

    val availableValues = duration - delta

    return sumDouble(0 until availableValues) { t ->
        this.getSampleAtChannel(i0, t) * this.getSampleAtChannel(i1, t + delta)
    } / availableValues
}


fun SignalEnsemble.estimateAutoCorrelation(delta: Int): DoubleArray2D {
    return doubleArray2D(channelCount, channelCount) { i0, i1 ->
        estimateAutoCorrelation(i0, i1, delta)
    }
}

inline fun SignalEnsemble.mapSamples(operation: (sample: DoubleArray1D)->DoubleArray1D): SignalEnsemble {

    val values = doubleZeros(channelCount, duration).asMutable()

    for (t in timeSteps) {
        values[All, t] = operation(samples[t])
    }

    return SignalEnsemble(
            values = values
    )
}

fun SignalEnsemble.mix(coefficients: DoubleArray1D): DoubleArray1D =
        doubleArray1D(duration) { t ->
            coefficients inner samples[t]
        }


fun SignalEnsemble.correlationMatrix(): DoubleArray2D =
        doubleArray2D(channelCount, channelCount) { i0, i1 ->
            meanValue { samples ->
                samples[i0] * samples[i1]
            }
        }

inline fun SignalEnsemble.meanValue(value: (x: DoubleArray1D)->Double): Double =
        sumDouble(timeSteps) { t -> value(getSamples(t)) } / duration

inline fun SignalEnsemble.meanVector(value: (x: DoubleArray1D)->DoubleArray1D): DoubleArray1D =
        sumVector(timeSteps) { t -> value(getSamples(t)) } / duration


fun mergeSignals(vararg arrays: DoubleArray1D): SignalEnsemble =
        SignalEnsemble(stack(*arrays))


fun SignalEnsemble.mixSignals(matrix: DoubleArray2D): SignalEnsemble {

    val values = doubleZeros(this.channelCount, this.duration).asMutable()

    for (t in 0 until duration) {
        values[All, t] = matrix matMul this.samples[t]
    }

    return SignalEnsemble(
            values
    )
}


fun generateSignals(
        sensorCount: Int,
        timeSteps: Int,
        values: (sensor: Int, time: Int)->Double
): SignalEnsemble =
        SignalEnsemble(
                doubleArray2D(sensorCount, timeSteps) { i0, i1 -> values(i0, i1) }
        )


fun SignalEnsemble.uncorrelate(): SignalEnsemble {

    val decomposition = correlationMatrix().eigenDecomposition(true)

    val sqrtDinv = doubleDiagonal(decomposition.eigenValues.elementWise { 1.0 / sqrt(it) })

    val E = decomposition.vectorMatrix()

    val sqrtCinv = E matMul sqrtDinv matMul E.transpose()

    return mixSignals(sqrtCinv)
}
