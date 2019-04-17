package numeriko.lowrank.interfaces.array2d.generic

import tomasvolker.core.interfaces.factory.array2DOfNulls
import tomasvolker.core.performance.forEach
import tomasvolker.core.preconditions.requireSameShape

fun <T> Array2D<T>.toListOfLists(): List<List<T>> =
        List(shape0) { i0 ->
            List(shape1) { i1 -> this.getValue(i0, i1) }
        }

inline fun <T> Array2D<T>.forEachIndex(operation: (i0: Int, i1: Int) -> Unit) {

    forEach(shape0, shape1) { i0, i1 ->
        operation(i0, i1)
    }

}

inline fun <T, R> elementWise(source: Array2D<T>, destination: MutableArray2D<R>, operation: (T) -> R) {
    requireSameShape(source, destination)
    source.forEachIndex { i0, i1 ->
        destination.setValue(i0, i1, operation(source.getValue(i0, i1)))
    }

}

inline fun <T1, T2, R> elementWise(source1: Array2D<T1>, source2: Array2D<T2>, destination: MutableArray2D<R>, operation: (T1, T2) -> R) {
    requireSameShape(source1, source2)
    requireSameShape(source1, destination)
    source1.forEachIndex { i0, i1 ->
        destination.setValue(i0, i1, operation(source1.getValue(i0, i1), source2.getValue(i0, i1)))
    }
}

inline fun <T, R> Array2D<T>.elementWise(operation: (T) -> R): Array2D<R> {
    val result = array2DOfNulls<R>(shape0, shape1).asMutable()
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result as Array2D<R>
}

inline fun <T, A: MutableArray2D<T>> A.applyMap(operation: (T) -> T): A {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

inline fun <T1, T2, R> elementWise(array1: Array2D<T1>, array2: Array2D<T2>, operation: (T1, T2) -> R): Array2D<R> {
    requireSameShape(array1, array2)

    val result = array2DOfNulls<R>(array1.shape0, array1.shape1).asMutable()
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result as Array2D<R>

}

inline fun <T, A: MutableArray2D<T>> A.applyElementWise(other: Array2D<T>, operation: (T, T) -> T): A {
    requireSameShape(this, other)
    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
    return this
}

fun Array2D<*>.isSquare(): Boolean = shape0 == shape1