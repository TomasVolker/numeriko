package tomasvolker.numeriko.core.implementations.numeriko.array0d.double

import tomasvolker.numeriko.core.implementations.numeriko.NumerikoDoubleArray
import tomasvolker.numeriko.core.interfaces.array0d.double.DefaultMutableDoubleArray0D
import tomasvolker.numeriko.core.interfaces.array0d.generic.DefaultMutableArray0D

class NumerikoDoubleArray0D(
        var data: Double
): DefaultMutableDoubleArray0D() {

    override fun get(): Double = data

    override fun set(value: Double) {
        data = value
    }

}

class NumerikoDoubleArray0DView(
        val data: DoubleArray,
        val offset: Int
): DefaultMutableDoubleArray0D(), NumerikoDoubleArray {

    init {
        require(offset in 0 until data.size)
    }

    override val backingArray: DoubleArray
        get() = data

    override fun get(): Double = data[offset]

    override fun set(value: Double) {
        data[offset] = value
    }

}