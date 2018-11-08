package tomasvolker.kyplot.dsl

import tomasvolker.kyplot.model.LineStyle
import tomasvolker.kyplot.model.MarkerStyle
import tomasvolker.kyplot.model.drawing.Stem

inline fun Stem.Builder.lineStyle(init: LineStyle.Builder.()->Unit) {
    lineStyle.apply(init)
}

inline fun Stem.Builder.markerStyle(init: MarkerStyle.Builder.()->Unit) {
    markerStyle.apply(init)
}