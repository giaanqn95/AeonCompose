package com.example.aeoncompose.ui.base_view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

@Composable
        /**
         * [fixedCount] is total item per row.
         * */
fun GridLayout(
    modifier: Modifier = Modifier,
    fixedCount: Int = 4 /*default*/,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        /**
        Để không bị sai trong trường hợp [measurables.size/fixedCount] không tròn (làm tròn xuống) => tính sai số hàng => [parentHeight] sẽ không hiển
        thị hàng cuối cùng.
        Cách giải quyết:
        => [positionRow] là vị trí của hàng đang có
        => [totalRow] = [positionRow] + 1
        => [parentHeight] = [childSize] * [totalRow]
         **/
        var totalRow = 0
        val childX = IntArray(measurables.size) { 0 }
        val childY = IntArray(measurables.size) { 0 }
        val childSize = constraints.maxWidth / fixedCount
        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints.copy(childSize, childSize, childSize, childSize))
            val positionColumn = index % fixedCount
            val positionRow = index / fixedCount
            childX[index] = placeable.width * positionColumn
            childY[index] = placeable.height * positionRow
            totalRow = positionRow + 1
            placeable
        }

        val parentWidth = constraints.maxWidth
        val parentHeight = (childSize * totalRow)

        layout(parentWidth, parentHeight) {
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(childX[index], childY[index])
            }
        }
    }
}
