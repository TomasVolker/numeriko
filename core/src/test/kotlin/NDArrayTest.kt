import org.junit.Test
import tomasvolker.numeriko.core.array.arrayNDArrayFactory
import tomasvolker.numeriko.core.array.index.All
import tomasvolker.numeriko.core.array.index.Last
import tomasvolker.numeriko.core.array.integer.toNDArray
import tomasvolker.numeriko.core.interfaces.integer.IntNDArray
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.interfaces.integer.get
import tomasvolker.numeriko.core.interfaces.integer.set
import tomasvolker.numeriko.core.util.indexArrayToLinearIndex
import tomasvolker.numeriko.core.util.linearIndexToIndexArray

class NDArrayTest {

    @Test
    fun printIntNDArray() {

        val array: ReadOnlyIntNDArray = arrayNDArrayFactory.intArray(2, 2, 2) {
            it[0] + it[1] + it[2]
        }

        println(array)

        assert(array.toString() == "[ [ [ 0, 1] , [ 1, 2] ] , [ [ 1, 2] , [ 2, 3] ] ] ") {
            "Array of shape [2, 2, 2] of added indexes is not printed correctly"
        }

    }

}

