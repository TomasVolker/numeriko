package tomasvolker.numeriko.core.interfaces.integer

fun ReadOnlyIntNDArray.get(): Int = getInt()

operator fun ReadOnlyIntNDArray.get(vararg indexes: Int): Int =
        getInt(*indexes)

operator fun ReadOnlyIntNDArray.get(vararg indexArray: Any): ReadOnlyIntNDArray =
        getView(*indexArray)

operator fun ReadOnlyIntNDArray.get(indexArray: ReadOnlyIntNDArray): Int =
        getInt(indexArray)

operator fun IntNDArray.get(vararg indexes: Int): Int =
        getInt(*indexes)

operator fun IntNDArray.get(indexArray: ReadOnlyIntNDArray): Int =
        getInt(indexArray)

operator fun IntNDArray.get(vararg indexArray: Any): IntNDArray =
        getView(*indexArray)

fun IntNDArray.get(): Int = getInt()

operator fun IntNDArray.set(vararg indexes: Int, value: Int) =
        setInt(value, *indexes)

operator fun IntNDArray.set(vararg indexes: Any, valueArray: ReadOnlyIntNDArray) =
        setInt(valueArray, *indexes)

operator fun IntNDArray.set(indexArray: ReadOnlyIntNDArray, value: Int) =
        setInt(value, indexArray)
