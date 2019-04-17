package numeriko.lowrank.interfaces.array1d.generic.view

import numeriko.lowrank.interfaces.array1d.generic.Array1D
import numeriko.lowrank.interfaces.array1d.generic.DefaultArray1DIterator

class Default1DArrayListView<T>(
        val array: Array1D<T>
): List<T> {

    override val size: Int
        get() = array.size

    override fun get(index: Int): T = array.getValue(index)

    override fun indexOf(element: @UnsafeVariance T): Int {
        for (i in indices) {
            if (element == array.getValue(i)) {
                return i
            }
        }
        return -1
    }

    override fun lastIndexOf(element: @UnsafeVariance T): Int {
        for (i in indices.reversed()) {
            if (element == array.getValue(i)) {
                return i
            }
        }
        return -1
    }

    override fun contains(element:@UnsafeVariance T): Boolean =
            any { it == element }

    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean =
            elements.all { this.contains(it) }

    override fun isEmpty(): Boolean = size == 0

    override fun iterator(): Iterator<T> = listIterator()

    override fun listIterator(): ListIterator<T> = DefaultArray1DIterator(array)

    override fun listIterator(index: Int): ListIterator<T> = DefaultArray1DIterator(array, index)

    override fun subList(fromIndex: Int, toIndex: Int): List<T> =
            array.getView(fromIndex until toIndex).asList()


}