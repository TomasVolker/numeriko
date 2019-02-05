package tomasvolker.numeriko.sandbox.morphology

import tomasvolker.numeriko.core.interfaces.array2d.generic.*
import tomasvolker.numeriko.core.interfaces.factory.array2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.primitives.indicator
import tomasvolker.numeriko.core.primitives.squared
import tomasvolker.numeriko.sandbox.image.showImage
import kotlin.random.Random

fun Array2D<Double>.toDoubleArray2D() =
        doubleArray2D(shape0, shape1) { i0, i1 -> this[i0, i1] }

fun Array2D<Boolean>.dilate(mask: Array2D<Boolean>): Array2D<Boolean> {

    val result = array2D(shape0, shape1) { i0, i1 -> false }.asMutable()

    val halfShape0 = mask.shape0 / 2
    val halfShape1 = mask.shape1 / 2

    result.forEachIndex { i0, i1 ->

        mask.forEachIndex { j0, j1 ->
            val index0 = i0 + j0 - halfShape0
            val index1 = i1 + j1 - halfShape1

            if (index0 in 0 until shape0 && index1 in 0 until shape1) {
                if (mask[j0, j1] && this[index0, index1])
                    result[i0, i1] = true
            }
        }

    }

    return result
}

fun Array2D<Boolean>.erode(mask: Array2D<Boolean>): Array2D<Boolean> {

    val result = array2D(shape0, shape1) { i0, i1 -> true }.asMutable()

    val halfShape0 = mask.shape0 / 2
    val halfShape1 = mask.shape1 / 2

    result.forEachIndex { i0, i1 ->

        mask.forEachIndex { j0, j1 ->
            val index0 = i0 + j0 - halfShape0
            val index1 = i1 + j1 - halfShape1
            if (index0 in 0 until shape0 && index1 in 0 until shape1) {

                if (mask[j0, j1] && !this[index0, index1])
                    result[i0, i1] = false

            }
        }

    }

    return result
}

fun Array2D<Boolean>.open(mask: Array2D<Boolean>) =
        erode(mask).dilate(mask)

fun Array2D<Boolean>.close(mask: Array2D<Boolean>) =
        dilate(mask).erode(mask)

fun createCircle(n: Int) = array2D(n, n) { i0, i1 ->
    (i0 - n/2).squared() + (i1 - n/2).squared() <= (n/2).squared()
}

fun createSquare(n: Int) = array2D(n, n) { i0, i1 -> true }

fun main(args: Array<String>) {

    val n = 200

    val image = array2D(n, n) { i0, i1 ->
        Random.nextDouble() < 0.01
    }

    val mask = createCircle(5)

    var state = image

    showImage(state.elementWise { it.indicator() }.toDoubleArray2D())

    repeat(1) {

        state = state.open(mask)

        showImage(state.elementWise { it.indicator() }.toDoubleArray2D())

    }


}