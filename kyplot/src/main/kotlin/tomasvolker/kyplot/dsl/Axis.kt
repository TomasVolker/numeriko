package tomasvolker.kyplot.dsl

import tomasvolker.kyplot.model.*

fun Axis.Builder.between(lower: Number, upper: Number) =
    Axis.Limits.Explicit(lower, upper)

fun Plot.Builder.between(lower: Number, upper: Number) =
    Axis.Limits.Explicit(lower, upper)

fun Axis.Builder.auto() = Axis.Limits.Auto

fun Axis.Builder.tickLabels(labels: Iterable<Pair<Number, String>>) {

    this.tickPositions = Axis.TickPositions.Explicit(
        labels.map {
            Axis.Tick(
                position = it.first,
                label = it.second
            )
        }
    )

}

fun Axis.Builder.tickLabels(vararg labels: Pair<Number, String>) {
    tickLabels(labels.toList())
}

fun Axis.Builder.tickPositions(positions: Iterable<Number>) {

    this.tickPositions = Axis.TickPositions.Explicit(
        positions.map {
            Axis.Tick(it, "%.2f".format(it.toDouble()))
        }
    )

}

fun Axis.Builder.tickPositions(vararg positions: Number) {
    tickPositions(positions.toList())
}
