package tomasvolker.numeriko.core.jvm.factory

import tomasvolker.numeriko.core.jvm.generic.arraynd.JvmArrayND
import tomasvolker.numeriko.core.jvm.generic.arraynd.setAllInline
import tomasvolker.numeriko.core.jvm.int.arraynd.JvmIntArrayND
import tomasvolker.numeriko.core.jvm.int.arraynd.setAllInline
import tomasvolker.numeriko.core.interfaces.factory.ArrayNDFactory
import tomasvolker.numeriko.core.interfaces.int.array1d.IntArray1D
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntArrayND
import tomasvolker.numeriko.core.jvm.int.array1d.JvmIntArray1D
import tomasvolker.numeriko.core.util.computeSizeFromShape

val jvmArrayNDFactory = JvmArrayNDFactory()

class JvmArrayNDFactory: ArrayNDFactory {

    override fun intZeros(s0: Int): IntArray1D = JvmIntArray1D(IntArray(s0))

    override fun intArray1D(s0: Int, value: (i0: Int) -> Int): IntArray1D =
            JvmIntArray1D(IntArray(s0, value))

    override fun <T> arrayND(vararg shape: Int, value: (index: ReadOnlyIntArrayND) -> T): JvmArrayND<T> {
        val result = JvmArrayND(
                data = allocateData(shape) as Array<T?>,
                shapeArray = shape
        )
        result.setAllInline(value)
        return result as JvmArrayND<T>
    }

    override fun <T> arrayND(shape: ReadOnlyIntArrayND, value: (index: ReadOnlyIntArrayND) -> T): JvmArrayND<T> {
        val shapeArray = shape.getDataAsIntArray()
        val result = JvmArrayND(
                data = allocateData(shapeArray) as Array<T?>,
                shapeArray = shapeArray
        )
        result.setAllInline(value)
        return result as JvmArrayND<T>
    }

    override fun intArrayND(vararg shape: Int, value: (index: ReadOnlyIntArrayND) -> Int): JvmIntArrayND {
        val result = JvmIntArrayND(
                data = allocateIntData(shape),
                shapeArray = shape
        )
        result.setAllInline(value)
        return result
    }

    override fun intArrayND(shape: ReadOnlyIntArrayND, value: (index: ReadOnlyIntArrayND) -> Int): JvmIntArrayND {
        val shapeArray = shape.getDataAsIntArray()
        val result = JvmIntArrayND(
                data = allocateIntData(shapeArray),
                shapeArray = shapeArray
        )
        result.setAllInline(value)
        return result
    }

    override fun intZeros(vararg shape: Int): JvmIntArrayND =
            JvmIntArrayND(
                    data = allocateIntData(shape),
                    shapeArray = shape
            )

    override fun intZeros(shape: ReadOnlyIntArrayND): JvmIntArrayND =
            intZeros(*shape.getDataAsIntArray())

    private fun allocateIntData(shapeArray: IntArray) = IntArray(computeSizeFromShape(shapeArray))

    private fun allocateData(shapeArray: IntArray) = Array<Any?>(computeSizeFromShape(shapeArray)) { null }

}

