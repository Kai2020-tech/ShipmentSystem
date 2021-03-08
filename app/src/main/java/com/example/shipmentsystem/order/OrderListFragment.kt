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
import androidx.lifecycle.ViewModelProvider
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentOrderListBinding
import com.example.shipmentsystem.product.ItemViewModelFactory
import com.example.shipmentsystem.product.ProductViewModel
import timber.log.Timber


class OrderListFragment : Fragment() {
    private var orderBinding: FragmentOrderListBinding? = null
    private lateinit var productVM: ProductViewModel
    private val binding get() = orderBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        orderBinding = FragmentOrderListBinding.inflate(inflater, container, false)
        initProductViewModel()

        val productList = mutableListOf<String>()
        productVM.productList.observe(viewLifecycleOwner, Observer { it ->
//            productList.clear()
            it.forEach { product ->
                productList.add(product.name)
            }
            Timber.d("product $productList")
            initProductSpinner(productList)
        })




        return binding.root
    }

    private fun initProductSpinner(spinnerList: MutableList<String>) {
//        val productList = mutableListOf<String>("aaa","bbb","ccc")

//        var spinnerList= listOf<String>()

//        Timber.d(" spinner list $spinnerList")
        val adapter = ArrayAdapter(
            requireActivity(),
            R.layout.spinner_item,
            spinnerList
        )
        Timber.d("spinner $spinnerList")
//        adapter.setDropDownViewResource(R.layout.spinner_checked_item)
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
                    Toast.makeText(requireActivity(), position.toString(), Toast.LENGTH_SHORT).show()
                }

            }

    }

    private fun initProductViewModel() {
        val app = requireNotNull(activity).application
        productVM =
            ViewModelProvider(
                requireActivity(),
                ItemViewModelFactory(app)
            ).get(ProductViewModel::class.java)

        productVM.getAllProduct()
    }

}