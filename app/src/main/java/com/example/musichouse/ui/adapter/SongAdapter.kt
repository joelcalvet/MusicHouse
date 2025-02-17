package com.example.musichouse.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musichouse.data.model.Song
import com.example.musichouse.databinding.ItemSongBinding

class SongAdapter(
    private var songs: List<Song>,
    private val onSongClickListener: (Song) -> Unit // Afegim el listener
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song)
        holder.itemView.setOnClickListener {
            onSongClickListener(song)  // Quan es clica, cridem el listener
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newSongs: List<Song>) {
        songs = newSongs
        notifyDataSetChanged()
    }

    override fun getItemCount() = songs.size

    class SongViewHolder(private val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.song = song
            binding.executePendingBindings()
        }
    }
}
