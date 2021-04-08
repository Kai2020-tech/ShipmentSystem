package com.example.shipmentsystem.search

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
import com.example.shipmentsystem.getNavController
import com.example.shipmentsystem.ship.edit.EditVm


class SearchResultFragment : Fragment() {
    private var searchResultBinding: FragmentSearchResultBinding? = null
    private val binding get() = searchResultBinding!!

    private val searchVm: SearchVm by activityViewModels()
    private val editVm: EditVm by activityViewModels()

    private lateinit var rvSearchAdapter: RvSearchAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchResultBinding = FragmentSearchResultBinding.inflate(inflater, container, false)

        rvSearchAdapter = RvSearchAdapter()

        binding.rvSearchResult.adapter = rvSearchAdapter.apply {
            itemClickListener = {
                getNavController().navigate(R.id.action_searchResultFragment_to_editFragment)
                editVm.getProcessingItem(it)
            }
        }
        binding.rvSearchResult.layoutManager = LinearLayoutManager(requireActivity())

        searchVm.searchResultProcessingList.observe(viewLifecycleOwner, Observer {
            rvSearchAdapter.update(it)
            binding.tvSeachCount.text = getString(R.string.search_count, it.size.toString())
        })

        binding.tvSearchDate.text = searchVm.searchOrderDate




        return binding.root
    }


}