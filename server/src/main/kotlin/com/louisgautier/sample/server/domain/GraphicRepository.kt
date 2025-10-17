package com.louisgautier.sample.server.domain

import com.louisgautier.sample.server.database.entity.GraphicTable
import com.louisgautier.apicontracts.dto.Graphic
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll

class GraphicRepository {

    fun ResultRow.toGraphic() = Graphic(
        character = this[GraphicTable.character],
        strokes = this[GraphicTable.strokes].split(","),
        medians = Json.decodeFromString(this[GraphicTable.medians]),
    )

    suspend fun get(character: Char): Graphic? = suspendTransaction {
        GraphicTable.selectAll().where { GraphicTable.character eq character }.limit(1)
            .map { it.toGraphic() }.firstOrNull()
    }

    suspend fun batchCreate(graphic: List<Graphic>) = suspendTransaction {
        GraphicTable.batchInsert(
            graphic,
            ignore = true,
            shouldReturnGeneratedValues = false
        ) {
            this[GraphicTable.character] = it.character
            this[GraphicTable.strokes] = it.strokes.joinToString()
            this[GraphicTable.medians] = Json.encodeToString(it.medians)
        }
    }
}
