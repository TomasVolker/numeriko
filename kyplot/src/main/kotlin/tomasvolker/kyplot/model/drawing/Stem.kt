package tomasvolker.kyplot.model.drawing

import tomasvolker.kyplot.model.LineStyle
import tomasvolker.kyplot.model.MarkerStyle
import tomasvolker.kyplot.model.PlotDsl

data class Stem(
    val x: Iterable<Number>,
    val y: Iterable<Number>,
    val markerStyle: MarkerStyle,
    val lineStyle: LineStyle = LineStyle(),
    override val label: String = ""
): Drawing {

    @PlotDsl
    class Builder(
        var x: Iterable<Number> = emptyList(),
        var y: Iterable<Number> = emptyList(),
        var markerStyle: MarkerStyle.Builder = MarkerStyle.Builder(),
        var lineStyle: LineStyle.Builder = LineStyle.Builder(),
        var label: String = ""
    ): Drawing.Builder {

        override fun build() =
            Stem(
                x = x,
                y = y,
                markerStyle = markerStyle.build(),
                lineStyle = lineStyle.build(),
                label = label
            )
    }

}