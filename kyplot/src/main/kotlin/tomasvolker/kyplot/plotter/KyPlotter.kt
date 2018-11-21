package tomasvolker.kyplot.plotter

import tomasvolker.kyplot.model.*
import tomasvolker.kyplot.model.drawing.*
import tomasvolker.kyplot.model.LineType.*
import tomasvolker.kyplot.model.MarkerType.*
import tomasvolker.kyplot.model.MarkerFillStyle.*
import tomasvolker.kyplot.model.Legend.Position.*
import tomasvolker.kyscript.KyScriptWriter
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

class KyPlot: KyScriptWriter() {

    val plt = id("plt")

    val figure   = plt.id("figure")
    val suptitle = plt.id("suptitle")
    val subplot  = plt.id("subplot")
    val show     = plt.id("show")

    init {
        importAs("numpy", "np")
        importAs("matplotlib.pyplot", "plt")
        fromImport("matplotlib", "cm")
    }

    fun show(figure: Figure) {

        +figure()
        +suptitle(figure.title)

        for (plot in figure.plotList) {

            with(plot.position) {

                +subplot(
                    rowCount,
                    columnCount,
                    1 + column + row * columnCount
                )

            }

            buildPlot(plot)
        }

        +show()

    }

    private fun buildPlot(plot: Plot) {

        with(plot) {
            +plt.id("title")(title)

            drawingList.forEach { buildDrawing(it) }

            +plt.id("xlabel")(xAxis.label)
            +plt.id("ylabel")(yAxis.label)

            +plt.id("xscale")(xAxis.scale.toPythonText())
            +plt.id("yscale")(yAxis.scale.toPythonText())

            when(xAxis.limits) {
                is Axis.Limits.Explicit -> {
                    +plt.id("xlim")(xAxis.limits.min, xAxis.limits.max)
                }
            }

            when(yAxis.limits) {
                is Axis.Limits.Explicit -> {
                    +plt.id("ylim")(yAxis.limits.min, yAxis.limits.max)
                }
            }

            when(xAxis.tickPositions) {
                is Axis.TickPositions.Explicit -> {
                    +plt.id("xticks")(
                        xAxis.tickPositions.tickList.map { it.position },
                        xAxis.tickPositions.tickList.map { it.label }
                    )
                }
            }

            when(yAxis.tickPositions) {
                is Axis.TickPositions.Explicit -> {
                    +plt.id("yticks")(
                        yAxis.tickPositions.tickList.map { it.position },
                        yAxis.tickPositions.tickList.map { it.label }
                    )
                }
            }

            if (grid.visible) {

                +plt.id("grid")(
                    "linestyle" setTo grid.lineStyle.type.toPythonText(),
                    "linewidth" setTo grid.lineStyle.width,
                    "alpha" setTo grid.lineStyle.alpha
                )

                if (grid.lineStyle.color !is Color.Auto) {
                    +plt.id("grid")(
                        "color" setTo grid.lineStyle.color.toPythonColor()
                    )
                }

            }

            if (legend.visible) {
                +plt.id("legend")("loc" setTo legend.position.toPythonString())
            }

        }

    }

    fun plt(functionName: String, args: List<Any?>, kwargs: Map<String, Any?>) {
        +plt.id(functionName)(
                inject("*${args.toPythonExpression()}"),
                inject("**${kwargs.toPythonExpression()}")
        )
    }


    private fun buildDrawing(drawing: Drawing) {
        when(drawing) {
            is Line -> {
                plt("plot",
                    listOf(drawing.x, drawing.y),
                    mapOf(
                        "label"      to drawing.label,
                        "linewidth"  to drawing.lineStyle.width,
                        "lineStyle"  to drawing.lineStyle.type.toPythonText(),
                        "color"      to drawing.lineStyle.color.toPythonColor(),
                        "alpha"      to drawing.lineStyle.alpha,
                        "marker"     to drawing.markerStyle.type.toPythonText(),
                        "markersize" to drawing.markerStyle.size,
                        "zorder"     to 3
                    )
                )
            }
            is Histogram -> {
                plt("hist",
                    listOf(),
                    mapOf(
                        "x"       to drawing.data,
                        "bins"    to drawing.bins,
                        "label"   to drawing.label,
                        "density" to drawing.normalized,
                        "zorder"  to 3,
                        "color"   to drawing.color.toPythonColor(),
                        "alpha"   to drawing.alpha
                    )
                )
            }
            is SpectrumMagnitude -> {
                +plt.id("magnitude_spectrum")(
                    drawing.signal,
                    "label" setTo drawing.label,
                    "Fs" setTo drawing.samplingFrequency
                )
            }
            is SpectrumPhase -> {
                +plt.id("phase_spectrum")(
                    drawing.signal,
                    "label" setTo drawing.label,
                    "Fs" setTo drawing.samplingFrequency
                )
            }
            is PowerSpectralDensity -> {
                +plt.id("psd")(
                    drawing.signal,
                    "label" setTo drawing.label,
                    "Fs" setTo drawing.samplingFrequency
                )
            }
            is CrossSpectralDensity -> {
                +plt.id("csd")(
                    drawing.signal1,
                    drawing.signal2,
                    "label" setTo drawing.label,
                    "Fs" setTo drawing.samplingFrequency
                )
            }
            is Scatter -> {
                +plt.id("scatter")(
                    drawing.x,
                    drawing.y,
                    "marker" setTo drawing.markerStyle.type.toPythonText(),
                    "label" setTo drawing.label,
                    "alpha" setTo drawing.markerStyle.alpha,
                    "color" setTo  drawing.markerStyle.color.toPythonColor(),
                    "zorder" setTo 3 // To show over the grid
                )
            }
            is Bar -> {
                +plt.id("bar")(
                    "x" setTo drawing.x,
                    "heights" setTo drawing.heights,
                    "label" setTo drawing.label,
                    "align" setTo drawing.alignment.toPythonText(),
                    "width" setTo drawing.width,
                    "color" setTo (drawing.color.toPythonColor() ?: Color.BLUE.toPythonColor())
                )
            }
            is Stem -> {
                +plt.id("stem")(
                    drawing.x,
                    drawing.y,
                    "label" setTo drawing.label,
                    "linefmt" setTo (drawing.lineStyle.color.toPythonString() + drawing.lineStyle.type.toPythonText()),
                    "markerfmt" setTo ((drawing.markerStyle.type.toPythonText() ?: ".") +
                            drawing.markerStyle.color.toPythonString())
                )
            }
            is Image -> {
                +plt.id("imshow")(
                    drawing.data,
                    "label" setTo drawing.label,
                    "alpha" setTo drawing.alpha,
                    "cmap" setTo "gray"
                )
            }
        }
    }

    override fun serializeToPython(value: Any?): String = when(value) {
        is ArrayND<*> -> value.toString()
        is Iterable<*> -> {
            when(value) {
                is Set<*> -> super.serializeToPython(value)
                is Map<*, *> -> super.serializeToPython(value)
                else -> super.serializeToPython(value.toList())
            }
        }
        else -> super.serializeToPython(value)
    }

}

fun Axis.Scale.toPythonText(): String = when(this) {
    Axis.Scale.LINEAR -> "linear"
    Axis.Scale.LOGARITHMIC -> "log"
}

fun Color.toPythonColor(): List<Number>? = when(this) {
    is Color.Auto -> null
    is Color.Explicit -> listOf(red.apply {  }, green, blue)
}

fun LineType.toPythonText(): String = when(this) {
    SOLID -> "-"
    DASHED -> "--"
    DASH_DOT -> "-."
    DOTTED -> ":"
}

fun MarkerType.toPythonText(): String? = when(this) {
    MarkerType.NONE -> null
    POINT -> "."
    PIXEL -> ","
    CIRCLE -> "o"
    TRIANGLE_DOWN -> "v"
    TRIANGLE_UP -> "^"
    TRIANGLE_LEFT -> "<"
    TRIANGLE_RIGHT -> ">"
    TRI_DOWN -> "1"
    TRI_UP -> "2"
    TRI_LEFT -> "3"
    TRI_RIGHT -> "4"
    SQUARE -> "s"
    PENTAGON -> "p"
    STAR -> "*"
    HEXAGON_1 -> "h"
    HEXAGON_2 -> "H"
    PLUS -> "+"
    X -> "x"
    X_FILLED -> "X"
    DIAMOND -> "D"
    THIN_DIAMOND -> "d"
    VERTICAL_LINE -> "|"
    HORIZONTAL_LINE -> "_"
}

fun MarkerFillStyle.toPythonText(): String = when(this) {
    MarkerFillStyle.NONE -> "none"
    FULL -> "full"
    LEFT -> "left"
    MarkerFillStyle.RIGHT -> "right"
    BOTTOM -> "bottom"
    TOP -> "top"
}

fun BarAlignment.toPythonText(): String = when(this) {
    BarAlignment.CENTER -> "center"
    BarAlignment.EDGE -> "edge"
}

fun Color.toPythonString(): String? =
    when(this) {
        Color.BLUE -> "b"
        Color.GREEN -> "g"
        Color.RED -> "r"
        Color.CYAN -> "c"
        Color.MAGENTA -> "m"
        Color.YELLOW -> "y"
        Color.BLACK -> "k"
        Color.Auto -> ""
        else -> null
    }

fun Legend.Position.toPythonString(): String? =
    when(this) {
        AUTO -> "best"
        UPPER_RIGHT -> "upper right"
        UPPER_LEFT -> "upper left"
        LOWER_LEFT -> "lower left"
        LOWER_RIGHT -> "lower right"
        CENTER_LEFT -> "center left"
        CENTER_RIGHT -> "center right"
        CENTER_LOWER -> "lower center"
        CENTER_UPPER -> "upper center"
        CENTER -> "center"
    }