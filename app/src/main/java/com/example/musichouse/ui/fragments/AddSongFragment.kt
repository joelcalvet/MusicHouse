package com.example.musichouse.ui.fragments

import android.annotation.SuppressLint
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
import com.example.musichouse.databinding.FragmentAddSongBinding
import com.example.musichouse.ui.viewmodel.SongViewModel

class AddSongFragment : Fragment() {

    private var _binding: FragmentAddSongBinding? = null
    private val binding get() = _binding!!
    private val songViewModel: SongViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialitza el ViewModel amb el repository
        val songDao = SongDatabase.getDatabase(requireContext()).songDao()
        songViewModel.init(SongRepository(songDao))

        // Recupera les dades si s'està actualitzant una cançó (update mode)
        val songId = arguments?.getInt("songId") ?: 0
        val songTitle = arguments?.getString("songTitle") ?: ""
        val songArtist = arguments?.getString("songArtist") ?: ""
        val songAlbum = arguments?.getString("songAlbum") ?: ""
        val songDuration = arguments?.getFloat("songDuration") ?: 0f

        // Si tenim dades, omple els camps per actualitzar
        if (songTitle.isNotEmpty() && songArtist.isNotEmpty() && songAlbum.isNotEmpty()) {
            binding.etTitle.setText(songTitle)
            binding.etArtist.setText(songArtist)
            binding.etAlbum.setText(songAlbum)
            binding.etDuration.setText(songDuration.toString())
        }

        binding.btnSave.setOnClickListener {
            val newTitle = binding.etTitle.text.toString().trim()
            val newArtist = binding.etArtist.text.toString().trim()
            val newAlbum = binding.etAlbum.text.toString().trim()
            val newDurationText = binding.etDuration.text.toString().trim()
            val newDuration = newDurationText.toFloatOrNull() ?: 0f

            if (newTitle.isNotEmpty() && newArtist.isNotEmpty() && newAlbum.isNotEmpty()) {
                val newSong = Song(
                    id = songId, // Si songId és 0, és una nova cançó; si no, és actualització.
                    title = newTitle,
                    artist = newArtist,
                    album = newAlbum,
                    duration = newDuration
                )

                if (songId != 0) {
                    songViewModel.updateSong(newSong)
                    Toast.makeText(requireContext(), "Cançó actualitzada!", Toast.LENGTH_SHORT).show()
                } else {
                    songViewModel.addSong(newSong)
                    Toast.makeText(requireContext(), "Cançó afegida!", Toast.LENGTH_SHORT).show()
                }
                findNavController().navigate(R.id.action_addSongFragment_to_homeFragment)
            } else {
                Toast.makeText(requireContext(), "Tots els camps són obligatoris!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}