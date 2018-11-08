package tomasvolker.kyplot.model.drawing

import tomasvolker.kyplot.model.PlotDsl

data class CrossSpectralDensity(
    val signal1: Iterable<Number> = emptyList(),
    val signal2: Iterable<Number> = emptyList(),
    val samplingFrequency: Number = 1.0,
    override val label: String = ""
): Drawing {

    @PlotDsl
    class Builder(
        var signal1: Iterable<Number> = emptyList(),
        var signal2: Iterable<Number> = emptyList(),
        var samplingFrequency: Number = 1.0,
        var label: String = ""
    ): Drawing.Builder {

        override fun build() =
            CrossSpectralDensity(
                signal1 = signal1,
                signal2 = signal2,
                samplingFrequency = samplingFrequency,
                label = label
            )

    }

}