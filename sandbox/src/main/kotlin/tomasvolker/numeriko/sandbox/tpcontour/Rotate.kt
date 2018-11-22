package tomasvolker.numeriko.sandbox.tpcontour

import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.lastIndex0
import tomasvolker.numeriko.core.interfaces.array2d.generic.lastIndex1
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.sandbox.image.loadGrayScaleImage
import tomasvolker.numeriko.sandbox.image.saveGrayScale
import java.io.File

fun DoubleArray2D.rotate180(): DoubleArray2D =
        doubleArray2D(shape0, shape1) { i0, i1 ->
            this[lastIndex0-i0, lastIndex1-i1]
        }

fun main() {

    val directory = "./sandbox/res/contour/"

    val result = directory + "rotated/"

    val dir = File(directory)

    dir.listFiles().forEach { file ->

        if(file.isFile) {
            val image = loadGrayScaleImage(file)

            val rotated = image.rotate180()

            saveGrayScale(rotated, File(result + file.name))
        }
    }

}