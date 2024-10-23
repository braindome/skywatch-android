package se.braindome.skywatch.ui.home.cards

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class PressureData(val time: Float, val pressure: Float)

@Composable
fun PressureChart() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val padding = 16.dp.toPx()
        val data = listOf(
            PressureData(0f, 1013f),
            PressureData(4f, 1015f),
            PressureData(8f, 1012f),
            PressureData(12f, 1018f),
            PressureData(16f, 1016f)
        )

        drawLine(
            start = Offset(padding, height - padding),
            end = Offset(width - padding, height - padding),
            color = Color.Black,
            strokeWidth = 2.dp.toPx()
        )

        drawLine(
            start = Offset(padding, height - padding),
            end = Offset(width - padding, height - padding),
            color = Color.Black,
            strokeWidth = 2.dp.toPx()
        )

        val maxTime = data.maxOf { it.time }
        val maxPressure = data.maxOf { it.pressure }
        val path = Path().apply {
            data.forEachIndexed { index, point ->
                val x = padding + (point.time / maxTime) * (width - padding * 2)
                val y = height - padding - (point.pressure / maxPressure) * (height - padding * 2)
                if (index == 0) {
                    moveTo(x, y)
                } else {
                    lineTo(x, y)
                }
            }
        }

        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PressureChartPreview() {
    PressureChart()
}