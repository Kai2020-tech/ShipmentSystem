package com.example.shipmentsystem.ship.complete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentCompleteBinding
import com.example.shipmentsystem.databinding.FragmentProcessingBinding
import timber.log.Timber


class CompleteFragment : Fragment() {
    private var processingBinding: FragmentCompleteBinding? = null
    private val binding get() = processingBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        processingBinding = FragmentCompleteBinding.inflate(inflater,container,false)



     return binding.root
    }




}