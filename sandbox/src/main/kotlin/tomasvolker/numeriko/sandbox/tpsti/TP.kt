package tomasvolker.numeriko.sandbox.tpsti

import tomasvolker.kyplot.dsl.*
import tomasvolker.kyplot.model.Color
import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.interfaces.array1d.double.minus
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.elementWise
import tomasvolker.numeriko.core.interfaces.array2d.double.transpose
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.operations.stack
import tomasvolker.numeriko.core.probability.continuous.NormalDistribution
import tomasvolker.numeriko.core.probability.continuous.UniformDistribution
import java.io.File
import java.nio.ByteOrder
import kotlin.math.*
import kotlin.random.Random
import java.util.Random as JavaRandom

fun analyseMeasurements(
        sources: SignalEnsemble
) {

    showPlot {

        xAxis.label = "x"
        yAxis.label = "y"

        scatter {
            x = sources.channel[0]
            y = sources.channel[1]
        }

    }

    val A = D[D[ 1, 1],
              D[-1, 2]]

    val measurements = sources.mixSignals(matrix = A)

    showPlot {

        xAxis.label = "x"
        yAxis.label = "y"

        scatter {
            x = measurements.channel[0]
            y = measurements.channel[1]
        }

    }

}

fun firstPart() {

    val uniform = UniformDistribution(
            min = -sqrt(3.0),
            max = sqrt(3.0)
    )

    val uniformSources = generateSignals(
            sensorCount = 2,
            timeSteps = 1000
    ) { sensor, time ->
        uniform.nextDouble()
    }

    analyseMeasurements(uniformSources)

    val A = D[D[ 1, 1],
              D[-1, 2]]

    val measurements = uniformSources.mixSignals(matrix = A)

    val corners = D[D[ 0.01, 5.25],
                    D[ 3.45, 1.63],
                    D[ 0.02,-5.22],
                    D[-3.40,-1.72]]

    val horizontal = corners[1, All] - corners[0, All]
    val vertical = corners[0, All] - corners[3, All]
    println(horizontal)
    println(vertical)

    val mat = (horizontal stack vertical).transpose().also { println(it) }

    showPlot {

        xAxis.label = "x"
        yAxis.label = "y"

        scatter {
            x = measurements.channel[0]
            y = measurements.channel[1]
        }

        scatter {
            x = corners[All, 0]
            y = corners[All, 1]
            color = Color.RED
        }

        line {
            x = corners[0..1, 0]
            y = corners[0..1, 1]
            color = Color.RED
        }

        line {
            x = corners[1..2, 0]
            y = corners[1..2, 1]
            color = Color.RED
        }

        line {
            x = corners[2..3, 0]
            y = corners[2..3, 1]
            color = Color.RED
        }

        line {
            x = D[corners[3, 0], corners[0, 0]]
            y = D[corners[3, 1], corners[0, 1]]
            color = Color.RED
        }

    }

    val gaussian = NormalDistribution(
            mean = 2.0,
            deviation = 4.0
    )

    val gaussianSources = generateSignals(
            sensorCount = 2,
            timeSteps = 1000
    ) { sensor, time ->
        gaussian.nextDouble()
    }

    analyseMeasurements(gaussianSources)


    val moreSources = generateSignals(
            sensorCount = 4,
            timeSteps = 1000
    ) { sensor, time ->
        Random.nextDouble(
                from = -sqrt(3.0),
                until = sqrt(3.0)
        )
    }

    showPlot(moreSources)

}

fun independentComponentAnalysis(
        measurements: SignalEnsemble
): SignalEnsemble {

    val uncorrelated = measurements.uncorrelate()

    val basis = uncorrelated.independentComponentAnalysis()
    val ortho = basis.orthogonalize()
    println("Basis: ${ortho[0]}, ${ortho[1]}")
    val W = stack(ortho)

    return uncorrelated.mixSignals(W)
}

fun simpleSobi(
        measurements: SignalEnsemble,
        delay: Int = 1
): SignalEnsemble {

    val uncorrelated = measurements.uncorrelate()

    val autoCorrelation = uncorrelated.estimateAutoCorrelation(delay)
    val decomposition = autoCorrelation.eigenDecomposition(symetric = true)

    return uncorrelated.mixSignals(decomposition.vectorMatrix())
}

fun main(args: Array<String>) {

    val resDirectory = "./sandbox/res/tp/"

    firstPart()
    return
    val order = ByteOrder.LITTLE_ENDIAN

    val signal1 = File(resDirectory + "In_1.txt").readArray1D(order)
    val signal2 = File(resDirectory + "In_2.txt").readArray1D(order)

    val reference1 = File(resDirectory + "Ref_1.txt").readArray1D(order)
    val reference2 = File(resDirectory + "Ref_2.txt").readArray1D(order)

    val measurements = mergeSignals(signal1, signal2)
    val reference = mergeSignals(reference1, reference2)

    val icaResult = independentComponentAnalysis(measurements)

    val icaError = errorMatrix(reference, icaResult).elementWise { it.toDB() }

    println("Error Matrix [dB]: $icaError")

    showRecoveryPlot(
            reference = reference,
            recovered = icaResult
    )

    val sobiResult = sobi(measurements, window = 6)

    val sobiError = errorMatrix(reference, sobiResult).elementWise { it.toDB() }

    println("Error Matrix [dB]: $sobiError")

    showRecoveryPlot(
            reference = reference,
            recovered = sobiResult
    )


/*
    val maxDelay = 40

    val errors = (1..maxDelay).map { t ->
        val recovered = simpleSobi(measurements, delay = t)

        errorMatrix(reference, recovered).elementWise { it.toDB().also { println(it) } }.min() ?: 0.0
    }

    showPlot {
        title = "Autocorrelation delay used vs Error"
        line {
            x = (1..maxDelay).toList()
            y = errors.toList()
        }
        xAxis.label = "Autocorrelation delay used [time steps]"
        yAxis.label = "Error [dB]"
    }
*/

    /*
    val maxWindow = 20

    val errors = (2..maxWindow).map { w ->
        val recovered = measurements.sobi(
                window = w
        )

        errorMatrix(reference, recovered).elementWise { it.toDB() }.min().also { println(it) } ?: 0.0
    }

    showPlot {
        title = "Autocorrelation delay used vs Error"
        line {
            x = (2..maxWindow).toList()
            y = errors.toList()
        }
        xAxis.label = "Autocorrelation delay used [time steps]"
        yAxis.label = "Error [dB]"
    }
    */
}

fun errorMatrix(
        reference: SignalEnsemble,
        recovered: SignalEnsemble
): DoubleArray2D =
        doubleArray2D(reference.channelCount, recovered.channelCount) { i0, i1 ->
            normalizedQuadraticError(reference.channel[i0], recovered.channel[i1])
        }

fun showRecoveryPlot(
        reference: SignalEnsemble,
        recovered: SignalEnsemble
) {

    showFigure {

        allPlots {
            position.rowCount = 4
        }

        plot {
            title = "Recovered 1"
            line {
                x = recovered.timeArray
                y = recovered.channel[0]
            }
            position.row = 0
        }

        plot {
            title = "Recovered 2"
            line {
                x = recovered.timeArray
                y = recovered.channel[1]
            }
            position.row = 1
        }

        plot {
            title = "Reference 1"
            line {
                x = recovered.timeArray
                y = reference.channel[0]
            }
            position.row = 2
        }

        plot {
            title = "Reference 2"
            line {
                x = recovered.timeArray
                y = reference.channel[1]
            }
            position.row = 3
        }

    }

}


fun showPlot(signalEnsemble: SignalEnsemble) {

    val channelCount = signalEnsemble.channelCount

    showFigure {

        allPlots {
            position {
                rowCount = channelCount
            }
        }

        for (i in 0 until channelCount) {

            plot {
                title = "Signal $i"
                line {
                    x = signalEnsemble.timeArray
                    y = signalEnsemble.channel[i]
                }
                position.row = i
            }

        }

    }

}



