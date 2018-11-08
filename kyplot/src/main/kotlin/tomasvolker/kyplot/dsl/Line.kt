package tomasvolker.kyplot.dsl

import tomasvolker.kyplot.model.Color
import tomasvolker.kyplot.model.LineStyle
import tomasvolker.kyplot.model.MarkerStyle
import tomasvolker.kyplot.model.drawing.Line

inline fun Line.Builder.lineStyle(init: LineStyle.Builder.()->Unit) {
    lineStyle.apply(init)
}

inline fun Line.Builder.markerStyle(init: MarkerStyle.Builder.()->Unit) {
    markerStyle.apply(init)
}

var Line.Builder.color: Color
    get() = lineStyle.color
    set(value) { lineStyle.color = value }
