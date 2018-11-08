package tomasvolker.numeriko.zoo.image

import tomasvolker.numeriko.core.dsl.ar
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.elementWise
import tomasvolker.numeriko.core.linearalgebra.filter2D
import kotlin.math.hypot

fun DoubleArray2D.computeGradientX(): DoubleArray2D {

    val filter = ar[ar[ -1.0, 0.0, 1.0 ],
                    ar[ -2.0, 0.0, 2.0 ],
                    ar[ -1.0, 0.0, 1.0 ]]

    return this.filter2D(filter)
}

fun DoubleArray2D.computeGradientY(): DoubleArray2D {

    val filter = ar[ar[ -1.0, -2.0, -1.0 ],
                    ar[  0.0,  0.0,  0.0 ],
                    ar[  1.0,  2.0,  1.0 ]]

    return this.filter2D(filter)
}

fun DoubleArray2D.computeGradients(): Pair<DoubleArray2D, DoubleArray2D> =
        Pair(computeGradientX(), computeGradientY())

fun DoubleArray2D.computeEdges(): DoubleArray2D {

    val (gradx, grady) = this.computeGradients()

    return elementWise(gradx, grady) { x, y ->
        hypot(x, y)
    }

}