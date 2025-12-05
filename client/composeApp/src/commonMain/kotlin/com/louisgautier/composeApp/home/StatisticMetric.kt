package com.louisgautier.composeApp.home

import androidx.compose.ui.graphics.Color
import com.louisgautier.composeApp.design.ai.Orange100
import com.louisgautier.composeApp.design.ai.Orange700
import com.louisgautier.composeApp.design.ai.Purple100
import com.louisgautier.composeApp.design.ai.Purple700
import com.louisgautier.composeApp.design.ai.Teal100
import com.louisgautier.composeApp.design.ai.Teal700
import com.louisgautier.utils.CoreKeepForR8
import learn_chinese.client.composeapp.generated.resources.Res
import learn_chinese.client.composeapp.generated.resources.ic_rounded_bolt
import learn_chinese.client.composeapp.generated.resources.ic_rounded_calendar_today
import learn_chinese.client.composeapp.generated.resources.ic_rounded_streak
import org.jetbrains.compose.resources.DrawableResource

@CoreKeepForR8
enum class StatisticMetric(
    val title: String,
    val icon: DrawableResource,
    val containerColor: Color,
    val contentColor: Color,
) {

    Streak(
        title = "Day Streak",
        icon = Res.drawable.ic_rounded_streak,
        containerColor = Orange100,
        contentColor = Orange700,
    ),

    Sessions(
        title = "Sessions",
        icon = Res.drawable.ic_rounded_calendar_today,
        containerColor = Teal100,
        contentColor = Teal700,
    ),

    TotalScore(
        title = "Total XP",
        icon = Res.drawable.ic_rounded_bolt,
        containerColor = Purple100,
        contentColor = Purple700,
    ),
}

