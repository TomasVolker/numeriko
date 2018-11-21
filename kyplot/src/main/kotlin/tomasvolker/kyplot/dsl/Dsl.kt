package tomasvolker.kyplot.dsl

import tomasvolker.kyplot.model.drawing.*

inline fun showHistogram(
    data: Iterable<Number> = emptyList(),
    title: String = "",
    init: Histogram.Builder.()->Unit = {}
) {
    showPlot(title) {
        histogram(
            data = data,
            init = init
        )
    }
}


inline fun showLine(
    x: Iterable<Number> = emptyList(),
    y: Iterable<Number> = emptyList(),
    title: String = "",
    init: Line.Builder.()->Unit = {}
) {
    showPlot(title) {
        line(
            x = x,
            y = y,
            init = init
        )
    }
}

inline fun showScatter(
    x: Iterable<Number> = emptyList(),
    y: Iterable<Number> = emptyList(),
    title: String = "",
    init: Scatter.Builder.()->Unit = {}
) {
    showPlot(title) {
        scatter(
            x = x,
            y = y,
            init = init
        )
    }
}

inline fun showStem(
    x: Iterable<Number> = emptyList(),
    y: Iterable<Number> = emptyList(),
    init: Stem.Builder.()->Unit = {}
) {
    showPlot {
        stem(
            x = x,
            y = y,
            init = init
        )
    }
}

inline fun showImage(
    data: Iterable<Iterable<Number>> = emptyList(),
    title: String = "",
    init: Image.Builder.()->Unit = {}
) {
    showPlot(title) {
        grid.visible = false
        image(
            data = data,
            init = init
        )
    }
}