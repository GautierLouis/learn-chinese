package com.louisgautier.composeApp.drawing.legacy


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.louisgautier.composeApp.drawing.TransformStroke
import com.louisgautier.composeApp.drawing.dashEffect
import com.louisgautier.composeApp.drawing.drawingDetector
import com.louisgautier.composeApp.drawing.medianToPath
import com.louisgautier.composeApp.drawing.pointsToPath
import com.louisgautier.composeApp.drawing.toOffset
import dtwSimilaritySymmetric
import kotlin.math.min

@Deprecated("-")
@Composable
fun DrawingCanvas(
    modifier: Modifier = Modifier,
    strokes: List<String>,
    medians: List<List<List<Double>>>,
    mode: CharacterDrawMode,
    onDrawingResult: (Float) -> Unit
) {

    var currentStrokeIndex by rememberSaveable { mutableIntStateOf(0) }

    val currentPoints = remember { mutableStateListOf<Offset>() }
    var canvasSize by remember { mutableStateOf(IntSize(0, 0)) }

    val strokesAsPath = TransformStroke.transformPath(strokes, canvasSize)

    val completedPath = strokesAsPath.subList(0, minOf(currentStrokeIndex, strokes.size))
        .takeIf { currentStrokeIndex > 0 }

    val medianToShow = medians.getOrNull(currentStrokeIndex)?.let {
        TransformStroke.transformPath(listOf(medianToPath(it)), canvasSize).first()
    }

    val strokeWidth = 40f
    val strokeColor = Color.DarkGray

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .onGloballyPositioned { coordinates -> canvasSize = coordinates.size }
            .drawingDetector(currentPoints) {
                val transformedPath = TransformStroke.transformOffset(currentPoints.toList(), canvasSize)
                val result = dtwSimilaritySymmetric(transformedPath, medianToShow!!.toOffset())

                currentStrokeIndex++
                currentPoints.clear()

                if (currentStrokeIndex == strokes.size - 1) {
                    onDrawingResult(result.toFloat())
                }
            }
    ) {

        val svgSize = 1024f
        val scaleFactor = min(size.width / svgSize, size.height / svgSize)
        if (mode == CharacterDrawMode.MODEL_GUIDE) {
            strokesAsPath.forEach { line ->
                drawPath(
                    path = line,
                    color = Color.LightGray,
                )
            }
        }

        completedPath?.forEach { line ->
            drawPath(
                path = line,
                color = Color.DarkGray,
            )
        }

        if ((mode == CharacterDrawMode.GUIDE_LINE || mode == CharacterDrawMode.MODEL_GUIDE) && medianToShow != null) {
            drawPath(
                path = medianToShow,
                color = Color.Red,
                style = Stroke(
                    width = 20f,
                    miter = 1f,
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.Companion.dashEffect(scaleFactor)
                )
            )
        }

        // Draw the ongoing path
        if (currentPoints.isNotEmpty()) {
            val livePath = currentPoints.pointsToPath()
            drawPath(
                path = livePath,
                color = strokeColor,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
    }
}

