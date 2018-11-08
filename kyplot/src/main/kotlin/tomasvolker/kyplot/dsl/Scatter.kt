package tomasvolker.kyplot.dsl

import tomasvolker.kyplot.model.Color
import tomasvolker.kyplot.model.MarkerStyle
import tomasvolker.kyplot.model.drawing.Scatter

inline fun Scatter.Builder.markerStyle(init: MarkerStyle.Builder.()->Unit) {
    markerStyle.apply(init)
}

var Scatter.Builder.color: Color
    get() = markerStyle.color
    set(value) { markerStyle.color = value }

var Scatter.Builder.alpha: Number
    get() = markerStyle.alpha
    set(value) { markerStyle.alpha = value }