package tomasvolker.numeriko.core.jvm.int.arraynd

import tomasvolker.numeriko.core.interfaces.integer.arraynd.IntArrayNDIterator

class JvmIntArrayNDIterator(override val array: JvmIntArrayND): IntArrayNDIterator {

    private val data = array.data

    private var index: Int = 0

    override fun readInt() = data[index]

    override fun writeInt(value: Int) {
        data[index] = value
    }

    override fun incrementBy(amount: Int) {
        index += amount
    }

    override fun decrementBy(amount: Int) {
        index -= amount
    }

    override fun moveToFirst() {
        index = 0
    }

    override fun moveToLast() {
        index = data.lastIndex
    }

    override fun cursorInBounds() = index in data.indices

}
