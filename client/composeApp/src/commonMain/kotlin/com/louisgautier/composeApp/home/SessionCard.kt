package com.louisgautier.composeApp.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.composeApp.design.ai.Gray200
import com.louisgautier.composeApp.design.ai.Teal50
import com.louisgautier.composeApp.design.ai.Teal600
import com.louisgautier.composeApp.design.previewSession
import com.louisgautier.domain.model.Difficulty
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.utils.toHHMMSS
import com.louisgautier.domain.utils.toISODateString
import learn_chinese.client.composeapp.generated.resources.Res
import learn_chinese.client.composeapp.generated.resources.ic_rounded_trophy
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun SessionCard(
    session: Session,
    modifier: Modifier = Modifier.Companion
) {
    val locale = Locale.Companion.current

    HomeCard(modifier) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Companion.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RoundIcon(
                icon = Res.drawable.ic_rounded_trophy,
                containerColor = Teal50,
                contentColor = Teal600,
                size = 24.dp,
                padding = 4.dp
            )
            Text(text = session.date.toISODateString())
            Spacer(Modifier.Companion.weight(1f))
            LastSessionScore(session.score)
        }

        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LastSessionMetric(
                metric = SessionMetric.QuestionCount,
                value = session.responses.size.toString(),
                modifier = Modifier.Companion.weight(1f)
            )
            LastSessionMetric(
                metric = SessionMetric.Time,
                value = session.duration.toHHMMSS(),
                modifier = Modifier.Companion.weight(1f)
            )
            LastSessionMetric(
                metric = SessionMetric.Difficulty,
                value = session.difficulty.name.lowercase().capitalize(locale),
                modifier = Modifier.Companion.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
@Preview
fun SessionCardPreview() {
    SessionCard(session = previewSession)
}