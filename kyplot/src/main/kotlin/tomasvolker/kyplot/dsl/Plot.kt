package tomasvolker.kyplot.dsl

import tomasvolker.kyplot.model.*
import tomasvolker.kyplot.model.drawing.*


inline fun Plot.Builder.position(init: PlotPosition.Builder.()->Unit) {
    position.apply(init)
}

inline fun Plot.Builder.xAxis(init: Axis.Builder.()->Unit) {
    xAxis.apply(init)
}

inline fun Plot.Builder.yAxis(init: Axis.Builder.()->Unit) {
    yAxis.apply(init)
}

inline fun Plot.Builder.grid(init: Plot.Grid.Builder.()->Unit) {
    grid.apply(init)
}

inline fun Plot.Builder.legend(init: Legend.Builder.()->Unit) {
    legend.apply(init)
}

inline fun Plot.Grid.Builder.lineStyle(init: LineStyle.Builder.()->Unit) {
    lineStyle.apply(init)
}

inline fun <T: Drawing.Builder> Plot.Builder.drawing(
    drawing: T,
    init: T.()->Unit = {}
) {
    drawingList.add(drawing.apply(init))
}

inline fun Plot.Builder.line(
    x: Iterable<Number> = emptyList(),
    y: Iterable<Number> = emptyList(),
    init: Line.Builder.()->Unit = {}
) {
    drawing(Line.Builder()) {
        this.x = x
        this.y = y
        init()
    }
}

inline fun Plot.Builder.spectrumMagnitude(
    signal: Iterable<Number> = emptyList(),
    samplingFrequency: Number = 1.0,
    init: SpectrumMagnitude.Builder.()->Unit = {}
) {
    drawing(SpectrumMagnitude.Builder()) {
        this.signal = signal
        this.samplingFrequency = samplingFrequency
        init()
    }
}


inline fun Plot.Builder.spectrumPhase(
    signal: Iterable<Number> = emptyList(),
    samplingFrequency: Number = 1.0,
    init: SpectrumPhase.Builder.()->Unit = {}
) {
    drawing(SpectrumPhase.Builder()) {
        this.signal = signal
        this.samplingFrequency = samplingFrequency
        init()
    }
}

inline fun Plot.Builder.powerSpectralDensity(
    signal: Iterable<Number> = emptyList(),
    samplingFrequency: Number = 1.0,
    init: PowerSpectralDensity.Builder.()->Unit = {}
) {
    drawing(PowerSpectralDensity.Builder()) {
        this.signal = signal
        this.samplingFrequency = samplingFrequency
        init()
    }
}

inline fun Plot.Builder.crossSpectralDensity(
    signal1: Iterable<Number> = emptyList(),
    signal2: Iterable<Number> = emptyList(),
    samplingFrequency: Number = 1.0,
    init: CrossSpectralDensity.Builder.()->Unit = {}
) {
    drawing(CrossSpectralDensity.Builder()) {
        this.signal1 = signal1
        this.signal2 = signal2
        this.samplingFrequency = samplingFrequency
        init()
    }
}

inline fun Plot.Builder.histogram(
    data: Iterable<Number> = emptyList(),
    init: Histogram.Builder.()->Unit = {}
) {
    drawing(Histogram.Builder()) {
        this.data = data
        init()
    }
}

inline fun Plot.Builder.scatter(
    x: Iterable<Number> = emptyList(),
    y: Iterable<Number> = emptyList(),
    init: Scatter.Builder.()->Unit = {}
) {
    drawing(Scatter.Builder()) {
        this.x = x
        this.y = y
        init()
    }
}

inline fun Plot.Builder.bar(
    x: Iterable<Number> = emptyList(),
    heights: Iterable<Number> = emptyList(),
    init: Bar.Builder.()->Unit = {}
) {
    drawing(Bar.Builder()) {
        this.x = x
        this.heights = heights
        init()
    }
}

inline fun Plot.Builder.image(
    data: Iterable<Iterable<Number>> = emptyList(),
    init: Image.Builder.()->Unit = {}
) {
    drawing(Image.Builder()) {
        this.data = data
        init()
    }
}

inline fun showPlot(title: String = "", init: Plot.Builder.()->Unit) {
    showFigure {
        plot(title, init)
    }
}

inline fun Plot.Builder.stem(
    x: Iterable<Number> = emptyList(),
    y: Iterable<Number> = emptyList(),
    init: Stem.Builder.()->Unit = {}
) {
    drawing(Stem.Builder()) {
        this.x = x
        this.y = y
        init()
    }
}