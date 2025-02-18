package com.example.musichouse.ui.viewmodel

import androidx.lifecycle.*
import com.example.musichouse.data.model.Song
import com.example.musichouse.data.repository.SongRepository
import kotlinx.coroutines.launch

class SongViewModel : ViewModel() {

    private lateinit var repository: SongRepository

    val allSongs: LiveData<List<Song>> get() = repository.getAllSongs()

    fun init(repository: SongRepository) {
        this.repository = repository
    }

    fun addSong(song: Song) {
        viewModelScope.launch {
            repository.insert(song)
        }
    }

    fun updateSong(song: Song) {
        viewModelScope.launch {
            repository.update(song)
        }
    }

    fun deleteSong(song: Song) {
        viewModelScope.launch {
            repository.delete(song)
        }
    }
}