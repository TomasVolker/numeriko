package tomasvolker.kyplot.model.drawing

import tomasvolker.kyplot.model.LineStyle
import tomasvolker.kyplot.model.MarkerStyle
import tomasvolker.kyplot.model.PlotDsl

data class Line(
    val x: Iterable<Number>,
    val y: Iterable<Number>,
    override val label: String = "",
    val lineStyle: LineStyle = LineStyle(),
    val markerStyle: MarkerStyle = MarkerStyle()
): Drawing {

    @PlotDsl
    class Builder(
        var x: Iterable<Number> = emptyList(),
        var y: Iterable<Number> = emptyList(),
        var label: String = "",
        var lineStyle: LineStyle.Builder = LineStyle.Builder(),
        var markerStyle: MarkerStyle.Builder = MarkerStyle.Builder()
    ): Drawing.Builder {

        override fun build() =
            Line(
                x = x,
                y = y,
                label = label,
                lineStyle = lineStyle.build(),
                markerStyle = markerStyle.build()
            )

    }

}