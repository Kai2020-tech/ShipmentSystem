package com.example.shipmentsystem.ship

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentProcessingBinding


class ProcessingFragment : Fragment() {
    private var processingBinding: FragmentProcessingBinding? = null
    private val binding get() = processingBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        processingBinding = FragmentProcessingBinding.inflate(inflater,container,false)




        return binding.root
    }


}