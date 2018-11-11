package tomasvolker.numeriko.zoo.image

import tomasvolker.kyplot.model.*
import tomasvolker.kyplot.dsl.*
import tomasvolker.kyplot.model.drawing.Image
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.toListOfLists
import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleArray2D

inline fun showImage(
        data: DoubleArray2D = mutableDoubleArray2D(0, 0) { _, _ -> 0.0 },
        title: String = "",
        init: Image.Builder.()->Unit = {}
) = showImage(data.toListOfLists(), title, init)


inline fun Plot.Builder.image(
        data: DoubleArray2D = mutableDoubleArray2D(0, 0) { _, _ -> 0.0 },
        init: Image.Builder.()->Unit = {}
) = image(data.toListOfLists(), init)