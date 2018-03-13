package tomasvolker.numeriko.core.interfaces.int.arraynd

fun ReadOnlyIntNDArray.get(): Int = getInt()

operator fun ReadOnlyIntNDArray.get(vararg indexes: Int): Int =
        getInt(*indexes)

operator fun ReadOnlyIntNDArray.get(indexArray: ReadOnlyIntNDArray): Int =
        getInt(indexArray)

operator fun IntNDArray.get(vararg indexes: Int): Int =
        getInt(*indexes)

operator fun IntNDArray.get(indexArray: ReadOnlyIntNDArray): Int =
        getInt(indexArray)

fun IntNDArray.get(): Int = getInt()

operator fun IntNDArray.set(vararg indexes: Int, value: Int) =
        setInt(value, *indexes)

operator fun IntNDArray.set(indexArray: ReadOnlyIntNDArray, value: Int) =
        setInt(value, indexArray)
