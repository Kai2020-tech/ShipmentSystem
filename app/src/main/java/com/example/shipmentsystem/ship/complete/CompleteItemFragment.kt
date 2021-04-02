package com.example.shipmentsystem.ship.complete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentCompleteItemBinding

class CompleteItemFragment : Fragment() {
    private var completeItemBinding: FragmentCompleteItemBinding? = null
    private val binding get() = completeItemBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        completeItemBinding = FragmentCompleteItemBinding.inflate(inflater, container, false)

        return binding.root
    }


}