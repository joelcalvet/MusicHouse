package com.example.musichouse.data.repository;

import com.example.musichouse.data.database.SongDao
import com.example.musichouse.data.model.Song

class SongRepository(private val songDao: SongDao) {

    fun getAllSongs() = songDao.getAllSongs()

    suspend fun insert(song: Song) {
        songDao.insert(song)
    }

    suspend fun update(song: Song) {
        songDao.update(song)
    }

    suspend fun delete(song: Song) {
        songDao.delete(song)
    }
}
