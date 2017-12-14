import org.junit.Test
import tomasvolker.numeriko.core.array.arrayNDArrayFactory
import tomasvolker.numeriko.core.array.integer.toNDArray
import tomasvolker.numeriko.core.interfaces.integer.IntNDArray
import tomasvolker.numeriko.core.interfaces.integer.get
import tomasvolker.numeriko.core.interfaces.integer.set
import tomasvolker.numeriko.core.util.indexArrayToLinearIndex
import tomasvolker.numeriko.core.util.linearIndexToIndexArray

class NDArrayTest {

    @Test
    fun testIntNDArray() {

        val a: IntNDArray = arrayNDArrayFactory.intArray(3, 2) {
            it[0] * it[1]
        }

        println(a)

        println(a[0..2, 0])

        assert(a.dimension == 2) { "Wrong dimension" }

        assert(a.size == 6) { "Wrong size" }

        assert(a[1, 1] == 1) { "Wrong value" }

        assert(a[-1, -1] == 2) { "Wrong value" }

        val b = a.copy()

        assert(a == b)

        b[2, 1] = -9

        assert(a != b)

        val shape = intArrayOf(4, 3, 8)

        assert(indexArrayToLinearIndex(shape, linearIndexToIndexArray(shape.toNDArray(), 32)) == 32)

    }
/*
    @Test
    fun testStringNDArray() {

        val a: NDArray<String> = ArrayNDArray(15,22,12) {
            "(${it[0]}, ${it[1]}, ${it[2]})"
        }

        assert(a.dimension == 3) { "Wrong dimension" }

        assert(a.size == 15*22*12) { "Wrong size, ${a.size}" }

        a[8, 6, 0] = "hola"
        a[12, 2, 6] = "aloja"
        a[14, 21, 11] = "salut"

        assert(a[14, 21, 11] == "salut") { "Wrong value" }

        val b = a.copy()

        assert(a == b)

        b[2, 1, 9] = "chau"

        assert(a != b)

    }
*/
}

