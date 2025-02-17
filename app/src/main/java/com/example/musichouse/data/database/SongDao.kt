package com.example.musichouse.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.musichouse.data.model.Song

@Dao
interface SongDao {
    // Insertar una nova cançó
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(song: Song)

    // Actualitzar una cançó existent
    @Update
    suspend fun update(song: Song)

    // Esborrar una cançó
    @Delete
    suspend fun delete(song: Song)

    // Obtenir totes les cançons
    @Query("SELECT * FROM songs")
    fun getAllSongs(): LiveData<List<Song>>

    // Obtenir una sola cançó pel seu id
    @Query("SELECT * FROM songs WHERE id = :songId")
    suspend fun getSongById(songId: Int): Song?
}
