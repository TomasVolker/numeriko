package tomasvolker.kyplot.model

data class MarkerStyle(
    val type: MarkerType = MarkerType.NONE,
    val size: Number = 1.0,
    val color: Color = Color.Auto,
    val alpha: Number = 1.0
    ) {

    @PlotDsl
    class Builder(
        var type: MarkerType = MarkerType.NONE,
        var size: Number = 1.0,
        var color: Color = Color.Auto,
        var alpha: Number = 1.0
        ) {
        fun build() = MarkerStyle(
            type = type,
            size = size,
            color = color,
            alpha = alpha
        )
    }

}