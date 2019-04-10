package tomasvolker.numeriko.lowrank.interfaces.array0d.generic

class DefaultArray0DIterator<T>(
        val array: Array0D<T>
): Iterator<T> {

    private var read: Boolean = false

    override fun hasNext(): Boolean = !read

    override fun next(): T =
            array.getValue().apply { read = true }

}