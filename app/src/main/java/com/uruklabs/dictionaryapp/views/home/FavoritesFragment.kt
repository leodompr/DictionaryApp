package com.uruklabs.dictionaryapp.views.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.uruklabs.dictionaryapp.databinding.FragmentFavoritesBinding
import com.uruklabs.dictionaryapp.models.uiModels.Word
import com.uruklabs.dictionaryapp.viewModels.FavoritesViewModel
import com.uruklabs.dictionaryapp.views.adapters.ListWordsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val adapterRv = ListWordsAdapter { word ->
        HomeFragmentDirections.actionHomeFragmentToDetailsWordFragment(word.word).also {
            findNavController().navigate(it)
        }
    }
    private val viewModel by viewModel<FavoritesViewModel>()
    private val listWords = mutableListOf<Word>()

    override fun onStart() {
        super.onStart()
        viewModel.getFavoritesWords()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.wordLiveData.observe(this) {
            listWords.clear()
            it?.let { listWords.addAll(it)
                binding.progressBarWordList.visibility = View.GONE
            }
            adapterRv.setWordsl(listWords)
            binding.llEmptyWordList.visibility = if (listWords.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(listWords)


    }

    private fun initRecyclerView(list : List<Word>) {
        adapterRv.setWordsl(list)
        binding.rvWordList.apply{
            adapter = adapterRv
        }
    }




}