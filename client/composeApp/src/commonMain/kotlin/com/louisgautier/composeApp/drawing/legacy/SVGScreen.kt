package com.louisgautier.composeApp.drawing.legacy

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.louisgautier.apicontracts.dto.Graphic
import com.louisgautier.composeApp.drawing.legacy.SVGViewModel.CharacterUiState.Success
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SVGScreen(
    viewModel: SVGViewModel = koinViewModel(),
    code: Int,
) {
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(code) {
        viewModel.fetchSVG(code)
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.TopCenter
        ) {
            when (uiState) {
                is SVGViewModel.CharacterUiState.Loading -> CircularProgressIndicator()
                is SVGViewModel.CharacterUiState.Success -> DrawableCharacter((uiState as Success).graphic)
                is SVGViewModel.CharacterUiState.Error -> Text(text = (uiState as SVGViewModel.CharacterUiState.Error).message)
            }
        }
    }
}

@Composable
fun DrawableCharacter(
    graphic: Graphic
) {
    var strokeIndex: Int by remember { mutableStateOf(0) }
    var accuracy by remember { mutableFloatStateOf(0f) }

    var enabled by remember { mutableStateOf(false) }

    val translationX by animateFloatAsState(
        targetValue = if (enabled) 20f else 0f,
        animationSpec = repeatable(
            iterations = 3,
            animation = tween(durationMillis = 50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        finishedListener = { enabled = false } // Reset after animation
    )

    Column {
//        DrawingCanvas(
//            modifier = Modifier.offset(x = translationX.dp),
//            strokes = graphic.strokes,
//            medians = graphic.medians,
//            currentStrokeIndex = strokeIndex,
//            mode = MODEL_GUIDE
//        ) { percent ->
//            accuracy = percent
//            if (accuracy >= 50) {
//                strokeIndex++
//            } else enabled = true
//        }
        SimpleDrawing(
            pathData = graphic.strokes,
        )

        Row {
            Button(
                onClick = {
                    strokeIndex++
                },
                enabled = strokeIndex < graphic.strokes.size
            ) {
                Text("Index: $strokeIndex")
            }
            Button(
                onClick = {
                    strokeIndex = 0
                },
            ) {
                Text("Reset")
            }
            Text("Percentage accuracy: $accuracy")
        }
    }
}