package tomasvolker.numeriko.complex.interfaces.array2d.view

import tomasvolker.numeriko.complex.interfaces.array2d.MutableComplexArray2D
import tomasvolker.numeriko.lowrank.interfaces.array1d.double.view.DefaultMutableDoubleArray1D
import tomasvolker.numeriko.lowrank.interfaces.array2d.double.view.DefaultMutableDoubleArray2D


class ComplexArray2DRealView(
        val array: MutableComplexArray2D
): DefaultMutableDoubleArray2D() {

    override val shape0: Int
        get() = array.shape0

    override val shape1: Int
        get() = array.shape1

    override fun get(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return array.real(i0, i1)
    }

    override fun set(i0: Int, i1: Int, value: Double) {
        requireValidIndices(i0, i1)
        array.setReal(value, i0, i1)
    }

}

class ComplexArray2DImagView(
        val array: MutableComplexArray2D
): DefaultMutableDoubleArray2D() {

    override val shape0: Int
        get() = array.shape0

    override val shape1: Int
        get() = array.shape1

    override fun get(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return array.imag(i0, i1)
    }

    override fun set(i0: Int, i1: Int, value: Double) {
        requireValidIndices(i0, i1)
        array.setImag(value, i0, i1)
    }

}

class ComplexArray2DAbsView(
        val array: MutableComplexArray2D
): DefaultMutableDoubleArray2D() {

    override val shape0: Int
        get() = array.shape0

    override val shape1: Int
        get() = array.shape1

    override fun get(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return array.abs(i0, i1)
    }

    override fun set(i0: Int, i1: Int, value: Double) {
        requireValidIndices(i0, i1)
        array.setAbs(value, i0, i1)
    }

}

class ComplexArray2DArgView(
        val array: MutableComplexArray2D
): DefaultMutableDoubleArray2D() {

    override val shape0: Int
        get() = array.shape0

    override val shape1: Int
        get() = array.shape1

    override fun get(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return array.arg(i0, i1)
    }

    override fun set(i0: Int, i1: Int, value: Double) {
        requireValidIndices(i0, i1)
        array.setArg(value, i0, i1)
    }

}