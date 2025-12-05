package com.louisgautier.composeApp.session

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.louisgautier.apicontracts.dto.Graphic
import com.louisgautier.composeApp.design.ai.Green700
import com.louisgautier.composeApp.design.modifier.dashedBorder
import com.louisgautier.composeApp.design.previewGraphic
import com.louisgautier.composeApp.drawing.DrawableArea
import com.louisgautier.domain.model.Difficulty
import com.louisgautier.domain.model.Response
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
fun GraphicPager(
    difficulty: Difficulty,
    graphic: Graphic,
    modifier: Modifier = Modifier,
    onComplete: (Response) -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .dashedBorder(
                2.dp,
                Green700,
                RoundedCornerShape(16.dp),
                on = 10.dp,
                off = 10.dp,
            ),
    ) {
        DrawableArea(
            medians = graphic.medians,
            drawReference = difficulty != Difficulty.HARD,
            drawHint = difficulty == Difficulty.EASY,
            onComplete = { strokes, result ->
                onComplete(Response(graphic.code, result, strokes))
            }
        )
    }
}

class DifficultyPreviewParameter() : PreviewParameterProvider<Difficulty> {
    override val values: Sequence<Difficulty>
        get() = Difficulty.entries.asSequence()
}

@Preview
@Composable
fun GraphicPagerPreview(
    @PreviewParameter(DifficultyPreviewParameter::class) difficulty: Difficulty
) {
    GraphicPager(difficulty, previewGraphic)
}