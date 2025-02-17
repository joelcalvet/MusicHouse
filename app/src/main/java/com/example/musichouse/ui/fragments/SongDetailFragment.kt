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
import com.example.musichouse.data.model.Song
import com.example.musichouse.databinding.FragmentSongDetailBinding
import com.example.musichouse.ui.viewmodel.SongViewModel

class SongDetailFragment : Fragment() {

    private var _binding: FragmentSongDetailBinding? = null
    private val binding get() = _binding!!
    private val songViewModel: SongViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSongDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                .setTitle("Confirmar Eliminació")
                .setMessage("Estas segur d'eliminar aquesta cançó?")
                .setPositiveButton("Si") { _, _ ->
                    val song = Song(title = title!!, artist = artist!!, album = album!!, duration = duration)
                    songViewModel.deleteSong(song)
                    Toast.makeText(requireContext(), "Cançó eliminada!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_songDetailFragment_to_homeFragment)
                }
                .setNegativeButton("No", null)
                .show()
        }

        binding.btnUpdate.setOnClickListener {
            val bundle = Bundle().apply {
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