package tomasvolker.numeriko.complex.interfaces.array1d.view

import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array1d.MutableComplexArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString


abstract class DefaultComplexArray1D: ComplexArray1D {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableComplexArray1D: DefaultComplexArray1D(), MutableComplexArray1D


class DefaultMutableComplexArray1DView (
        val array: MutableComplexArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : DefaultMutableComplexArray1D() {

    override fun real(i0: Int): Double {
        requireValidIndices(i0)
        return array.real(offset + stride * i0)
    }

    override fun setReal(value: Double, i0: Int) {
        requireValidIndices(i0)
        array.setReal(value, offset + stride * i0)
    }

    override fun imag(i0: Int): Double {
        requireValidIndices(i0)
        return array.imag(offset + stride * i0)
    }

    override fun setImag(value: Double, i0: Int) {
        requireValidIndices(i0)
        array.setImag(value, offset + stride * i0)
    }

}

class ComplexArray1DRealView(
        val array: MutableComplexArray1D
): DefaultMutableDoubleArray1D() {

    override val size: Int
        get() = array.size

    override fun getDouble(i0: Int): Double {
        requireValidIndices(i0)
        return array.real(i0)
    }

    override fun setDouble(value: Double, i0: Int) {
        requireValidIndices(i0)
        array.setReal(value, i0)
    }

}

class ComplexArray1DImagView(
        val array: MutableComplexArray1D
): DefaultMutableDoubleArray1D() {

    override val size: Int
        get() = array.size

    override fun getDouble(i0: Int): Double {
        requireValidIndices(i0)
        return array.imag(i0)
    }

    override fun setDouble(value: Double, i0: Int) {
        requireValidIndices(i0)
        array.setImag(value, i0)
    }

}

class ComplexArray1DAbsView(
        val array: MutableComplexArray1D
): DefaultMutableDoubleArray1D() {

    override val size: Int
        get() = array.size

    override fun getDouble(i0: Int): Double {
        requireValidIndices(i0)
        return array.abs(i0)
    }

    override fun setDouble(value: Double, i0: Int) {
        requireValidIndices(i0)
        array.setAbs(value, i0)
    }

}

class ComplexArray1DArgView(
        val array: MutableComplexArray1D
): DefaultMutableDoubleArray1D() {

    override val size: Int
        get() = array.size

    override fun getDouble(i0: Int): Double {
        requireValidIndices(i0)
        return array.arg(i0)
    }

    override fun setDouble(value: Double, i0: Int) {
        requireValidIndices(i0)
        array.setArg(value, i0)
    }

}