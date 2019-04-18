package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.unsafeArrayND
import tomasvolker.numeriko.core.interfaces.factory.unsafeDoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.unsafeFloatArrayND
import tomasvolker.numeriko.core.interfaces.factory.unsafeIntArrayND
import tomasvolker.numeriko.core.interfaces.slicing.PermutedSlice
import tomasvolker.numeriko.core.interfaces.slicing.reduceDim
import tomasvolker.numeriko.core.preconditions.requireValidAxis

inline fun <T, R> ArrayND<T>.reduce(
        axis: Int = 0,
        reduction: (acc: ArrayND<T>)->R
): ArrayND<R> {
    requireValidAxis(axis)

    val partialShape = shape.copy().asMutable().apply { set(axis, 1) }

    val result = unsafeArrayND(partialShape) { index ->
        reduction(
                getPermutedSlice(
                        PermutedSlice(
                                permutation = IntArray(1) { axis },
                                shape = IntArray(1) { shape(axis) },
                                strides = IntArray(1) { 1 },
                                origin = index
                        )
                )
        )
    }

    return result.reduceDim(axis)
}

inline fun IntArrayND.reduce(
        axis: Int = 0,
        reduction: (acc: IntArrayND)->Int
): IntArrayND {
    requireValidAxis(axis)

    val partialShape = shape.copy().asMutable().apply { set(axis, 1) }

    val result = unsafeIntArrayND(partialShape) { index ->
        reduction(
                getPermutedSlice(
                        PermutedSlice(
                                permutation = IntArray(1) { axis },
                                shape = IntArray(1) { shape(axis) },
                                strides = IntArray(1) { 1 },
                                origin = index
                        )
                )
        )
    }

    return result.reduceDim(axis)
}


inline fun DoubleArrayND.reduce(
        axis: Int = 0,
        reduction: (acc: DoubleArrayND)->Double
): DoubleArrayND {
    requireValidAxis(axis)

    val partialShape = shape.copy().asMutable().apply { set(axis, 1) }

    val result = unsafeDoubleArrayND(partialShape) { index ->
        reduction(
                getPermutedSlice(
                    PermutedSlice(
                            permutation = IntArray(1) { axis },
                            shape = IntArray(1) { shape(axis) },
                            strides = IntArray(1) { 1 },
                            origin = index
                    )
                )
        )
    }

    return result.reduceDim(axis)
}


inline fun FloatArrayND.reduce(
        axis: Int = 0,
        reduction: (acc: FloatArrayND)->Float
): FloatArrayND {
    requireValidAxis(axis)

    val partialShape = shape.copy().asMutable().apply { set(axis, 1) }

    val result = unsafeFloatArrayND(partialShape) { index ->
        reduction(
                getPermutedSlice(
                        PermutedSlice(
                                permutation = IntArray(1) { axis },
                                shape = IntArray(1) { shape(axis) },
                                strides = IntArray(1) { 1 },
                                origin = index
                        )
                )
        )
    }

    return result.reduceDim(axis)
}

