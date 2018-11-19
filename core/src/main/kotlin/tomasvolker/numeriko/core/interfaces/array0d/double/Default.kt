package tomasvolker.numeriko.core.interfaces.array0d.double

class DefaultDoubleArray0DIterator(
        val array: DoubleArray0D
): DoubleIterator() {

    var read: Boolean = false

    override fun hasNext(): Boolean = !read

    override fun nextDouble(): Double = array.getDouble()

}
