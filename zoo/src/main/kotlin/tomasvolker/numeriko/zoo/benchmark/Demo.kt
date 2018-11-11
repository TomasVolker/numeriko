package tomasvolker.numeriko.zoo.benchmark

import koma.randn
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.inverse.InvertMatrix
import tomasvolker.kyplot.dsl.*
import tomasvolker.numeriko.core.interfaces.factory.doubleRandom
import tomasvolker.numeriko.core.linearalgebra.inverse
import tomasvolker.numeriko.core.linearalgebra.solve
import kotlin.system.measureTimeMillis

fun main() {

    benchmarkSolve()

}

data class Run(
        val index: Int,
        val timeMillis: Long
)

inline fun measure(index: Int, log: Boolean = true, block: ()->Unit) =
        Run(
                index = index,
                timeMillis = measureTimeMillis(block)
                        .also {
                            if(log) println("Run: $it millis")
                        }
        )

inline fun benchmark(repetitions: Int, log: Boolean = true, block: () -> Unit) =
        (1..repetitions).map { i -> measure(i, log, block) }

inline fun <T> logTime(label: String, block: () -> T): T {
    var result: T? = null

    measureTimeMillis {
        result = block()
    }.also { println("$label: $it millis") }

    return result as T
}

fun benchmarkInverse() {

    val iterations = 100
    val matrixSize = 500

    println("Numeriko")
    val numerikoX = logTime("Matrix generation") { doubleRandom(matrixSize, matrixSize) }
    val numerikoResults = benchmark(iterations) { numerikoX.inverse() }

    println("Koma")
    val komaX = logTime("Matrix generation") { randn(matrixSize, matrixSize) }
    val komaResults = benchmark(iterations) { komaX.inv() }

    println("Nd4j")
    val nd4jX = logTime("Matrix generation") { Nd4j.rand(matrixSize, matrixSize) }
    val nd4jResults = benchmark(iterations) { InvertMatrix.invert(nd4jX, false) }


    showPlot {

        line {
            label = "Numeriko"
            x = numerikoResults.map { it.index }
            y = numerikoResults.map { it.timeMillis }
        }

        line {
            label = "Koma"
            x = komaResults.map { it.index }
            y = komaResults.map { it.timeMillis }
        }

        line {
            label = "Nd4j"
            x = nd4jResults.map { it.index }
            y = nd4jResults.map { it.timeMillis }
        }

        xAxis {
            label = "Iteration"
            limits = between(0, iterations)
        }

        yAxis {
            label = "Time [millis]"
            limits = between(1, 600)
        }

        legend.visible = true

    }

}

fun benchmarkSolve() {

    val iterations = 100
    val systemSize = 500

    println("Numeriko")
    val numerikoX = logTime("Matrix generation") { doubleRandom(systemSize, systemSize) }
    val numerikob = logTime("Vector generation") { doubleRandom(systemSize) }
    val numerikoResults = benchmark(iterations) { numerikoX.solve(numerikob) }

    println("Koma")
    val komaX = logTime("Matrix generation") { randn(systemSize, systemSize) }
    val komab = logTime("Vector generation") { randn(systemSize, 1) }
    val komaResults = benchmark(iterations) { komaX.solve(komab) }


    showPlot {

        line {
            label = "Numeriko"
            x = numerikoResults.map { it.index }
            y = numerikoResults.map { it.timeMillis }
        }

        line {
            label = "Koma"
            x = komaResults.map { it.index }
            y = komaResults.map { it.timeMillis }
        }

        xAxis {
            label = "Iteration"
            limits = between(0, iterations)
        }

        yAxis {
            label = "Time [millis]"
            limits = between(1, 300)
        }

        legend.visible = true

    }

}
