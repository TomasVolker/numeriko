package tomasvolker.numeriko.complex.interfaces.array1d.view

import tomasvolker.numeriko.complex.interfaces.array1d.MutableComplexArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1D

class ComplexArray1DRealView(
        val array: MutableComplexArray1D
): DefaultMutableDoubleArray1D() {

    override val size: Int
        get() = array.size

    override fun get(i0: Int): Double {
        requireValidIndices(i0)
        return array.real(i0)
    }

    override fun set(i0: Int, value: Double) {
        requireValidIndices(i0)
        array.setReal(value, i0)
    }

}

class ComplexArray1DImagView(
        val array: MutableComplexArray1D
): DefaultMutableDoubleArray1D() {

    override val size: Int
        get() = array.size

    override fun get(i0: Int): Double {
        requireValidIndices(i0)
        return array.imag(i0)
    }

    override fun set(i0: Int, value: Double) {
        requireValidIndices(i0)
        array.setImag(value, i0)
    }

}

class ComplexArray1DAbsView(
        val array: MutableComplexArray1D
): DefaultMutableDoubleArray1D() {

    override val size: Int
        get() = array.size

    override fun get(i0: Int): Double {
        requireValidIndices(i0)
        return array.abs(i0)
    }

    override fun set(i0: Int, value: Double) {
        requireValidIndices(i0)
        array.setAbs(value, i0)
    }

}

class ComplexArray1DArgView(
        val array: MutableComplexArray1D
): DefaultMutableDoubleArray1D() {

    override val size: Int
        get() = array.size

    override fun get(i0: Int): Double {
        requireValidIndices(i0)
        return array.arg(i0)
    }

    override fun set(i0: Int, value: Double) {
        requireValidIndices(i0)
        array.setArg(value, i0)
    }

}