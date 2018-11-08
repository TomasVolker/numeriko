package tomasvolker.numeriko.zoo.image

import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

val Int.alpha: Int get() = (this and 0x00FFFFFF.inv()) shr 24
val Int.red  : Int get() = (this and 0x00FF0000) shr 16
val Int.green: Int get() = (this and 0x0000FF00) shr 8
val Int.blue : Int get() = (this and 0x000000FF)


fun BufferedImage.toDoubleArray2D(): DoubleArray2D =
        doubleArray2D(height, width) { y, x ->
            val rgb = getRGB(x, y)
            (rgb.red + rgb.green + rgb.blue) / (3.0 * 255.0)
        }

/*
fun BufferedImage.toBooleanArray2D(): Array2D<Boolean> =
        array2D(height, width) { y, x ->
            val rgb = getRGB(x, y)
            (rgb.red + rgb.green + rgb.blue) / (3.0 * 255.0)
        }
*/

fun loadGrayScaleImage(file: File): DoubleArray2D =
        ImageIO.read(file).toDoubleArray2D()

fun loadGrayScaleImage(path: String): DoubleArray2D =
        loadGrayScaleImage(File(path))
