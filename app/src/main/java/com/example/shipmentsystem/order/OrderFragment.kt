package com.example.shipmentsystem.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentOrderBinding
import com.example.shipmentsystem.getProductViewModel
import com.example.shipmentsystem.product.ProductVM
import timber.log.Timber


class OrderFragment : Fragment() {
    private var orderBinding: FragmentOrderBinding? = null
    private lateinit var productVM: ProductVM
    private val binding get() = orderBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        orderBinding = FragmentOrderBinding.inflate(inflater, container, false)
        productVM = getProductViewModel()
        productVM.getAllProduct()
        getProductListToSpinner()

        return binding.root
    }

    private fun getProductListToSpinner() {
        val productList = mutableListOf<String>()
        productVM.productList.observe(viewLifecycleOwner, Observer { it ->
            it.forEach { product ->
                productList.add(product.name)
            }
            setProductSpinner(productList)
        })
    }

    private fun setProductSpinner(list: MutableList<String>) {
        val adapter = ArrayAdapter(
            requireActivity(),      //context
            R.layout.spinner_item,  //spinner text view style
            list                    //spinner option list
        )
        binding.spinnerProduct.adapter = adapter
        binding.spinnerProduct.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Timber.d("clicked")
                    Toast.makeText(requireActivity(), list[position], Toast.LENGTH_SHORT).show()
                }

            }

    }
}