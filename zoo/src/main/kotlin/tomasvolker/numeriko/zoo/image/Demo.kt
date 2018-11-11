package tomasvolker.numeriko.zoo.image

import tomasvolker.kyplot.dsl.*
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.LiteralIndex
import tomasvolker.numeriko.core.index.Single
import tomasvolker.numeriko.core.interfaces.array2d.double.elementWise
import tomasvolker.numeriko.core.interfaces.arraynd.double.as2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.collapseView
import kotlin.math.hypot

fun main() {

    val rgbImage = loadGrayScaleImage("./zoo/res/sun.gif")

    //val image = rgbImage.getView(All, All, Single(0)).collapseView(2).as2D()
    val image = rgbImage

    val gradx = image.computeGradientX()
    val grady = image.computeGradientY()

    val edges = elementWise(gradx, grady) { x, y ->
        hypot(x, y)
    }

    showFigure {

        allPlots {

            grid.visible = false
            position {
                gridWidth = 2
                gridHeight = 2
            }

        }

        plot("Original") {

            image(image)

            position {
                x = 0
                y = 0
            }
        }

        plot("Edges") {

            image(edges)

            position {
                x = 1
                y = 0
            }
        }

        plot("Gradient X") {

            image(gradx)

            position {
                x = 0
                y = 1
            }
        }

        plot("Gradient Y") {

            image(grady)

            position {
                x = 1
                y = 1
            }
        }

    }

}