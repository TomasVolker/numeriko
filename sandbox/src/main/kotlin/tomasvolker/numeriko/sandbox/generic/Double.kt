package tomasvolker.numeriko.sandbox.generic

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.implementations.numeriko.arraynd.NumerikoDoubleArrayND
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.elementWise
import tomasvolker.numeriko.core.interfaces.arraynd.double.elementWise
import tomasvolker.numeriko.core.interfaces.arraynd.double.plus
import tomasvolker.numeriko.core.interfaces.arraynd.generic.forEachIndices
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.performance.fastForEach
import tomasvolker.numeriko.core.performance.fastForEachIndices
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {

    NumerikoConfig.checkRanges = true

    repeat(20) { iteration ->

        println("Iteration $iteration:")

/*
        val image1 = Random.nextDoubleArrayND(I[800, 600, 3])
        val image2 = Random.nextDoubleArrayND(I[800, 600, 3])
*/
        val image1 = Random.nextDoubleArray2D(2000, 2000).asMutable()
        val image2 = Random.nextDoubleArrayND(I[2000, 2000]).asMutable() as NumerikoDoubleArrayND


        val operator = measureTimeMillis {
            image1.elementWise { 2 * it }
        }
        println("ElementWise2D: $operator millis")

        val operatorND = measureTimeMillis {
            image2.elementWise { 2 * it }
        }
        println("ElementWiseND: $operatorND millis")

        val fastLambda2D = measureTimeMillis {
            doubleArray2D(image1.shape0, image1.shape1) { i0, i1 -> 2 * image1[i0, i1] }
        }
        println("Lambda2D: $fastLambda2D millis")

        val lambdaND = measureTimeMillis {
            doubleArrayND(image2.shape) { indices -> 2 * image2.getDouble(indices) }
        }
        println("LambdaND: $lambdaND millis")

        val fastLambdaND = measureTimeMillis {
            fastDoubleArrayND(image2.shape) { indices -> 2 * image2.getDouble(indices) }
        }
        println("Fast LambdaND: $fastLambdaND millis")

        println()

    }
}