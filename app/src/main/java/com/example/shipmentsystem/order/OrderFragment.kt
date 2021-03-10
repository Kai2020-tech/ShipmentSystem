package com.example.shipmentsystem.order

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.FtsOptions
import com.example.shipmentsystem.OrderVm
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentOrderBinding
import com.example.shipmentsystem.db.OrderItem
import com.example.shipmentsystem.db.Product
import com.example.shipmentsystem.getOrderViewModel
import com.example.shipmentsystem.getProductViewModel
import com.example.shipmentsystem.product.ProductVM
import com.example.shipmentsystem.product.RvProductAdapter
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


class OrderFragment : Fragment() {
    private var orderBinding: FragmentOrderBinding? = null
    private val binding get() = orderBinding!!

    private lateinit var productVM: ProductVM
    private lateinit var orderVM: OrderVm

    private lateinit var orderRvAdapter: RvOrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        orderBinding = FragmentOrderBinding.inflate(inflater, container, false)

        productVM = getProductViewModel()

        orderVM = getOrderViewModel()

        productVM.getAllProduct()

        getProductListToSpinner()

        setDatePicker()

        return binding.root
    }

    private fun getProductListToSpinner() {
        val productList = mutableListOf<String>()
        productVM.productList.observe(viewLifecycleOwner, Observer { it ->
            productList.clear()
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

    private fun initOrderRecyclerView() {
        orderRvAdapter = RvOrderAdapter()
        binding.rvOrder.adapter = orderRvAdapter.apply {
            itemClickListener = {
//                orderVM.onSelectProduct(it)
            }

            changeBackgroundListener = { currentItem, holder ->
                setSelectedItemColor(currentItem, holder)
            }
        }
        binding.rvOrder.layoutManager = LinearLayoutManager(requireActivity())
//        orderVM.productList.observe(viewLifecycleOwner, Observer {
//            orderRvAdapter.update(it)
//        })
    }

    private fun setSelectedItemColor(currentItem: OrderItem, holder: RvOrderAdapter.MyHolder) {
        val selectedColor = getString(R.string.selectedColor)
        val defaultColor = getString(R.string.defaultColor)
//        orderVM.selectedProduct.observe(viewLifecycleOwner, Observer {
//            //do not use "it" in here, cause it might be null
//            if (currentItem.id == orderVM.selectedProduct.value?.id) {
//                holder.name.setTextColor(Color.BLACK)
//                holder.price.setTextColor(Color.BLACK)
//                holder.itemView.setBackgroundColor(Color.parseColor(selectedColor))
//            } else {
//                holder.name.setTextColor(Color.WHITE)
//                holder.price.setTextColor(Color.WHITE)
//                holder.itemView.setBackgroundColor(Color.parseColor(defaultColor))
//            }
//        })
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun setDatePicker(){
        binding.tvDate.text = SimpleDateFormat("yyyy/MM/dd").format(System.currentTimeMillis())
        binding.tvDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireActivity(), { _, year, month, day ->
                run {
                    binding.tvDate.text = "$year/${month + 1}/$day"
                }
            }, year, month, day).show()
        }
    }
}