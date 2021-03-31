package com.example.shipmentsystem.ship

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentShipBinding
import com.example.shipmentsystem.getNavController
import com.example.shipmentsystem.toast
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

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater?.inflate(R.menu.menu_search, menu)

    }

}