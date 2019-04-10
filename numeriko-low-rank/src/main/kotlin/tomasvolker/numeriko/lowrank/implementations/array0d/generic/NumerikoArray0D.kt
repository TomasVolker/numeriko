package tomasvolker.numeriko.lowrank.implementations.array0d.generic

import tomasvolker.numeriko.lowrank.interfaces.array0d.generic.DefaultMutableArray0D

class NumerikoArray0D<T>(
        var data: T
): DefaultMutableArray0D<T>() {

    override fun getValue(): T = data

    override fun setValue(value: T) {
        data = value
    }

}

class NumerikoArray0DView<T>(
        val data: Array<T>,
        val offset: Int
): DefaultMutableArray0D<T>() {

    init {
        require(offset in 0 until data.size)
    }

    override fun getValue(): T = data[offset]

    override fun setValue(value: T) {
        data[offset] = value
    }

}
