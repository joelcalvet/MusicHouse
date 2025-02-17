package com.example.musichouse.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.musichouse.data.model.Song

@Database(entities = [Song::class], version = 2, exportSchema = false)
abstract class SongDatabase : RoomDatabase() {

    // Defineix el DAO
    abstract fun songDao(): SongDao

    companion object {
        @Volatile
        private var INSTANCE: SongDatabase? = null

        fun getDatabase(context: Context): SongDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SongDatabase::class.java,  // Nom de la classe canviat a "SongDatabase"
                    "song_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
