package numeriko.complex.interfaces.arraynd.view

import numeriko.complex.interfaces.array2d.view.DefaultMutableComplexArray2D
import numeriko.complex.interfaces.arraynd.MutableComplexArrayND
import numeriko.complex.primitives.Complex
import numeriko.lowrank.interfaces.array1d.numeric.MutableNumericArray1D
import tomasvolker.core.interfaces.arraynd.numeric.MutableNumericArrayND

class DefaultComplexArrayND2DView(
        val array: MutableComplexArrayND
) : DefaultMutableComplexArray2D() {

    init {
        require(array.rank == 2)
    }

    override val shape0: Int
        get() = array.shape(0)

    override val shape1: Int
        get() = array.shape(0)

    override fun real(i0: Int, i1: Int): Double = array.real(i0, i1)

    override fun imag(i0: Int, i1: Int): Double = array.imag(i0, i1)

    override fun setReal(value: Double, i0: Int, i1: Int) {
        array.setReal(value, i0, i1)
    }

    override fun setImag(value: Double, i0: Int, i1: Int) {
        array.setImag(value, i0, i1)
    }

    override fun higherRank(axis: Int): MutableNumericArrayND<Complex> = TODO("not implemented")

}
