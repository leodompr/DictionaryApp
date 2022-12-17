package com.uruklabs.dictionaryapp.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.uruklabs.dictionaryapp.R
import com.uruklabs.dictionaryapp.databinding.FragmentHomeBinding
import com.uruklabs.dictionaryapp.views.adapters.ViewPagerAdapter


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()
        viewPagerAdapter()
    }

    private fun viewPagerAdapter() {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(WordListFragment(), getString(R.string.title_words))
        adapter.addFragment(HistoryFragment(), getString(R.string.title_history))
        adapter.addFragment(FavoritesFragment(), getString(R.string.title_favorites))
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }




}