package tomasvolker.numeriko.core.interfaces.int.arraynd

fun ReadOnlyIntArrayND.get(): Int = getInt()

operator fun ReadOnlyIntArrayND.get(vararg indexes: Int): Int =
        getInt(*indexes)

operator fun ReadOnlyIntArrayND.get(indexArray: ReadOnlyIntArrayND): Int =
        getInt(indexArray)

operator fun IntArrayND.get(vararg indexes: Int): Int =
        getInt(*indexes)

operator fun IntArrayND.get(indexArray: ReadOnlyIntArrayND): Int =
        getInt(indexArray)

fun IntArrayND.get(): Int = getInt()

operator fun IntArrayND.set(vararg indexes: Int, value: Int) =
        setInt(value, *indexes)

operator fun IntArrayND.set(indexArray: ReadOnlyIntArrayND, value: Int) =
        setInt(value, indexArray)
