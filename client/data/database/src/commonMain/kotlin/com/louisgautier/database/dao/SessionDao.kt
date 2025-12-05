package com.louisgautier.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.louisgautier.database.entity.BasicStatistics
import com.louisgautier.database.entity.SessionEntity

@Dao
interface SessionDao {

    @Insert
    suspend fun insert(sessionEntity: SessionEntity)

    @Insert(onConflict = REPLACE)
    suspend fun replace(sessionEntity: SessionEntity)

    @Query("SELECT * FROM SessionEntity ORDER BY date DESC")
    suspend fun getAll(): List<SessionEntity>

    @Query("SELECT * FROM SessionEntity ORDER BY date DESC LIMIT :limit")
    suspend fun getLast(limit: Int): List<SessionEntity>

    @Query("SELECT * FROM SessionEntity WHERE responses LIKE '%\"code\":' || :code || '%' ORDER BY date DESC LIMIT 3")
    suspend fun getLastFor(code: Int): List<SessionEntity>

    @Query(
        """
        SELECT 
            COALESCE(SUM(score), 0) AS totalScore,
            COALESCE(AVG(duration), 0) AS averageTime,
            COALESCE(COUNT(*), 0) AS sessionCount,
            GROUP_CONCAT(difficulty) AS difficulties,
            GROUP_CONCAT(DISTINCT date) AS uniqueDates
        FROM SessionEntity
    """
    )
    suspend fun getBasicStatistics(): BasicStatistics

}
