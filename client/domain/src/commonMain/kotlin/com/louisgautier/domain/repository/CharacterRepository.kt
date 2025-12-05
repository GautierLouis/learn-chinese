package com.louisgautier.domain.repository

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevel
import com.louisgautier.apicontracts.dto.DictionaryWithGraphic
import com.louisgautier.apicontracts.dto.Graphic
import com.louisgautier.apicontracts.dto.LevelCount
import com.louisgautier.apicontracts.dto.ResponseList
import com.louisgautier.apicontracts.dto.SimpleDictionary

interface CharacterRepository {
    suspend fun getLevelCount(): Result<List<LevelCount>>
    suspend fun generateSession(
        level: List<CharacterFrequencyLevel>,
        limit: Int
    ): Result<List<DictionaryWithGraphic>>

    suspend fun getByLevel(
        level: CharacterFrequencyLevel,
        page: Int,
        limit: Int
    ): Result<ResponseList<SimpleDictionary>>

    suspend fun getByName(code: Int): Result<DictionaryWithGraphic>
    suspend fun getSVG(code: Int): Result<Graphic>
}

