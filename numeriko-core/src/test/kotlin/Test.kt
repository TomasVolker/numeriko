import tomasvolker.numeriko.core.dsl.A
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.interfaces.slicing.`~`
import tomasvolker.numeriko.core.interfaces.slicing.get

fun main() {

    val array = A[
            A[A[1, 2, 3],
              A[4, 5, 6]],
            A[A[1, 5, 3],
              A[4, 5, 6]]
    ]



}
