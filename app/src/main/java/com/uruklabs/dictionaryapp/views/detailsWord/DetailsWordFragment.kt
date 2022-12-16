package com.uruklabs.dictionaryapp.views.detailsWord

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.uruklabs.dictionaryapp.R
import com.uruklabs.dictionaryapp.databinding.FragmentDetailsWordBinding
import com.uruklabs.dictionaryapp.helper.FirebaseHelper
import com.uruklabs.dictionaryapp.models.uiModels.Word
import com.uruklabs.dictionaryapp.utils.ListForNext
import com.uruklabs.dictionaryapp.viewModels.HistoryViewModel
import com.uruklabs.dictionaryapp.views.adapters.ListWordsAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsWordFragment : Fragment() {
    private lateinit var binding: FragmentDetailsWordBinding
    private val args by navArgs<DetailsWordFragmentArgs>()
    private val viewModel by viewModel<HistoryViewModel>()
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var word: Word

    override fun onStart() {
        super.onStart()
        viewModel.getWord(args.word)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.wordSearch.observe(this) {
            mediaPlayer = null
            it?.let {
                initViews(it)
                word = it
            }
        }

        viewModel.error.observe(this) {
            Toast.makeText(requireContext(), getString(R.string.error_word), Toast.LENGTH_SHORT).show()
            val word = args.word
            Handler().postDelayed({
                findNavController().navigateUp()
            }, 1000)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.ivExit.setOnClickListener {
           findNavController().navigateUp()
        }

        binding.btnNext.setOnClickListener {
            val listAdapter = ListForNext.listForNext
            val position = listAdapter.indexOf(word.word)
            if (position < listAdapter.size - 1) {
                val word = listAdapter[position + 1]
                viewModel.getWord(word)
            } else {
                Toast.makeText(requireContext(), "No more words", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            val listAdapter = ListForNext.listForNext
            val position = listAdapter.indexOf(word.word)
            if (position > 0) {
                val word = listAdapter[position - 1]
                viewModel.getWord(word)
            } else {
                Toast.makeText(requireContext(), "No more words", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun initSeekBar(){
        binding.seekBar2.max = mediaPlayer?.duration ?: 0
        val handler = android.os.Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    binding.seekBar2.progress = mediaPlayer?.currentPosition ?: 0
                    handler.postDelayed(this, 0)
                } catch (e: Exception) {
                    binding.seekBar2.progress = 0
                    Toast.makeText(context, "No audio", Toast.LENGTH_SHORT).show()
                }
            }
        }, 0)

    }



    private fun initViews(word: Word) {
        binding.tvWord.visibility = View.VISIBLE
        binding.tvPhonetic.visibility = View.VISIBLE
        binding.tvMeaning.visibility = View.VISIBLE
        binding.btnBack.visibility = View.VISIBLE
        binding.btnNext.visibility = View.VISIBLE
        binding.progressBar2.visibility = View.GONE
        binding.tvMeaningValue.visibility = View.VISIBLE
        binding.tvWord.text = word.word
        binding.tvMeaningValue.text = word.definitions
        binding.tvPhonetic.text = word.pronunciation

        if (word.isFavorite){
            binding.ivFavorite.setImageResource(R.drawable.ic_baseline_star_24_red)
        } else {
            binding.ivFavorite.setImageResource(R.drawable.ic_baseline_star_outline_24)
        }

        binding.ivFavorite.setOnClickListener {
            if (word.isFavorite){
                deleteWordFavorite(word)
            } else {
                addToFavorite(word)
            }
        }


        word.audio?.let {
            if(it != ""){
                binding.ivPlay.visibility = View.VISIBLE
                binding.seekBar2.visibility = View.VISIBLE
                mediaPlayer = MediaPlayer.create(requireContext(), it.toUri())
                initSeekBar()
            } else {
                binding.ivPlay.visibility = View.INVISIBLE
                binding.seekBar2.visibility = View.INVISIBLE
            }
        } ?: run {
            binding.ivPlay.visibility = View.INVISIBLE
            binding.seekBar2.visibility = View.INVISIBLE
        }

        binding.ivPlay.setOnClickListener {
            mediaPlayer?.start()
        }


        binding.seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })


    }

    private fun addToFavorite(word: Word){
        word.isFavorite = true
        viewModel.insertWord(word)
        FirebaseHelper.getIdUser()
            ?.let { FirebaseHelper.getDatabase().child("favorites").child(it).child(word.word).setValue(word)
                .addOnCompleteListener {
                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }

    }


    fun deleteWordFavorite(word: Word) {
        word.isFavorite = false
        viewModel.insertWord(word)
        FirebaseHelper.getIdUser()?.let {
            FirebaseHelper.getDatabase().child("favorites").child(it).child(word.word).removeValue()
                .addOnCompleteListener {
                    Toast.makeText(context, "Deleted from favorites", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
        }


    }

}