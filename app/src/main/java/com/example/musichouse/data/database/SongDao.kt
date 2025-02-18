package com.example.musichouse.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.musichouse.data.model.Song

@Dao
interface SongDao {

    @Query("SELECT * FROM songs ORDER BY title ASC")
    fun getAllSongs(): LiveData<List<Song>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(song: Song)

    @Update
    suspend fun update(song: Song)

    @Delete
    suspend fun delete(song: Song)
}