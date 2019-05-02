package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.interfaces.slicing.reduceRank
import tomasvolker.numeriko.core.preconditions.requireValidAxis

fun reduceSlice(array: ArrayND<*>, axis: Int, index: IntArray): ArraySlice =
        ArraySlice(
            permutation = intArrayOf(axis),
            shape = intArrayOf(array.shape(axis)),
            strides = intArrayOf(1),
            origin = index
        )

inline fun <T, R> ArrayND<T>.reduce(
        axis: Int = 0,
        keepAxis: Boolean = false,
        reduction: (acc: ArrayND<T>)->R
): ArrayND<R> {
    requireValidAxis(axis)

    val partialShape = shape.copy().asMutable().apply { set(axis, 1) }

    val result = unsafeArrayND(partialShape) { index ->
        reduction(getSlice(reduceSlice(this, axis, index)))
    }

    return if(keepAxis) result else result.reduceRank(axis)
}

inline fun IntArrayND.reduce(
        axis: Int = 0,
        keepAxis: Boolean = false,
        reduction: (acc: IntArrayND)->Int
): IntArrayND {
    requireValidAxis(axis)

    val partialShape = shape.copy().asMutable().apply { set(axis, 1) }

    val result = unsafeIntArrayND(partialShape) { index ->
        reduction(getSlice(reduceSlice(this, axis, index)))
    }

    return if(keepAxis) result else result.reduceRank(axis)
}

inline fun IntArrayND.reduceDouble(
        axis: Int = 0,
        keepAxis: Boolean = false,
        reduction: (acc: IntArrayND)->Double
): DoubleArrayND {
    requireValidAxis(axis)

    val partialShape = shape.copy().asMutable().apply { set(axis, 1) }

    val result = unsafeDoubleArrayND(partialShape) { index ->
        reduction(getSlice(reduceSlice(this, axis, index)))
    }

    return if(keepAxis) result else result.reduceRank(axis)
}

inline fun IntArrayND.reduceFloat(
        axis: Int = 0,
        keepAxis: Boolean = false,
        reduction: (acc: IntArrayND)->Float
): FloatArrayND {
    requireValidAxis(axis)

    val partialShape = shape.copy().asMutable().apply { set(axis, 1) }

    val result = unsafeFloatArrayND(partialShape) { index ->
        reduction(getSlice(reduceSlice(this, axis, index)))
    }

    return if(keepAxis) result else result.reduceRank(axis)
}


inline fun DoubleArrayND.reduce(
        axis: Int = 0,
        keepAxis: Boolean = false,
        reduction: (acc: DoubleArrayND)->Double
): DoubleArrayND {
    requireValidAxis(axis)

    val partialShape = shape.copy().asMutable().apply { set(axis, 1) }

    val result = unsafeDoubleArrayND(partialShape) { index ->
        reduction(getSlice(reduceSlice(this, axis, index)))
    }

    return if(keepAxis) result else result.reduceRank(axis)
}


inline fun FloatArrayND.reduce(
        axis: Int = 0,
        keepAxis: Boolean = false,
        reduction: (acc: FloatArrayND)->Float
): FloatArrayND {
    requireValidAxis(axis)

    val partialShape = shape.copy().asMutable().apply { set(axis, 1) }

    val result = unsafeFloatArrayND(partialShape) { index ->
        reduction(getSlice(reduceSlice(this, axis, index)))
    }

    return if(keepAxis) result else result.reduceRank(axis)
}

inline fun ByteArrayND.reduce(
        axis: Int = 0,
        keepAxis: Boolean = false,
        reduction: (acc: ByteArrayND)->Byte
): ByteArrayND {
    requireValidAxis(axis)

    val partialShape = shape.copy().asMutable().apply { set(axis, 1) }

    val result = unsafeByteArrayND(partialShape) { index ->
        reduction(getSlice(reduceSlice(this, axis, index)))
    }

    return if(keepAxis) result else result.reduceRank(axis)
}
