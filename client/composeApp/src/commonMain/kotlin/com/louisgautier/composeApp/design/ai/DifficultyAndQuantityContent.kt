package com.louisgautier.composeApp.design.ai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.louisgautier.composeApp.session.QuestionCount
import com.louisgautier.composeApp.session.QuestionCountPicker
import com.louisgautier.domain.model.Difficulty

@Composable
fun DifficultyAndQuantityContent(
    selectedDifficulty: Difficulty,
    questionCount: QuestionCount,
    modifier: Modifier = Modifier,
    onDifficultySelected: (Difficulty) -> Unit = {},
    onQuestionCountSelected: (QuestionCount) -> Unit = {}
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        DifficultyPickerContent(
            selectedDifficulty = selectedDifficulty,
            onDifficultySelected = onDifficultySelected
        )

        QuestionCountPicker(
            questionCount = questionCount,
            onQuestionCountSelected = onQuestionCountSelected
        )
    }
}