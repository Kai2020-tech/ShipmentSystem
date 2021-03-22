package com.example.shipmentsystem.ship

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shipmentsystem.databinding.FragmentProcessingBinding


class ProcessingFragment : Fragment() {
    private var processingBinding: FragmentProcessingBinding? = null
    private val binding get() = processingBinding!!
    private lateinit var processAdapter: RvProcessingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        processingBinding = FragmentProcessingBinding.inflate(inflater,container,false)

        processAdapter = RvProcessingAdapter()
        binding.recyclerView.adapter = processAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())




        return binding.root
    }


}