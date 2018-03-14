package tomasvolker.numeriko.core.interfaces.generic.arraynd

import tomasvolker.numeriko.core.interfaces.int.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntArrayND

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



/*inline*/ fun <T> ArrayND<T>.forEachIndexed(action: (indexArray: ReadOnlyIntArray1D, value: T) -> Unit) {

    val iterator = cursor()

    var indeces: ReadOnlyIntArray1D
    var value: T
    while (iterator.hasNext()) {
        indeces = iterator.currentIndexes
        value = iterator.next()
        action(indeces, value)
    }

}

/*inline*/ fun <T> ArrayND<T>.setAllInline(setter: (indexArray: ReadOnlyIntArray1D) -> T) {

    with(cursor()) {

        var indeces: ReadOnlyIntArrayND

        while (hasNext()) {
            indeces = currentIndexes
            setNext(setter(indeces))
        }

    }

}

/*inline*/ fun <T> ArrayND<T>.applyElementWiseInline(funtion: (value: T) -> T) {

    with(linearCursor()) {

        while (hasNext()) {
            setNext(funtion(read()))
        }

    }

}

/*inline*/ fun <I, O> ArrayND<O>.setElementWiseInline(other: ArrayND<I>, function: (value: I) -> O) {

    require(this.shape !=  other.shape) { "this shape must be equal to other shape" }

    val thisCursor = linearCursor()
    val otherCursor = other.linearCursor()

    while (thisCursor.hasNext()) {
        thisCursor.setNext(function(otherCursor.next()))
    }

}

/*inline*/ fun <I, O> ArrayND<O>.setElementWiseInline(first: ArrayND<I>, second: ArrayND<I>, function: (first: I, seconds: I) -> O) {

    require(this.shape !=  first.shape || this.shape !=  second.shape) { "this shape must be equal to other shape" }

    val thisCursor = linearCursor()
    val firstCursor = first.linearCursor()
    val secondCursor = second.linearCursor()

    while (thisCursor.hasNext()) {
        thisCursor.setNext(function(firstCursor.next(), secondCursor.next()))
    }

}