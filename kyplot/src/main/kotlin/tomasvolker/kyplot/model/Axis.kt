package tomasvolker.kyplot.model

data class Axis(
    val label: String = "",
    val limits: Limits = Limits.Auto,
    val tickPositions: TickPositions = TickPositions.Auto,
    val scale: Scale = Scale.LINEAR
) {

    enum class Scale {
        LINEAR,
        LOGARITHMIC
    }

    sealed class Limits {
        object Auto: Limits()
        data class Explicit(val min: Number, val max: Number): Limits()
    }

    sealed class TickPositions {
        object Auto: TickPositions()
        data class Explicit(val tickList: List<Tick>): TickPositions()
    }

    data class Tick(val position: Number, val label: String = "")

    @PlotDsl
    class Builder(
        var label: String = "",
        var limits: Limits = Limits.Auto,
        var tickPositions: TickPositions = TickPositions.Auto,
        var scale: Scale = Scale.LINEAR
    ) {

        infix fun Number.upTo(upper: Number) = Limits.Explicit(this, upper)

        fun build() = Axis(
            label = label,
            limits = limits,
            tickPositions = tickPositions,
            scale = scale
        )
    }
}