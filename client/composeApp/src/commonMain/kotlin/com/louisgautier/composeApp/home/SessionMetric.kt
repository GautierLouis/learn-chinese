package com.louisgautier.composeApp.home

import com.louisgautier.utils.CoreKeepForR8
import learn_chinese.client.composeapp.generated.resources.Res
import learn_chinese.client.composeapp.generated.resources.ic_rounded_avg_time
import learn_chinese.client.composeapp.generated.resources.ic_rounded_bolt
import learn_chinese.client.composeapp.generated.resources.ic_rounded_target
import org.jetbrains.compose.resources.DrawableResource

@CoreKeepForR8
enum class SessionMetric(
    val title: String,
    val icon: DrawableResource,
) {

    QuestionCount(title = "Questions", icon = Res.drawable.ic_rounded_target),
    Time(title = "Time", icon = Res.drawable.ic_rounded_avg_time),
    Score(title = "Score", icon = Res.drawable.ic_rounded_bolt),
    Difficulty(title = "Difficulty", icon = Res.drawable.ic_rounded_bolt),
}