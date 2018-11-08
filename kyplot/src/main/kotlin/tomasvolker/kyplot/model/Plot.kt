package tomasvolker.kyplot.model

import tomasvolker.kyplot.model.drawing.Drawing

data class Plot(
    val title: String = "",
    val xAxis: Axis = Axis(),
    val yAxis: Axis = Axis(),
    val drawingList: List<Drawing> = emptyList(),
    val position: PlotPosition = PlotPosition(),
    val grid: Grid = Grid(),
    val legend: Legend = Legend()
) {

    @PlotDsl
    class Builder(
        var title: String = "",
        var xAxis: Axis.Builder = Axis.Builder(),
        var yAxis: Axis.Builder = Axis.Builder(),
        var drawingList: MutableList<Drawing.Builder> = mutableListOf(),
        var position: PlotPosition.Builder = PlotPosition.Builder(),
        var grid: Grid.Builder = Grid.Builder(),
        var legend: Legend.Builder = Legend.Builder()
    ) {

        fun build() = Plot(
            title = title,
            xAxis = xAxis.build(),
            yAxis = yAxis.build(),
            drawingList = drawingList.map { it.build() },
            position = position.build(),
            grid = grid.build(),
            legend = legend.build()
        )

    }

    data class Grid(
        val lineStyle: LineStyle = LineStyle(),
        val visible: Boolean = true
    ) {

        @PlotDsl
        class Builder(
            var lineStyle: LineStyle.Builder = LineStyle.Builder(),
            var visible: Boolean = true
        ) {

            fun build() =
                Grid(
                    lineStyle = lineStyle.build(),
                    visible = visible
                )

        }

    }

}
