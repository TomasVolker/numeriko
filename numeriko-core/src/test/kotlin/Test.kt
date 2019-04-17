import tomasvolker.numeriko.core.dsl.A
import tomasvolker.numeriko.core.interfaces.arraynd.generic.arrayAlongAxis

fun main() {

    println(
            A[
             A[A[1, 2, 3],
               A[4, 5, 6]],
             A[A[1, 5, 3],
               A[4, 5, 6]]
            ].arrayAlongAxis(0, 1)
    )

}
