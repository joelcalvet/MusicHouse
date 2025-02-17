package com.example.musichouse.ui.viewmodel
import android.app.Application
import androidx.lifecycle.*
import com.example.musichouse.data.database.SongDatabase
import com.example.musichouse.data.model.Song
import kotlinx.coroutines.launch

class SongViewModel(application: Application) : AndroidViewModel(application) {

    private val songDao = SongDatabase.getDatabase(application).songDao()
    val allSongs: LiveData<List<Song>> = songDao.getAllSongs()

    fun addSong(song: Song) {
        viewModelScope.launch { songDao.insert(song) }
    }

    fun updateSong(song: Song) {
        viewModelScope.launch { songDao.update(song) }
    }

    fun deleteSong(song: Song) {
        viewModelScope.launch { songDao.delete(song) }
    }
}
