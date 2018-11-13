package tomasvolker.numeriko.sandbox.tpsti

import tomasvolker.kyplot.dsl.*
import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.operations.stack
import tomasvolker.numeriko.core.probability.DoubleNormalDistribution
import tomasvolker.numeriko.core.probability.UniformDistribution
import java.io.File
import java.nio.ByteOrder
import kotlin.math.*
import kotlin.random.Random
import java.util.Random as JavaRandom

fun analyseMeasurements(
        sources: SignalEnsemble
) {

    showPlot {

        scatter {
            x = sources.channel[0].asList()
            y = sources.channel[1].asList()
        }

    }

    val A = D[D[ 1, 1],
              D[-1, 2]]

    val measurements = sources.mixSignals(matrix = A)

    showPlot {

        scatter {
            x = measurements.channel[0].asList()
            y = measurements.channel[1].asList()
        }

    }

}



fun main(args: Array<String>) {
/*
    val uniform = UniformDistribution(
            min = -sqrt(3.0),
            max = sqrt(3.0)
    )

    val uniformSources = generateSignals(
            sensorCount = 2,
            timeSteps = 1000
    ) { sensor, time ->
        uniform.nextSample()
    }

    analyseMeasurements(uniformSources)

    val gaussian = DoubleNormalDistribution(
            mean = 2.0,
            deviation = 4.0
    )

    val gaussianSources = generateSignals(
            sensorCount = 2,
            timeSteps = 1000
    ) { sensor, time ->
        gaussian.nextSample()
    }

    analyseMeasurements(gaussianSources)
*/

    val moreSources = generateSignals(
            sensorCount = 4,
            timeSteps = 1000
    ) { sensor, time ->
        Random.nextDouble(
                from = -sqrt(3.0),
                until = sqrt(3.0)
        )
    }

    //showPlot(moreSources)

    val order = ByteOrder.LITTLE_ENDIAN

    val file1 = File("./sandbox/res/tp/In_1.txt")
    val signal1 = file1.readArray1D(order)

    val file2 = File("./sandbox/res/tp/In_2.txt")
    val signal2 = file2.readArray1D(order)

    val reference1 = File("./sandbox/res/tp/Ref_1.txt").readArray1D(order)
    val reference2 = File("./sandbox/res/tp/Ref_2.txt").readArray1D(order)

    val measurements = mergeSignals(signal1, signal2)

    //showPlot(measurements)

    val uncorrelated = measurements.uncorrelate()

    //showPlot(uncorrelated)

    val basis = uncorrelated.independentComponentAnalysis()
    val ortho = basis.orthogonalize()
    println("Basis: ${ortho[0]}, ${ortho[1]}")
    val W = stack(*ortho.toTypedArray())

    val recovered = uncorrelated.mixSignals(W)

    showFigure {

        allPlots {
            position.rowCount = 4
        }

        plot {
            title = "Recovered 1"
            line {
                x = uncorrelated.timeArray.asList()
                y = recovered.channel[0].asList()
            }
            position.row = 0
        }

        plot {
            title = "Recovered 2"
            line {
                x = uncorrelated.timeArray.asList()
                y = recovered.channel[1].asList()
            }
            position.row = 1
        }

        plot {
            title = "Reference 1"
            line {
                x = uncorrelated.timeArray.asList()
                y = reference1.asList()
            }
            position.row = 2
        }

        plot {
            title = "Reference 2"
            line {
                x = uncorrelated.timeArray.asList()
                y = reference2.asList()
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
                    x = signalEnsemble.timeArray.asList()
                    y = signalEnsemble.channel[i].asList()
                }
                position.row = i
            }

        }

    }

}



