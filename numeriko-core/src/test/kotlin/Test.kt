import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.interfaces.factory.toIntArrayND
import tomasvolker.numeriko.core.interfaces.slicing.*

fun main() {

    val matrix = intArrayOf(
            1, 2, 3,
            4, 6, 5,
            7, 8, 8,
            7, 8, 8
    ).toIntArrayND(I[4, 3]).asMutable()

    val vector = I[1, 2, 3 ,4].expandRank(1)


}
