package tomasvolker.numeriko.complex.array.view

import tomasvolker.numeriko.complex.array.ComplexArray1D
import tomasvolker.numeriko.complex.array.MutableComplexArray1D
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

    override fun getReal(i: Int): Double {
        if (i !in 0 until size) {
            throw IndexOutOfBoundsException("$i")
        }

        return array.getReal(offset + stride * i)
    }

    override fun setReal(value: Double, i: Int) {
        if (i !in 0 until size) {
            throw IndexOutOfBoundsException("$i")
        }

        array.setReal(value, offset + stride * i)
    }

    override fun getImag(i: Int): Double {
        if (i !in 0 until size) {
            throw IndexOutOfBoundsException("$i")
        }

        return array.getImag(offset + stride * i)
    }

    override fun setImag(value: Double, i: Int) {
        if (i !in 0 until size) {
            throw IndexOutOfBoundsException("$i")
        }

        array.setImag(value, offset + stride * i)
    }

}

class ComplexArray1DRealView(
        val array: MutableComplexArray1D
): DefaultMutableDoubleArray1D() {

    override val size: Int
        get() = array.size

    override fun getDouble(index: Int): Double = array.getReal(index)

    override fun setDouble(value: Double, index: Int) {
        array.setReal(value, index)
    }

}

class ComplexArray1DImagView(
        val array: MutableComplexArray1D
): DefaultMutableDoubleArray1D() {

    override val size: Int
        get() = array.size

    override fun getDouble(index: Int): Double = array.getImag(index)

    override fun setDouble(value: Double, index: Int) {
        array.setImag(value, index)
    }

}

class ComplexArray1DAbsView(
        val array: MutableComplexArray1D
): DefaultMutableDoubleArray1D() {

    override val size: Int
        get() = array.size

    override fun getDouble(index: Int): Double = array.getAbs(index)

    override fun setDouble(value: Double, index: Int) {
        array.setAbs(value, index)
    }

}

class ComplexArray1DArgView(
        val array: MutableComplexArray1D
): DefaultMutableDoubleArray1D() {

    override val size: Int
        get() = array.size

    override fun getDouble(index: Int): Double = array.getArg(index)

    override fun setDouble(value: Double, index: Int) {
        array.setArg(value, index)
    }

}