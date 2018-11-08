package tomasvolker.kyplot.model

data class LineStyle(
    val color: Color = Color.Auto,
    val alpha: Number = 1.0,
    val type: LineType = LineType.SOLID,
    val width: Number = 1.0
) {

    @PlotDsl
    class Builder(
        var color: Color = Color.Auto,
        var alpha: Number = 1.0,
        var type: LineType = LineType.SOLID,
        var width: Number = 1.0
    ) {
        fun build() = LineStyle(
            color = color,
            alpha = alpha,
            type = type,
            width = width
        )
    }

}