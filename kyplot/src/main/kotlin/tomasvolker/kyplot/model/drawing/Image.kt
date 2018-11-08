package tomasvolker.kyplot.model.drawing

import tomasvolker.kyplot.model.PlotDsl

data class Image(
    val data: Iterable<Iterable<Double>>,
    override val label: String = "",
    val alpha: Double = 1.0
): Drawing {

    @PlotDsl
    class Builder(
        var data: Iterable<Iterable<Double>> = emptyList(),
        var label: String = "",
        var alpha: Double = 1.0
    ): Drawing.Builder {

        override fun build() =
            Image(
                data = data,
                label = label,
                alpha = alpha
            )

    }

}