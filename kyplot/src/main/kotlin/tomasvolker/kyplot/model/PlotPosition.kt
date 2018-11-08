package tomasvolker.kyplot.model

data class PlotPosition (
    val rowCount: Int = 1,
    val columnCount: Int = 1,
    val row: Int = 0,
    val column: Int = 0
) {
    @PlotDsl
    class Builder(
        var rowCount: Int = 1,
        var columnCount: Int = 1,
        var row: Int = 0,
        var column: Int = 0
    ) {
        fun build() = PlotPosition(
            rowCount = rowCount,
            columnCount = columnCount,
            row = row,
            column = column
        )

        var x: Int
            get() = column
            set(value) { column = value }

        var y: Int
            get() = row
            set(value) { row = value }

        var gridWidth: Int
            get() = columnCount
            set(value) { columnCount = value }

        var gridHeight: Int
            get() = rowCount
            set(value) { rowCount = value }

    }
}



