import org.junit.Test
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.index.rangeTo
import tomasvolker.numeriko.core.interfaces.factory.intNDArray
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.interfaces.int.arraynd.get
import tomasvolker.numeriko.core.interfaces.int.arraynd.set

class NDArrayTest {

    @Test
    fun printIntNDArray() {

        val array: ReadOnlyIntNDArray = intNDArray(2, 2, 2) {
            it[0] + it[1] + it[2]
        }

        assert(array.toString() == "[ [ [ 0, 1] , [ 1, 2] ] , [ [ 1, 2] , [ 2, 3] ] ] ") {
            "Array of shape [2, 2, 2] of added indexes is not printed correctly"
        }

    }

    @Test
    fun copyView() {

        var aux = 0

        val array = intNDArray(4, 3, 5) { aux++ }

        val view = array.view[1..2, All, 2..Last]

        val viewCopy = view.copy()

        assert(view == viewCopy) {
            "View is not equal to a copy of it"
        }

        array[1, 2, 3] = -1

        assert(view != viewCopy) {
            "View of modified jvm is equal to a copy of it before being modified"
        }

    }

}

