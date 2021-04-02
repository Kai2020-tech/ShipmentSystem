package com.example.shipmentsystem.ship.complete

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
import com.example.shipmentsystem.databinding.FragmentCompleteBinding
import com.example.shipmentsystem.databinding.FragmentProcessingBinding
import com.example.shipmentsystem.getNavController
import timber.log.Timber


class CompleteFragment : Fragment() {
    private var completeBinding: FragmentCompleteBinding? = null
    private val binding get() = completeBinding!!

    private val completeVm: CompleteVm by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        completeBinding = FragmentCompleteBinding.inflate(inflater, container, false)

        val rvCompleteAdapter = RvCompleteAdapter()
        binding.recyclerView.adapter = rvCompleteAdapter.apply {
            itemClickListener = {
                getNavController().navigate(R.id.action_shipFragment_to_completeItemFragment)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        completeVm.completeList.observe(viewLifecycleOwner, Observer {
            rvCompleteAdapter.update(it)
        })


        return binding.root
    }


}