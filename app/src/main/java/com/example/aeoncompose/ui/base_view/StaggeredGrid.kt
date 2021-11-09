package com.example.aeoncompose.ui.base_view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 4,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val rowWidths = IntArray(rows) { 0 }

        // Keep track of the max height of each row
        val rowHeights = IntArray(rows) { 0 }

        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.mapIndexed { index, measurable ->
            // Measure each child
            val placeable = measurable.measure(constraints)

            // Track the width and max height of each row
            val row = index % rows
//            rowWidths[row] += placeable.width
//            rowHeights[row] = max(rowHeights[row], placeable.height)

            rowWidths[row] = placeable.width
            rowHeights[row] = placeable.height

            placeable
        }
//        * (measurables.size / rows)
        val width = constraints.maxWidth

        // Grid's height is the sum of the tallest element of each row
        // coerced to the height constraints
        val height = (rowHeights.maxOrNull()?.coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))
            ?: constraints.maxHeight) * (measurables.size / rows)

        // Y of each row, based on the height accumulation of previous rows
//        val rowY = IntArray(rows) { 0 }
//        for (i in 1 until rows) {
//            rowY[i] = rowY[i-1] + rowHeights[i-1]
//        }
//
//        layout(width, height) {
//            // x cord we have placed up to, per row
//            val rowX = IntArray(rows) { 0 }
//
//            placeables.forEachIndexed { index, placeable ->
//                val row = index % rows
//                placeable.placeRelative(
//                    x = rowX[row],
//                    y = rowY[row]
//                )
//                rowX[row] += placeable.width
//            }
//        }

        val rowX = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowX[i] = rowX[i - 1] + rowWidths[i - 1]
        }

        layout(width, height) {
            // x cord we have placed up to, per row
            val rowY = IntArray(rows) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowY[row] += placeable.height
            }
        }
    }
}
