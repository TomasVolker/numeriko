package tomasvolker.numeriko.legacy.core.jvm.factory
/*
import tomasvolker.numeriko.legacy.core.jvm.generic.arraynd.JvmArrayND
import tomasvolker.numeriko.legacy.core.jvm.generic.arraynd.setAllInline
import tomasvolker.numeriko.legacy.core.jvm.int.arraynd.JvmIntArrayND

import tomasvolker.numeriko.legacy.core.interfaces.factory.ArrayNDFactory
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.legacy.core.jvm.int.array0d.JvmIntArray0D
import tomasvolker.numeriko.legacy.core.util.computeSizeFromShape
/*
val jvmArrayNDFactory = JvmArrayNDFactory()

class JvmArrayNDFactory: ArrayNDFactory {

    override fun intArray0DOf(value: Int) =
            JvmIntArray0D(value)

    override fun intZeros(s0: Int) =
            JvmIntArray1D(IntArray(s0))

    override fun intArray1D(s0: Int, value: (i0: Int) -> Int) =
            JvmIntArray1D(IntArray(s0, value))

    override fun intArray1DOf(vararg values: Int) =
            JvmIntArray1D(values)

    override fun <T> arrayND(vararg shape: Int, value: (index: ReadOnlyIntArray1D) -> T): JvmArrayND<T> {
        val result = JvmArrayND(
                data = allocateData(shape) as Array<T?>,
                shapeArray = shape
        )
        result.setAllInline(value)
        return result as JvmArrayND<T>
    }

    override fun <T> arrayND(shape: ReadOnlyIntArray1D, value: (index: ReadOnlyIntArray1D) -> T): JvmArrayND<T> {
        val shapeArray = shape.getDataAsIntArray()
        val result = JvmArrayND(
                data = allocateData(shapeArray) as Array<T?>,
                shapeArray = shapeArray
        )
        result.setAllInline(value)
        return result as JvmArrayND<T>
    }

    override fun intArrayND(vararg shape: Int, value: (index: ReadOnlyIntArray1D) -> Int): JvmIntArrayND {
        val result = JvmIntArrayND(
                data = allocateIntData(shape),
                shapeArray = shape
        )
        result.setAllInline(value)
        return result
    }

    override fun intArrayND(shape: ReadOnlyIntArray1D, value: (index: ReadOnlyIntArray1D) -> Int): JvmIntArrayND {
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

    override fun intZeros(shape: ReadOnlyIntArray1D): JvmIntArrayND =
            intZeros(*shape.getDataAsIntArray())

    private fun allocateIntData(shapeArray: IntArray) = IntArray(computeSizeFromShape(shapeArray))

    private fun allocateData(shapeArray: IntArray) = Array<Any?>(computeSizeFromShape(shapeArray)) { null }

}

*/