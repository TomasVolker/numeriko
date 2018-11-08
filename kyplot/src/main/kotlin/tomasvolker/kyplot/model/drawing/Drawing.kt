package tomasvolker.kyplot.model.drawing

interface Drawing {

    val label: String

    interface Builder {
        fun build(): Drawing
    }
}

