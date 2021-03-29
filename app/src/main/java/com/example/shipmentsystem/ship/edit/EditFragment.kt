package com.example.shipmentsystem.ship.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shipmentsystem.databinding.FragmentEditBinding
import timber.log.Timber

class EditFragment : Fragment() {
    private var editBinding: FragmentEditBinding? = null
    private val binding get() = editBinding!!

    private val editVm: EditVm by activityViewModels()

    private lateinit var rvEditAdapter: RvEditAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editBinding = FragmentEditBinding.inflate(inflater,container,false)

        //get processingItem from ViewModel,no need safeargs//
//        val args = EditFragmentArgs.fromBundle(requireArguments())
//        Timber.d("${args.processingItem.name}")

        rvEditAdapter = RvEditAdapter()

        binding.rvEdit.adapter = rvEditAdapter.apply {

        }
        binding.rvEdit.layoutManager = LinearLayoutManager(requireActivity())

        editVm.processingItem.observe(viewLifecycleOwner, Observer { processingItem ->
            binding.edCustomerName.setText(processingItem.name)
            rvEditAdapter.updateList(processingItem.orderList)
        })

        return binding.root
    }


}