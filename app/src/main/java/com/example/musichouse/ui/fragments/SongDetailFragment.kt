package com.example.musichouse.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musichouse.R
import com.example.musichouse.data.database.SongDatabase
import com.example.musichouse.data.model.Song
import com.example.musichouse.data.repository.SongRepository
import com.example.musichouse.databinding.FragmentSongDetailBinding
import com.example.musichouse.ui.viewmodel.SongViewModel
import com.example.musichouse.ui.adapter.SongAdapter

class SongDetailFragment : Fragment() {

    private var _binding: FragmentSongDetailBinding? = null
    private val binding get() = _binding!!
    private val songViewModel: SongViewModel by viewModels()
    private lateinit var songAdapter: SongAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSongDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel with the repository
        val songDao = SongDatabase.getDatabase(requireContext()).songDao()
        songViewModel.init(SongRepository(songDao))

        // Initialize the adapter
        songAdapter = SongAdapter(mutableListOf()) { song ->
            // Handle song click
        }

        val id = arguments?.getInt("songId") ?: 0
        val title = arguments?.getString("songTitle")
        val artist = arguments?.getString("songArtist")
        val album = arguments?.getString("songAlbum")
        val duration = arguments?.getFloat("songDuration") ?: 0f

        binding.tvSongTitle.text = title
        binding.tvSongArtist.text = artist
        binding.tvSongAlbum.text = album
        binding.tvSongDuration.text = duration.toString()

        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this song?")
                .setPositiveButton("Yes") { _, _ ->
                    val song = Song(id = id, title = title!!, artist = artist!!, album = album!!, duration = duration)
                    songViewModel.deleteSong(song)
                    songAdapter.deleteSong(id)
                    Toast.makeText(requireContext(), "Song deleted!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_songDetailFragment_to_homeFragment)
                }
                .setNegativeButton("No", null)
                .show()
        }

        binding.btnUpdate.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("songId", id)
                putString("songTitle", title)
                putString("songArtist", artist)
                putString("songAlbum", album)
                putFloat("songDuration", duration)
            }
            findNavController().navigate(R.id.action_songDetailFragment_to_addSongFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}