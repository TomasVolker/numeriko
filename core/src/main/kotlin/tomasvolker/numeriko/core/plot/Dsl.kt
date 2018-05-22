package tomasvolker.numeriko.core.plot

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D

fun style(init: PlotLine.Style.Builder.()->Unit) =
        PlotLine.Style.Builder().apply(init).build()

fun plot(init: Plot.() -> Unit) = Plot().apply(init)

fun Plot.line(x: DoubleArray1D, y: DoubleArray1D) = PlotLine(x, y).also { add(it) }

fun Plot.line(x: DoubleArray1D, y: DoubleArray1D, style: PlotLine.Style) =
        PlotLine(x, y, style).also { add(it) }

fun Plot.line(x: DoubleArray1D, y: DoubleArray1D, styleInit: PlotLine.Style.Builder.()->Unit) =
        line(x, y, style(styleInit))
