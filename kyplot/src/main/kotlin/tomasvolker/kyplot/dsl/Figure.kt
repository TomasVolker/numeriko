package tomasvolker.kyplot.dsl

import tomasvolker.kyplot.model.Figure
import tomasvolker.kyplot.model.Plot
import tomasvolker.kyplot.model.show

inline fun showFigure(init: Figure.Builder.()->Unit) {
    Figure.Builder()
        .apply(init)
        .build()
        .show()
}

inline fun Figure.Builder.plot(title: String = "", init: Plot.Builder.()->Unit) {
    plotList.add(
        Plot.Builder()
                .apply(defaultPlotInit)
                .apply {
                    this.title = title
                    init()
                }
    )
}

fun Figure.Builder.allPlots(init: Plot.Builder.()->Unit) {
    defaultPlotInit = init
}