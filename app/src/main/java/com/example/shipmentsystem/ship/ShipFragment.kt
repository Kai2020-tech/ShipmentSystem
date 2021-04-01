package com.example.shipmentsystem.ship

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.DialogSearchBinding
import com.example.shipmentsystem.databinding.FragmentShipBinding
import com.example.shipmentsystem.getNavController
import com.example.shipmentsystem.search.SearchVm
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ShipFragment : Fragment() {
    private var shipBinding: FragmentShipBinding? = null
    private val binding get() = shipBinding!!
    private lateinit var dialogSearchBinding: DialogSearchBinding

    private val searchVm: SearchVm by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        shipBinding = FragmentShipBinding.inflate(inflater, container, false)
        dialogSearchBinding = DialogSearchBinding.inflate(inflater, container, false)

        binding.viewPager2.adapter = VpAdapter(this.childFragmentManager, this.lifecycle)

        //if editFragment complete, action back to shipFragment
        // will make viewpager display specify fragment(completeFragment)
        arguments?.let {
            binding.viewPager2.currentItem = it.getInt("viewPager2Position")
        }

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
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {

                setDatePicker()

                AlertDialog.Builder(requireActivity())
                    .setView(dialogSearchBinding.root)
                    .setNegativeButton(getString(R.string.no)) { _, _ -> }
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        val start = dialogSearchBinding.tvDateStart.text.toString()
                        val end = dialogSearchBinding.tvDateEnd.text.toString()
                        val startDate: Date = SimpleDateFormat("yyyy/MM/dd").parse(start)
                        val endDate: Date = SimpleDateFormat("yyyy/MM/dd").parse(end)
                        searchVm.getSearchDate(startDate, endDate)
                        getNavController().navigate(R.id.action_shipFragment_to_searchResultFragment)
                    }
                    .setOnDismissListener {
                        (dialogSearchBinding.root.parent as ViewGroup).removeView(
                            dialogSearchBinding.root
                        )
                    }
                    .show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setDatePicker() {
        val dayTimeMillis = 86400000
        dialogSearchBinding.tvDateStart.text =
            SimpleDateFormat("yyyy/MM/dd").format(System.currentTimeMillis() - (7 * dayTimeMillis))
        dialogSearchBinding.tvDateEnd.text =
            SimpleDateFormat("yyyy/MM/dd").format(System.currentTimeMillis())

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        dialogSearchBinding.tvDateStart.setOnClickListener {
            DatePickerDialog(requireActivity(), { _, year, month, day ->
                run {
                    dialogSearchBinding.tvDateStart.text = "$year/${month + 1}/$day"
                }
            }, year, month, day).show()
        }

        dialogSearchBinding.tvDateEnd.setOnClickListener {
            DatePickerDialog(requireActivity(), { _, year, month, day ->
                run {
                    dialogSearchBinding.tvDateEnd.text = "$year/${month + 1}/$day"
                }
            }, year, month, day).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        shipBinding = null
    }

}