package com.example.shipmentsystem.ship.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentSearchResultBinding


class SearchResultFragment : Fragment() {
    private var searchResultBinding: FragmentSearchResultBinding? = null
    private val binding get() = searchResultBinding!!

    private val searchVm: SearchVm by activityViewModels()

    private lateinit var rvSearchAdapter: RvSearchAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchResultBinding = FragmentSearchResultBinding.inflate(inflater, container, false)

        rvSearchAdapter = RvSearchAdapter()

        binding.rvSearchResult.adapter = rvSearchAdapter
        binding.rvSearchResult.layoutManager = LinearLayoutManager(requireActivity())

        searchVm.searchList.observe(viewLifecycleOwner, Observer {
            rvSearchAdapter.update(it)
        })




        return binding.root
    }


}