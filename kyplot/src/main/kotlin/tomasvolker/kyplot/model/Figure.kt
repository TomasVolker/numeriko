package tomasvolker.kyplot.model

import tomasvolker.kyplot.plotter.KyPlot
import tomasvolker.kyscript.readKyScript

@DslMarker
annotation class PlotDsl

data class Figure(
    val title: String = "",
    val plotList: List<Plot> = emptyList()
) {
    @PlotDsl
    class Builder(
        var title: String = "",
        var plotList: MutableList<Plot.Builder> = mutableListOf(),
        var defaultPlotInit: Plot.Builder.()->Unit = {}
    ) {
        fun build() = Figure(
                title = title,
                plotList = plotList.map { it.build() }
        )
    }
}

fun Figure.show() {
    readKyScript(
        KyPlot().also {
            it.show(this)
        }.build()
    )
}
