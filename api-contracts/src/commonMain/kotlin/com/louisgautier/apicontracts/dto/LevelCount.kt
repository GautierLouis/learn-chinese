package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class LevelCount(val level: CharacterFrequencyLevel, val count: Int)