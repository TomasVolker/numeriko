import org.junit.Test
import tomasvolker.numeriko.core.array.arrayNDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.interfaces.integer.get

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

