package com.cy.beautygankio.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.cy.beautygankio.data.Girl
import kotlinx.coroutines.flow.Flow

@Dao
interface GirlDao {
    @Insert(onConflict = REPLACE)
    suspend fun save(vararg girl: Girl):List<Long>

    @Insert(onConflict = REPLACE)
    suspend fun save(girlsList: List<Girl>):List<Long>

    @Query("SELECT * FROM girl WHERE _id = :girlId")
    fun load(girlId:String):Girl

    @Query("SELECT * FROM girl")
    fun getGirls(): Flow<List<Girl>>
}