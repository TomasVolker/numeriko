package tomasvolker.numeriko.core.interfaces.generic.arraynd

import tomasvolker.numeriko.core.interfaces.integer.array1d.ReadOnlyIntArray1D

operator fun <T> ReadOnlyArrayND<T>.get(vararg indeces: Int) =
        getValue(*indeces)

operator fun <T> ReadOnlyArrayND<T>.get(indexArray: ReadOnlyIntArray1D) =
        getValue(indexArray)

operator fun <T> ArrayND<T>.get(vararg indeces: Int) =
        getValue(*indeces)

operator fun <T> ArrayND<T>.get(indexArray: ReadOnlyIntArray1D) =
        getValue(indexArray)

operator fun <T> ArrayND<T>.set(vararg indeces: Int, value: T) =
        setValue(value, *indeces)

operator fun <T> ArrayND<T>.set(indexArray: ReadOnlyIntArray1D, value: T) =
        setValue(value, indexArray)



fun <T> ArrayND<T>.forEachIndexed(action: (indexArray: ReadOnlyIntArray1D, value: T) -> Unit) {

    val iterator = cursor()

    var indeces: ReadOnlyIntArray1D
    var value: T
    while (iterator.hasNext()) {
        indeces = iterator.currentIndices
        value = iterator.next()
        action(indeces, value)
    }

}
