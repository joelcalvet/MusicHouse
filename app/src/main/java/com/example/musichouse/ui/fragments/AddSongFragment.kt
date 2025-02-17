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
import com.example.musichouse.data.model.Song
import com.example.musichouse.databinding.FragmentAddSongBinding
import com.example.musichouse.ui.viewmodel.SongViewModel

class AddSongFragment : Fragment() {

    private var _binding: FragmentAddSongBinding? = null
    private val binding get() = _binding!!
    private val songViewModel: SongViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("songTitle")
        val artist = arguments?.getString("songArtist")
        val album = arguments?.getString("songAlbum")
        val duration = arguments?.getFloat("songDuration") ?: 0f

        if (title != null && artist != null && album != null) {
            binding.etTitle.setText(title)
            binding.etArtist.setText(artist)
            binding.etAlbum.setText(album)
            binding.etDuration.setText(duration.toString())
        }

        binding.btnSave.setOnClickListener {
            val newTitle = binding.etTitle.text.toString()
            val newArtist = binding.etArtist.text.toString()
            val newAlbum = binding.etAlbum.text.toString()
            val newDuration = binding.etDuration.text.toString().toFloatOrNull() ?: 0f

            if (newTitle.isNotEmpty() && newArtist.isNotEmpty()) {
                val newSong = Song(
                    title = newTitle,
                    artist = newArtist,
                    album = newAlbum,
                    duration = newDuration
                )
                if (title != null && artist != null && album != null) {
                    songViewModel.updateSong(newSong)
                    Toast.makeText(requireContext(), "Cançó actualitzada!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    songViewModel.addSong(newSong)
                    Toast.makeText(requireContext(), "Cançó afegida!", Toast.LENGTH_SHORT).show()
                }
                findNavController().navigate(R.id.action_addSongFragment_to_homeFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Tots els camps són obligatoris!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
