package com.uruklabs.dictionaryapp.views.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uruklabs.dictionaryapp.R
import com.uruklabs.dictionaryapp.databinding.FragmentWordListBinding
import com.uruklabs.dictionaryapp.models.uiModels.Word
import com.uruklabs.dictionaryapp.viewModels.WordViewModel
import com.uruklabs.dictionaryapp.views.adapters.ListWordsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class WordListFragment : Fragment() {
    private lateinit var dialog: Dialog
    private lateinit var binding: FragmentWordListBinding
    private val adapterRv = ListWordsAdapter{ word ->
        HomeFragmentDirections.actionHomeFragmentToDetailsWordFragment(word.word).also {
            findNavController().navigate(it)
        }

    }
    private val listWords = mutableListOf<Word>()
    private val viewModel by viewModel<WordViewModel>()

    override fun onStart() {
        super.onStart()
        if (listWords.isEmpty()) {
            viewModel.getWord()
            openDialogSearching()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = Dialog(requireContext())
        viewModel.wordLiveData.observe(this) {
            listWords.add(it)
            adapterRv.setWordsl(listWords)
            dialog.dismiss()

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordListBinding.inflate(inflater, container, false)
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

    private fun openDialogSearching(){
        dialog.setContentView(R.layout.dialog_search_word)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)
        dialog.show()
    }






}