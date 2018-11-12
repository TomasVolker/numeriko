package tomasvolker.numeriko.sandbox.image

import tomasvolker.kyplot.model.*
import tomasvolker.kyplot.dsl.*
import tomasvolker.kyplot.model.drawing.Image
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.toListOfLists
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros

inline fun showImage(
        data: DoubleArray2D = doubleZeros(0, 0),
        title: String = "",
        init: Image.Builder.()->Unit = {}
) = showImage(data.toListOfLists(), title, init)


inline fun Plot.Builder.image(
        data: DoubleArray2D = doubleZeros(0, 0),
        init: Image.Builder.()->Unit = {}
) = image(data.toListOfLists(), init)
