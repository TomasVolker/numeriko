package tomasvolker.numeriko.sandbox.image

import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.array2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND
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


fun BufferedImage.toBooleanArray2D(): Array2D<Boolean> =
        array2D(height, width) { y, x ->
            val rgb = getRGB(x, y)
            (rgb.red + rgb.green + rgb.blue) / (3.0 * 255.0) > 0.5
        }

fun BufferedImage.toDoubleArrayRGB(): DoubleArrayND =
        doubleArrayND(I[height, width, 3]) { (y, x, c) ->
            val rgb = getRGB(x, y)
            when(c) {
                0 -> rgb.red
                1 -> rgb.green
                2 -> rgb.blue
                else -> throw IllegalStateException()
            } / 255.0
        }

fun BufferedImage.toDoubleArrayRGBA(): DoubleArrayND =
        doubleArrayND(I[height, width, 4]) { (y, x, c) ->
            val rgb = getRGB(x, y)
            when(c) {
                0 -> rgb.red
                1 -> rgb.green
                2 -> rgb.blue
                3 -> rgb.alpha
                else -> throw IllegalStateException()
            } / 255.0
        }


fun loadGrayScaleImage(file: File): DoubleArray2D =
        ImageIO.read(file).toDoubleArray2D()

fun loadRGBImage(file: File): DoubleArrayND =
        ImageIO.read(file).toDoubleArrayRGB()

fun loadRGBAImage(file: File): DoubleArrayND =
        ImageIO.read(file).toDoubleArrayRGBA()

fun loadGrayScaleImage(path: String): DoubleArray2D =
        loadGrayScaleImage(File(path))

fun loadRGBImage(path: String): DoubleArrayND =
        loadRGBImage(File(path))

fun loadRGBAImage(path: String): DoubleArrayND =
        loadRGBAImage(File(path))
