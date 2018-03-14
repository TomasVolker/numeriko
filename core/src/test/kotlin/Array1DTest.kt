import org.junit.Test
import tomasvolker.numeriko.core.interfaces.factory.intArray1D

class Array1DTest {

    @Test
    fun createArray() {

        val array = intArray1D(5) { i -> 2*i }

        println(array)

    }

}
