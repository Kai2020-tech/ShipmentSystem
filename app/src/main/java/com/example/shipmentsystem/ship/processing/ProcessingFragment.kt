package com.example.shipmentsystem.ship.processing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentProcessingBinding
import com.example.shipmentsystem.getNavController
import com.example.shipmentsystem.ship.edit.EditVm
import timber.log.Timber

class ProcessingFragment : Fragment() {
    private var processingBinding: FragmentProcessingBinding? = null
    private val binding get() = processingBinding!!
    private lateinit var rvProcessAdapter: RvProcessingAdapter

    private val processingVm: ProcessingVm by activityViewModels()
    private val editVm: EditVm by activityViewModels()

    init {
        Timber.d("processing fragment create $this")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        processingBinding = FragmentProcessingBinding.inflate(inflater, container, false)

        rvProcessAdapter = RvProcessingAdapter()

        binding.recyclerView.adapter = rvProcessAdapter.apply {
            itemClickListener = {
                getNavController().navigate(R.id.action_shipFragment_to_editFragment)
                editVm.getProcessingItem(it)    //use viewModel access
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        processingVm.getList()

        processingVm.processingList.observe(viewLifecycleOwner, Observer { list ->
            rvProcessAdapter.update(list)
        })


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("processing fragment destroyed.")
        processingBinding = null
    }
}