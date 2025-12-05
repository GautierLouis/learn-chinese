package com.louisgautier.composeApp.drawing.legacy

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.louisgautier.composeApp.drawing.drawingDetector
import kotlinx.coroutines.delay
import kotlin.math.hypot


@Composable
fun SimpleDrawing(
    pathData: List<String>,
) {

    val currentPoints = remember { mutableStateListOf<Offset>() }
    var canvasSize by remember { mutableStateOf(IntSize(0, 0)) }

    val strokes: List<Path> = remember(pathData, canvasSize) {
        if (canvasSize.width == 0 || canvasSize.height == 0) {
            emptyList()
        } else {
            pathData.map { median ->
                playground(median)
            }
        }
    }

    val animatable = remember(strokes) {
        strokes.map { Animatable(0f) }
    }

    LaunchedEffect(strokes) {
        if (strokes.isEmpty()) return@LaunchedEffect
        do {
            for (i in strokes.indices) {
                animatable[i].snapTo(0f)
                animatable[i].animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
                )
                delay(80L)
            }
        } while (true)
    }

    val strokeStyle = Stroke(width = 40f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    var animatePath by remember { mutableStateOf(false) }
    val animatedProgress by animateFloatAsState(
        targetValue = if (animatePath) 1f else 0f,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        label = "PathAnimationProgress", finishedListener = { }
    )

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(true) { animatePath = !animatePath }
            .onGloballyPositioned { coordinates -> canvasSize = coordinates.size }
            .drawingDetector(currentPoints) {}
    ) {

        //Placeholder
//        strokes.forEach {
//            drawPath(
//                path = buildPathUpTo(it, 1f),
//                color = Color.LightGray,
//            )
//        }

        strokes.forEachIndexed { index, path ->
            val pathMeasure = PathMeasure()
            pathMeasure.setPath(path, false)
            val segmentPath = Path()
            pathMeasure.getSegment(0f, animatedProgress * pathMeasure.length, segmentPath, true)
            drawPath(segmentPath, color = Color.Blue)
        }


        //Animated
//        strokes.forEachIndexed { index, path ->
//            val progress = animatable.getOrNull(index)?.value ?: 0f
//            drawPath(
//                path = buildFilledStrokePathUpTo(path, progress, 40f),
//                color = Color.Black,
//            )
//        }

//        if (currentPoints.isNotEmpty()) {
//            val livePath = currentPoints.pointsToPath()
//            drawPath(
//                path = livePath,
//                color = Color.Black,
//                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
//            )
//        }
    }

}


fun playground(stroke: String): Path {
    val path = PathParser()
    return path.parsePathString(stroke).toPath()
    //return path.parsePathString(stroke).toPath().transformWith(matrix)
}

private fun dist(a: Offset, b: Offset): Float = hypot((a.x - b.x), (a.y - b.y))
private fun lerp(a: Offset, b: Offset, t: Float): Offset =
    Offset(a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t)


private fun buildPathUpTo(points: List<Offset>, progress: Float): Path {
    val path = Path()
    if (points.isEmpty()) return path
    path.moveTo(points[0].x, points[0].y)
    if (progress <= 0f) return path
    if (progress >= 1f) {
        for (i in 1 until points.size) path.lineTo(points[i].x, points[i].y)
        return path
    }

    // compute segment lengths
    val segLens = FloatArray(maxOf(1, points.size - 1))
    var total = 0f
    for (i in 0 until points.size - 1) {
        val l = dist(points[i], points[i + 1])
        segLens[i] = l
        total += l
    }
    if (total <= 0f) return path

    val target = total * progress
    var acc = 0f
    for (i in 0 until points.size - 1) {
        val p0 = points[i]
        val p1 = points[i + 1]
        val seg = segLens[i]
        if (acc + seg < target) {
            path.lineTo(p1.x, p1.y)
            acc += seg
        } else {
            val need = target - acc
            val t = if (seg <= 0f) 0f else need / seg
            val pt = lerp(p0, p1, t)
            path.lineTo(pt.x, pt.y)
            break
        }
    }
    return path
}

private fun normalize(v: Offset): Offset {
    val len = hypot(v.x, v.y)
    return if (len == 0f) Offset.Zero else Offset(v.x / len, v.y / len)
}
