package tomasvolker.kyplot.model.drawing

import tomasvolker.kyplot.model.PlotDsl

data class PowerSpectralDensity(
    val signal: Iterable<Number>,
    val samplingFrequency: Number = 1.0,
    override val label: String = ""
): Drawing {

    @PlotDsl
    class Builder(
        var signal: Iterable<Number> = emptyList(),
        var samplingFrequency: Number = 1.0,
        var label: String = ""
    ): Drawing.Builder {

        override fun build() =
            PowerSpectralDensity(
                signal = signal,
                samplingFrequency = samplingFrequency,
                label = label
            )

    }

}