package com.louisgautier.composeApp.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
fun StatisticValue(
    metric: StatisticMetric,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundIcon(
            icon = metric.icon,
            containerColor = metric.containerColor,
            contentColor = metric.contentColor,
            size = 36.dp,
            padding = 8.dp
        )
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = metric.title, fontSize = 16.sp)
    }
}


@Composable
@Preview(showBackground = true)
fun StatisticValue(
    @PreviewParameter(StatisticCardItemProvider::class) item: StatisticMetric
) {
    StatisticValue(item, "10")
}

class StatisticCardItemProvider() : PreviewParameterProvider<StatisticMetric> {
    override val values: Sequence<StatisticMetric>
        get() = StatisticMetric.entries.asSequence()
}
