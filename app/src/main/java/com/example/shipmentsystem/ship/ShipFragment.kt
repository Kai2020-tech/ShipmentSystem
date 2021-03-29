package com.example.shipmentsystem.ship

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentShipBinding
import com.google.android.material.tabs.TabLayoutMediator


class ShipFragment : Fragment() {
    private var shipBinding: FragmentShipBinding? = null
    private val binding get() = shipBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shipBinding = FragmentShipBinding.inflate(inflater, container, false)

        binding.viewPager2.adapter = VpAdapter(this.childFragmentManager, this.lifecycle)

        val title: ArrayList<String> =
            arrayListOf(getString(R.string.processing), getString(R.string.complete))

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = title[position]
        }.attach()

        return binding.root
    }
}