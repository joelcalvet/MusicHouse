package com.example.musichouse.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musichouse.R
import com.example.musichouse.data.database.SongDatabase
import com.example.musichouse.data.repository.SongRepository
import com.example.musichouse.databinding.FragmentHomeBinding
import com.example.musichouse.ui.adapter.SongAdapter
import com.example.musichouse.ui.viewmodel.SongViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val songViewModel: SongViewModel by viewModels()
    private lateinit var songAdapter: SongAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel with the repository
        val songDao = SongDatabase.getDatabase(requireContext()).songDao()
        songViewModel.init(SongRepository(songDao))

        // Initialize the adapter
        songAdapter = SongAdapter(mutableListOf()) { song ->
            val bundle = Bundle().apply {
                putInt("songId", song.id)
                putString("songTitle", song.title)
                putString("songArtist", song.artist)
                putString("songAlbum", song.album)
                putFloat("songDuration", song.duration)
            }
            findNavController().navigate(R.id.action_homeFragment_to_songDetailFragment, bundle)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = songAdapter
        }

        songViewModel.allSongs.observe(viewLifecycleOwner) { songs ->
            songAdapter.updateData(songs)
        }

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addSongFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}