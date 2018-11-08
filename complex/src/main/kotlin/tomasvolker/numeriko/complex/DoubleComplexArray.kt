package tomasvolker.numeriko.complex

import kotlin.math.atan2
import kotlin.math.hypot

class DoubleComplexArray(size: Int): Iterable<DoubleComplex> {

    val realArray = DoubleArray(size)

    val imagArray = DoubleArray(size)

    fun absArray() =
            DoubleArray(size) { i ->
                hypot(realArray[i], imagArray[i])
            }

    fun argArray() =
            DoubleArray(size) { i ->
                atan2(imagArray[i], realArray[i])
            }

    val size: Int get() = realArray.size

    operator fun get(i: Int) = DoubleComplex(realArray[i], imagArray[i])

    operator fun set(i: Int, value: DoubleComplex) =
            set(i, value.real, value.imag)

    fun set(i: Int, real: Double, imag: Double) {
        realArray[i] = real
        imagArray[i] = imag
    }

    fun toList() = List(size) { i-> this[i] }

    override fun iterator() = DoubleComplexArrayIterator(this)

}

class DoubleComplexArrayIterator(
        val array: DoubleComplexArray
): Iterator<DoubleComplex> {

    private var nextIndex: Int = 0

    override fun hasNext(): Boolean = nextIndex < array.size

    override fun next(): DoubleComplex = array[nextIndex].also { nextIndex++ }

}

inline fun DoubleComplexArray(size: Int, init: (i: Int)->DoubleComplex) =
        DoubleComplexArray(size).apply {
            for (i in 0 until size) {
                val value = init(i)
                realArray[i] = value.real
                imagArray[i] = value.imag
            }
        }

inline fun DoubleComplexArray(
        size: Int,
        initReal: (i: Int)->Double,
        initImag: (i: Int)->Double
) = DoubleComplexArray(size).apply {
    for (i in 0 until size) {
        realArray[i] = initReal(i)
        imagArray[i] = initImag(i)
    }
}

inline fun DoubleComplexArray.replaceAll(
        realOp: (real: Double, imag: Double)->Double,
        imagOp: (real: Double, imag: Double)->Double
) {

    for (i in 0 until size) {
        val real = realArray[i]
        val imag = imagArray[i]
        realArray[i] = realOp(real, imag)
        imagArray[i] = imagOp(real, imag)
    }

}

inline fun DoubleComplexArray.map(
        realOp: (real: Double, imag: Double)->Double,
        imagOp: (real: Double, imag: Double)->Double
) = DoubleComplexArray(size).also {

    for (i in 0 until size) {
        val real = this.realArray[i]
        val imag = this.imagArray[i]
        it.realArray[i] = realOp(real, imag)
        it.imagArray[i] = imagOp(real, imag)
    }

}
