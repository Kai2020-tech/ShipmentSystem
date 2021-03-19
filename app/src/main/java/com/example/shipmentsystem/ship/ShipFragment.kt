package com.example.shipmentsystem.ship

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentShipBinding


class ShipFragment : Fragment() {
    private var shipBinding: FragmentShipBinding? = null
    private val binding get() = shipBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shipBinding = FragmentShipBinding.inflate(inflater, container, false)


        return binding.root
    }

}