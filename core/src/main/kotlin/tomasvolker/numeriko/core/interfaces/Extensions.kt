package tomasvolker.numeriko.core.interfaces

import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray

operator fun <T> ReadOnlyNDArray<T>.get(vararg indeces: Int) =
        getValue(*indeces)

operator fun <T> ReadOnlyNDArray<T>.get(indexArray: Any): ReadOnlyNDArray<T> =
        getView(indexArray)

operator fun <T> ReadOnlyNDArray<T>.get(indexArray: ReadOnlyIntNDArray) =
        getValue(indexArray)

operator fun <T> NDArray<T>.get(vararg indeces: Int) =
        getValue(*indeces)

operator fun <T> NDArray<T>.get(indexArray: ReadOnlyIntNDArray) =
        getValue(indexArray)

operator fun <T> NDArray<T>.get(indexArray: Any): NDArray<T> =
        getView(indexArray)

operator fun <T> NDArray<T>.set(vararg indeces: Int, value: T) =
        setValue(value, *indeces)

operator fun <T> NDArray<T>.set(vararg indeces: Any, valueArray: ReadOnlyNDArray<T>) =
        setValue(valueArray, *indeces)

operator fun <T> NDArray<T>.set(indexArray: ReadOnlyIntNDArray, value: T) =
        setValue(value, indexArray)



/*inline*/ fun <T> NDArray<T>.forEachIndexed(action: (indexArray: ReadOnlyIntNDArray, value: T) -> Unit) {

    val iterator = cursor()

    var indeces: ReadOnlyIntNDArray
    var value: T
    while (iterator.hasNext()) {
        indeces = iterator.currentIndexes
        value = iterator.next()
        action(indeces, value)
    }

}

/*inline*/ fun <T> NDArray<T>.setAllInline(setter: (indexArray: ReadOnlyIntNDArray) -> T) {

    with(cursor()) {

        var indeces: ReadOnlyIntNDArray

        while (hasNext()) {
            indeces = currentIndexes
            setNext(setter(indeces))
        }

    }

}

/*inline*/ fun <T> NDArray<T>.applyElementWiseInline(funtion: (value: T) -> T) {

    with(linearCursor()) {

        while (hasNext()) {
            setNext(funtion(read()))
        }

    }

}

/*inline*/ fun <I, O> NDArray<O>.setElementWiseInline(other: NDArray<I>, function: (value: I) -> O) {

    require(this.shape !=  other.shape) { "this shape must be equal to other shape" }

    val thisCursor = linearCursor()
    val otherCursor = other.linearCursor()

    while (thisCursor.hasNext()) {
        thisCursor.setNext(function(otherCursor.next()))
    }

}

/*inline*/ fun <I, O> NDArray<O>.setElementWiseInline(first: NDArray<I>, second: NDArray<I>, function: (first: I, seconds: I) -> O) {

    require(this.shape !=  first.shape || this.shape !=  second.shape) { "this shape must be equal to other shape" }

    val thisCursor = linearCursor()
    val firstCursor = first.linearCursor()
    val secondCursor = second.linearCursor()

    while (thisCursor.hasNext()) {
        thisCursor.setNext(function(firstCursor.next(), secondCursor.next()))
    }

}