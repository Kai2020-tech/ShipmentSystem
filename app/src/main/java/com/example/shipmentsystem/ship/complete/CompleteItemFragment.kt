package com.example.shipmentsystem.ship.complete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shipmentsystem.databinding.FragmentCompleteItemBinding
import com.example.shipmentsystem.order.RvOrderAdapter
import java.text.SimpleDateFormat

class CompleteItemFragment : Fragment() {
    private var completeItemBinding: FragmentCompleteItemBinding? = null
    private val binding get() = completeItemBinding!!

    private val completeVm: CompleteVm by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        completeItemBinding = FragmentCompleteItemBinding.inflate(inflater, container, false)

        val orderAdapter = RvOrderAdapter()

        binding.rvOrder.adapter = orderAdapter
        binding.rvOrder.layoutManager = LinearLayoutManager(requireActivity())

        completeVm.completeItem.observe(viewLifecycleOwner, Observer {
            binding.edCustomerName.text = it.name
            binding.tvOrderDate.text = SimpleDateFormat("yyyy/MM/dd").format(it.orderDate)
            binding.tvCompleteDate.text = SimpleDateFormat("yyyy/MM/dd").format(it.completeDate)
            binding.tvItemCount.text = it.orderList.size.toString()
            binding.tvTotalPrice.text = it.totalPrice.toString()
            orderAdapter.updateList(it.orderList)
        })


        return binding.root
    }


}