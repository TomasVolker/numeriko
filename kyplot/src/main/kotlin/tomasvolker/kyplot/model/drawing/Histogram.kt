package tomasvolker.kyplot.model.drawing

import tomasvolker.kyplot.model.Color
import tomasvolker.kyplot.model.PlotDsl

data class Histogram(
    val data: Iterable<Number>,
    val bins: Int = 10,
    override val label: String = "",
    val normalized: Boolean = false,
    val color: Color = Color.Auto,
    val alpha: Double = 1.0
): Drawing {

    @PlotDsl
    class Builder(
        var data: Iterable<Number> = emptyList(),
        var bins: Int = 10,
        var label: String = "",
        var normalized: Boolean = false,
        var color: Color = Color.Auto,
        var alpha: Double = 1.0
    ): Drawing.Builder {

        override fun build() =
            Histogram(
                data = data,
                bins = bins,
                label = label,
                normalized = normalized,
                color = color,
                    alpha = alpha
            )

    }

}