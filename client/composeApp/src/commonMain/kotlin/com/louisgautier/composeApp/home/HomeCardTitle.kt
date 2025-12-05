package com.louisgautier.composeApp.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.louisgautier.composeApp.design.ai.Teal50
import com.louisgautier.composeApp.design.ai.Teal600
import learn_chinese.client.composeapp.generated.resources.Res
import learn_chinese.client.composeapp.generated.resources.ic_rounded_trophy
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun HomeCardTitle(
    title: String,
    icon: DrawableResource,
    trailing: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RoundIcon(
            icon = icon,
            containerColor = Teal50,
            contentColor = Teal600,
            size = 24.dp,
            padding = 4.dp
        )
        Text(text = title)
        Spacer(Modifier.weight(1f))
        trailing()
    }
}