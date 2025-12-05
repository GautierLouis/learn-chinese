package com.louisgautier.composeApp

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevel
import com.louisgautier.composeApp.session.QuestionCount
import com.louisgautier.domain.model.Difficulty
import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object HomeRoute : Route()

    @Serializable
    data object LoginRoute : Route()

    @Serializable
    data class SVG(
        val code: Int
    ) : Route()

    @Serializable
    data object DictionaryListRoute : Route()

    @Serializable
    data object SessionBuilderRoute : Route()

    @Serializable
    data class SessionRoute(
        val level: List<CharacterFrequencyLevel>,
        val difficulty: Difficulty,
        val limit: QuestionCount,
    ) : Route() {
        companion object {
            fun default() = SessionRoute(
                listOf(CharacterFrequencyLevel.COMMON),
                Difficulty.EASY, QuestionCount.FIVE
            )
        }
    }

    @Serializable
    data object SessionCongratulationScreenRoute : Route()

    @Serializable
    data class PracticeCharacterRoute(val code: Int) : Route()

}
