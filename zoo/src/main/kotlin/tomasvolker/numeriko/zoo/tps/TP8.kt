package tomasvolker.numeriko.zoo.tps

import tomasvolker.kyplot.dsl.line
import tomasvolker.kyplot.dsl.showLine
import tomasvolker.kyplot.dsl.showPlot
import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.*
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.linearalgebra.matMul
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sqrt

typealias State = DoubleArray1D
typealias Observation = DoubleArray1D

val random = Random()

fun doubleArray1DRandomN(s0: Int): DoubleArray1D =
        doubleArray1D(s0) { random.nextGaussian() }

class StateMachine(
        val transition: (State)-> State,
        val observation: (State)-> Observation,
        val stateSize: Int,
        val observationSize: Int
)

class NoisyStateMachine(
        val stateMachine: StateMachine,
        val processNoiseStd: DoubleArray2D,
        val observationNoiseStd: DoubleArray2D
) {

    val processNoiseCovariance get() = processNoiseStd matMul processNoiseStd.transpose()

    fun transitionNoisy(previous: State): State =
            stateMachine.transition(previous) + (processNoiseStd matMul doubleArray1DRandomN(stateMachine.stateSize))

    fun observationNoisy(previous: State): Observation =
            stateMachine.observation(previous) + (observationNoiseStd matMul doubleArray1DRandomN(stateMachine.observationSize))


}

val State.time get() = this[0]
val State.value get() = this[1]

fun DoubleArray1D.squared() = elementWise { it * it }

fun NoisyStateMachine.simulate(initial: State, steps: Int): List<Pair<State, Observation>> {
    val result = mutableListOf(initial)
    repeat(steps) {
        result.add(transitionNoisy(result.last()))
    }
    return result.map { it to observationNoisy(it) }
}

fun Double.squared() = this * this

fun normalPdf(x: Double, mu: Double, sigma: Double): Double =
        exp(-((x-mu).squared()) / (2 * sigma.squared())) / (sqrt(2 * PI) * sigma)

class Particle(
        var state: State,
        var weight: Double
)

fun main() {

    val stateMachine = StateMachine(
            transition = { prev ->
                val time = prev.time + 1
                val value = prev.value
                D[
                        time,
                        0.5 * value + 25 * value / (1 + value * value) + 8 * cos(1.2 * time)
                ]
            },
            observation = { state ->
                val value = state.value
                D[
                        state.time,
                        0.05 * value * value
                ]
            },
            stateSize = 2,
            observationSize = 2
    )

    val measureSigma = 1.0
    val processSigma = 0.1

    val noisy = NoisyStateMachine(
            stateMachine = stateMachine,
            processNoiseStd = D[D[0, 0],
                    D[0, sqrt(processSigma)]],
            observationNoiseStd = D[D[0, 0],
                                    D[0, 1]]
    )

    val simulation = noisy.simulate(
            initial = D[0, 0],
            steps = 150
    )

    val states = simulation.map { it.first }
    val observations = simulation.map { it.second }

    showLine(title = "State") {
        x = states.map { it.time }
        y = states.map { it.value }
    }

    showLine(title = "Observation") {
        x = observations.map { it.time }
        y = observations.map { it.value }
    }

    val particleList = List(10) { D[0, 0] }

    val particelHistory = particleList.map { noisy.simulate(it, 150) }

    showPlot {

        line {
            x = states.map { it.time }
            y = states.map { it.value }
            label = "state"
        }

        particelHistory.forEach { particle ->

            line {
                x = particle.map { it.first.time }
                y = particle.map { it.first.value }
                lineStyle.alpha = 0.5
            }

        }

    }

    /*
    val T = 1000

    val particles = MutableList(T) {
        Particle(
                state = ar[0.0, 0.0],
                weight = 1.0 / T
        )
    }

    for (t in 0 until T) {

        particles.forEach { particle ->
            particle.state = noisy.transitionNoisy(particle.state)
            particle.weight = normalPdf(
                    x = observations[t].value,
                    mu = particle.state.value,
                    sigma = measureSigma
            )
        }

        val normalization = particles.sumByDouble { it.weight }
        particles.forEach { it.weight /= normalization }


    }
    */



}

