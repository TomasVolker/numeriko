import org.junit.Test
import tomasvolker.numeriko.core.array.arrayNDArrayFactory
import tomasvolker.numeriko.core.debug.DebugArrays
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.index.rangeTo
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.interfaces.integer.get
import tomasvolker.numeriko.core.interfaces.integer.set

class NDArrayTest {

    @Test
    fun printIntNDArray() {

        val array: ReadOnlyIntNDArray = arrayNDArrayFactory.intArray(2, 2, 2) {
            it[0] + it[1] + it[2]
        }

        assert(array.toString() == "[ [ [ 0, 1] , [ 1, 2] ] , [ [ 1, 2] , [ 2, 3] ] ] ") {
            "Array of shape [2, 2, 2] of added indexes is not printed correctly"
        }

    }

    @Test
    fun copyView() {

        val array = DebugArrays.debugIntArray(4, 3, 5)

        val view = array[1..2, All, 2..Last]

        val viewCopy = view.copy()

        assert(view == viewCopy) {
            "View is not equal to a copy of it"
        }

        array[1, 2, 3] = -1

        assert(view != viewCopy) {
            "View of modified array is equal to a copy of it before being modified"
        }

    }

}

