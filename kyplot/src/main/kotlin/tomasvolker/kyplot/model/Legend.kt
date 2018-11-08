package tomasvolker.kyplot.model


data class Legend(
    val visible: Boolean = false,
    val position: Position = Position.AUTO
) {

    enum class Position {
        AUTO,
        UPPER_RIGHT,
        UPPER_LEFT,
        LOWER_LEFT,
        LOWER_RIGHT,
        CENTER_LEFT,
        CENTER_RIGHT,
        CENTER_UPPER,
        CENTER_LOWER,
        CENTER
    }

    @PlotDsl
    class Builder(
        var visible: Boolean = false,
        var position: Position = Position.AUTO
    ) {

        fun build() = Legend(
            visible = visible,
            position = position
        )
    }
}