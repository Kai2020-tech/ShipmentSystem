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
import com.example.shipmentsystem.ship.edit.EditVm
import com.example.shipmentsystem.toast

class ProcessingFragment : Fragment() {
    private var processingBinding: FragmentProcessingBinding? = null
    private val binding get() = processingBinding!!
    private lateinit var processAdapter: RvProcessingAdapter

    private val processingVm: ProcessingVm by activityViewModels()
    private val editVm: EditVm by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        processingBinding = FragmentProcessingBinding.inflate(inflater, container, false)

        processAdapter = RvProcessingAdapter()

        binding.recyclerView.adapter = processAdapter.apply {
            itemClickListener = {
                getNavController().navigate(R.id.action_shipFragment_to_editFragment)
//                getNavController().navigate(ShipFragmentDirections.actionShipFragmentToEditFragment(it))
                editVm.getProcessingItem(it)    //use viewModel access
                toast("$it")
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        processingVm.processingList.observe(viewLifecycleOwner, Observer { list ->
            processAdapter.update(list)
        })

        return binding.root
    }

    private fun getNavController(): NavController {
        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController

    }//set NavHostFragment, a container for fragments

}