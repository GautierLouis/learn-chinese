package com.louisgautier.domain.repository

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevel
import com.louisgautier.apicontracts.dto.DictionaryWithGraphic
import com.louisgautier.apicontracts.dto.LevelCount
import com.louisgautier.network.interfaces.CharacterService

class DefaultCharacterRepository(
    private val characterService: CharacterService
) : CharacterRepository {

    override suspend fun getLevelCount(): Result<List<LevelCount>> =
        characterService.getLevelCount()
            .map { list ->
                list.filter { it.level in CharacterFrequencyLevel.validEntry }
                    .sortedBy { it.level.ordinal }
            }

    override suspend fun generateSession(level: List<CharacterFrequencyLevel>, limit: Int) =
        characterService.generateSession(level, limit)

    override suspend fun getByLevel(level: CharacterFrequencyLevel, page: Int, limit: Int) =
        characterService.getByLevel(level, page, limit)

    override suspend fun getByName(code: Int): Result<DictionaryWithGraphic> = characterService.getByName(code)
    override suspend fun getSVG(code: Int) = characterService.getSVG(code)
}