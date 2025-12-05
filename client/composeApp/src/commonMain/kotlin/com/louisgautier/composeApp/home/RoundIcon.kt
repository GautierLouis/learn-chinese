package com.louisgautier.composeApp.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import learn_chinese.client.composeapp.generated.resources.Res
import learn_chinese.client.composeapp.generated.resources.ic_rounded_bar_chart
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RoundIcon(
    icon: DrawableResource,
    containerColor: Color,
    contentColor: Color,
    size: Dp = 24.dp,
    padding: Dp = 8.dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(contentColor),
            modifier = Modifier
                .size(size)
                .background(containerColor, CircleShape)
                .padding(padding)
        )
    }
}

@Preview
@Composable
fun RoundIconPreview() {
    RoundIcon(
        icon = Res.drawable.ic_rounded_bar_chart,
        containerColor = Color.Yellow,
        contentColor = Color.Black,
        size = 36.dp,
        padding = 8.dp,
        modifier = Modifier
    )
}