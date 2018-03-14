package tomasvolker.numeriko.core.interfaces.int.arraynd

import tomasvolker.numeriko.core.interfaces.int.array1d.ReadOnlyIntArray1D

fun ReadOnlyIntArrayND.get(): Int = getInt()

operator fun ReadOnlyIntArrayND.get(vararg indexes: Int): Int =
        getInt(*indexes)

operator fun ReadOnlyIntArrayND.get(indexArray: ReadOnlyIntArray1D): Int =
        getInt(indexArray)

operator fun IntArrayND.get(vararg indexes: Int): Int =
        getInt(*indexes)

operator fun IntArrayND.get(indexArray: ReadOnlyIntArray1D): Int =
        getInt(indexArray)

fun IntArrayND.get(): Int = getInt()

operator fun IntArrayND.set(vararg indexes: Int, value: Int) =
        setInt(value, *indexes)

operator fun IntArrayND.set(indexArray: ReadOnlyIntArray1D, value: Int) =
        setInt(value, indexArray)
